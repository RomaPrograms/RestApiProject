package by.yellow.testtask.model.pool;

import by.yellow.testtask.exception.PersistentException;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionPool {
    Connection getConnection()
            throws InterruptedException, SQLException, PersistentException;
    void releaseConnection(PooledConnection connection);
    void init(String driverClass, String url, String user, String password,
              int startSize, int maxSize) throws PersistentException;
    PooledConnection createConnection() throws SQLException;
    void destroy();
}
