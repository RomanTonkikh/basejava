package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;
    public final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("" +
                    " UPDATE resume r" +
                    "    SET full_name=?" +
                    "  WHERE r.uuid=?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, uuid);
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(uuid);
                }
            }
            sqlHelper.execute("" +
                            " DELETE FROM contact c" +
                            "       WHERE c.resume_uuid=?",
                    ps -> {
                        ps.setString(1, resume.getUuid());
                        ps.execute();
                        return null;
                    });
            insertContact(resume, uuid, conn);
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        String uuid = resume.getUuid();
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("" +
                            "    INSERT INTO resume (uuid, full_name)" +
                            "         VALUES (?,?)")) {
                        ps.setString(1, uuid);
                        ps.setString(2, resume.getFullName());
                        ps.execute();
                    }
                    insertContact(resume, uuid, conn);
                    return null;
                }
        );
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("" +
                        "    SELECT *" +
                        "      FROM resume r" +
                        " LEFT JOIN contact c" +
                        "        ON r.uuid = c.resume_uuid" +
                        "     WHERE r.uuid =?",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    do {
                        addContact(rs, resume);
                    } while (rs.next());
                    return resume;
                });
    }

    private void addContact(ResultSet rs, Resume resume) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            resume.addContact(ContactType.valueOf(rs.getString("type")), value);
        }
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("" +
                        " DELETE FROM resume r" +
                        "       WHERE r.uuid =?",
                ps -> {
                    ps.setString(1, uuid);
                    if (ps.executeUpdate() == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    return null;
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute("" +
                        "    SELECT *" +
                        "      FROM resume r" +
                        " LEFT JOIN contact c" +
                        "        ON r.uuid = c.resume_uuid" +
                        "  ORDER BY full_name, uuid",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    Map<String, Resume> resumes = new HashMap<>();
                    while (rs.next()) {
                        String uuid = rs.getString("uuid");
                        Resume resume = resumes.get(uuid);
                        if (resume == null) {
                            resume = new Resume(uuid, rs.getString("full_name"));
                            resumes.put(uuid, resume);
                        }
                        addContact(rs, resume);
                    }
                    List<Resume> list = new ArrayList<>(resumes.values());
                    list.sort(RESUME_COMPARATOR);
                    return list;
                });
    }

    @Override
    public int size() {
        return sqlHelper.execute("" +
                        " SELECT COUNT(*)" +
                        " FROM resume",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    rs.next();
                    return rs.getInt(1);
                });
    }

    private void insertContact(Resume resume, String uuid, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("" +
                " INSERT INTO contact (resume_uuid, type, value)" +
                " VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                ps.setString(1, uuid);
                ps.setString(2, entry.getKey().name());
                ps.setString(3, entry.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
}
