package by.yellow.testtask.model.mysql;

import by.yellow.testtask.exception.PersistentException;
import by.yellow.testtask.model.dao.Transaction;
import by.yellow.testtask.model.dao.TransactionFactory;
import by.yellow.testtask.model.pool.ConnectionPoolRealization;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionFactoryRealization implements TransactionFactory {
    private static final Logger LOGGER
            = LogManager.getLogger(TransactionFactoryRealization.class);
    private Connection connection;

    public TransactionFactoryRealization() throws PersistentException {
        connection = ConnectionPoolRealization.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.error("It is impossible to turn off autocommiting"
                    + " for database connection", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public Transaction createTransaction() {
        return new TransactionRealization(connection);
    }

    @Override
    public void close() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            LOGGER.error("Connection was closed incorrectly");
        }
    }
}
