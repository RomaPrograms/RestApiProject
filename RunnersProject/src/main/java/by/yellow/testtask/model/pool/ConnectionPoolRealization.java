package by.yellow.testtask.model.pool;

import by.yellow.testtask.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPoolRealization implements ConnectionPool {

    /**
     * Logger for creation notes to some appender.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(ConnectionPoolRealization.class);

    private static final String CONNECTION_LIMIT_NUMBER_EXCEPTION
            = "The limit of number of database connections is exceeded";

    private static final String CONNECTION_RECEIVED_EXCEPTION
            = "It is impossible to connect to a database";

    private static final String CONNECTION_RECEIVED_MESSAGE
            = "Connection was received from pool. Current pool size: %d used"
            + " connections; %d free connection";

    private static final String CONNECTION_RETURNED_MESSAGE
            = "Connection was returned into pool. Current pool size: %d used"
            + " connections; %d free connection";

    private static Lock lock = new ReentrantLock();

    private int maxPoolSize;

    private String url;

    private String user;

    private String password;

    private static ConnectionPoolRealization instance
            = new ConnectionPoolRealization();

    private BlockingQueue<PooledConnection> freeConnection
            = new LinkedBlockingQueue<>();
    private Set<PooledConnection> usedConnection
            = new ConcurrentSkipListSet<>();

    public static ConnectionPoolRealization getInstance() {
        return instance;
    }

    @Override
    public void init(final String driverClass, final String dbUrl,
                     final String dbUser, final String dbPassword,
                     final int startSize, final int maxSize)
            throws PersistentException {

        try {
            lock.lock();
            destroy();
            Class.forName(driverClass);
            this.url = dbUrl;
            this.user = dbUser;
            this.password = dbPassword;
            this.maxPoolSize = maxSize;
            for (int i = 0; i < startSize; i++) {
                freeConnection.put(createConnection());
            }
        } catch (ClassNotFoundException | SQLException
                | InterruptedException e) {
            LOGGER.fatal(
                    "It is impossible to initialize connection pool", e);
            Thread.currentThread().interrupt();
            throw new PersistentException(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Connection getConnection() throws PersistentException {
        PooledConnection connection = null;
        while (connection == null) {
            try {
                lock.lock();
                if (!freeConnection.isEmpty()) {
                    connection = freeConnection.take();
                } else {
                    if (usedConnection.size() < maxPoolSize) {
                        connection = createConnection();
                    } else {
                        LOGGER.error(CONNECTION_LIMIT_NUMBER_EXCEPTION);
                        lock.unlock();
                        throw new PersistentException();
                    }
                }
            } catch (InterruptedException | SQLException e) {
                LOGGER.error(CONNECTION_RECEIVED_EXCEPTION, e);
                lock.unlock();
                Thread.currentThread().interrupt();
                throw new PersistentException(e);
            }
        }
        usedConnection.add(connection);
        LOGGER.debug(String.format(CONNECTION_RECEIVED_MESSAGE,
                usedConnection.size(), freeConnection.size()));
        lock.unlock();
        return connection;
    }

    @Override
    public void releaseConnection(final PooledConnection connection) {
        try {
            lock.lock();

            connection.clearWarnings();
            connection.setAutoCommit(true);
            usedConnection.remove(connection);
            freeConnection.put(connection);
            LOGGER.debug(String.format(CONNECTION_RETURNED_MESSAGE,
                    usedConnection.size(), freeConnection.size()));

        } catch (SQLException | InterruptedException e) {
            LOGGER.warn(CONNECTION_RECEIVED_EXCEPTION);
            Thread.currentThread().interrupt();
            connection.close();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public PooledConnection createConnection() throws SQLException {
        lock.lock();
        PooledConnection connection = new PooledConnection(DriverManager
                .getConnection(url, user, password));
        lock.unlock();
        return connection;
    }

    @Override
    public void destroy() {
        lock.lock();
        usedConnection.addAll(freeConnection);
        freeConnection.clear();
        for (PooledConnection connection : usedConnection) {
            connection.close();
        }
        usedConnection.clear();
        lock.unlock();
    }
}
