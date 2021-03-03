package com.urise.webapp.sql;

import com.urise.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }
        public <X> X  doConnection(String sql, SqlOperator<X> operator) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return operator.doSqlProcessing(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public interface SqlOperator<X> {
        X doSqlProcessing(PreparedStatement st) throws SQLException;
    }

}
