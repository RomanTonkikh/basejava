package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
            try (PreparedStatement ps = conn.prepareStatement("" +
                    " DELETE FROM contact c" +
                    "       WHERE c.resume_uuid=?")) {
                ps.setString(1, resume.getUuid());
                ps.execute();
            }
            insertContact(resume, conn);
            try (PreparedStatement ps = conn.prepareStatement("" +
                    " DELETE FROM section s" +
                    "       WHERE s.resume_uuid=?")) {
                ps.setString(1, resume.getUuid());
                ps.execute();
            }
            insertSection(resume, conn);
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
                    insertContact(resume, conn);
                    insertSection(resume, conn);
                    return null;
                }
        );
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(conn -> {

            Resume resume;
            try (PreparedStatement ps = conn.prepareStatement("" +
                    "    SELECT *" +
                    "      FROM resume r" +
                    "     WHERE r.uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, rs.getString("full_name"));
            }
            try (PreparedStatement ps = conn.prepareStatement("" +
                    "    SELECT *" +
                    "      FROM contact c" +
                    "     WHERE resume_uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addContact(rs, resume);
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("" +
                    "    SELECT *" +
                    "      FROM section s" +
                    "     WHERE resume_uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addSection(rs, resume);
                }
            }
            return resume;
        });
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
        Map<String, Resume> resumes = new LinkedHashMap<>();
        return sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("" +
                    "    SELECT *" +
                    "      FROM resume" +
                    "  ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    resumes.put(uuid, new Resume(uuid, rs.getString("full_name")));
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("" +
                    "    SELECT *" +
                    "      FROM contact")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume resume = resumes.get(rs.getString("resume_uuid"));
                    addContact(rs, resume);
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("" +
                    "    SELECT *" +
                    "      FROM section")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume resume = resumes.get(rs.getString("resume_uuid"));
                    addSection(rs, resume);
                }
            }
            return new ArrayList<>(resumes.values());
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

    private void addContact(ResultSet rs, Resume resume) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            resume.addContact(ContactType.valueOf(rs.getString("type")), value);
        }
    }

    private void addSection(ResultSet rs, Resume resume) throws SQLException {

        String value = rs.getString("value");
        SectionType type = SectionType.valueOf(rs.getString("type"));
        if (value != null) {
            switch (type) {
                case OBJECTIVE:
                case PERSONAL:
                    TextSection ts = new TextSection(value);
                    resume.addSection(type, ts);
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    ListSection ls = new ListSection(Arrays.asList(value.split("\n")));
                    resume.addSection(type, ls);
                    break;
            }
        }
    }

    private void insertContact(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("" +
                " INSERT INTO contact (resume_uuid, type, value)" +
                " VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, entry.getKey().name());
                ps.setString(3, entry.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSection(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("" +
                " INSERT INTO section (resume_uuid, type, value)" +
                " VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> entry : resume.getSections().entrySet()) {
                SectionType st = entry.getKey();
                AbstractSection section = entry.getValue();
                switch (st) {
                    case OBJECTIVE:
                    case PERSONAL:
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, entry.getKey().name());
                        ps.setString(3, ((TextSection) section).getContent());
                        ps.addBatch();
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        StringBuilder sb = new StringBuilder();
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, entry.getKey().name());
                        List<String> list = ((ListSection) section).getTextList();
                        for (String text : list) {
                            sb.append(text);
                            sb.append("\n");
                        }
                        ps.setString(3, sb.toString());
                        ps.addBatch();
                        break;
                }
            }
            ps.executeBatch();
        }
    }
}

