package by.yellow.testtask.model.mysql;

import by.yellow.testtask.exception.PersistentException;
import by.yellow.testtask.model.dao.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TransactionRealization implements Transaction {

    private static final Logger LOGGER
            = LogManager.getLogger(TransactionRealization.class);
    private Connection connection;

    private static Map<Class<? extends Dao<?>>, Class<? extends
            BaseDaoRealization>> classes = new ConcurrentHashMap<>();

    static {
        classes.put(RunnerRaceDao.class, RunnerRaceDaoRealization.class);
        classes.put(RunnerResultDao.class, RunnerResultDaoRealization.class);
    }

    TransactionRealization(final Connection connection) {
        this.connection = connection;
    }

    @Override
    public <T extends Dao<?>> T createDao(
            final Class<T> key) throws PersistentException {
        Class<? extends BaseDaoRealization> value = classes.get(key);
        if (value != null) {
            try {
                BaseDaoRealization dao;
                dao = value.getConstructor().newInstance();
                dao.setConnection(connection);
                return (T) dao;
            } catch (InstantiationException | IllegalAccessException e) {
                LOGGER.error(
                        "It is impossible to create data access object", e);
                throw new PersistentException(e);
            } catch (InvocationTargetException e) {
                LOGGER.error("Constructor of " + value.getSimpleName()
                        + " throws an exception", e);
                throw new PersistentException(e);
            } catch (NoSuchMethodException e) {
                LOGGER.error("Matching method is not found", e);
                throw new PersistentException(e);
            }
        }
        throw new PersistentException("Dao class named " + key.getSimpleName()
                + " wasn't founded");
    }

    @Override
    public void commit() throws PersistentException {
        try {
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error("It is impossible to commit transaction", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public void rollback() throws PersistentException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOGGER.error("It is impossible to rollback transaction", e);
            throw new PersistentException(e);
        }
    }
}
