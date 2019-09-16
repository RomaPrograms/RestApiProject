package by.yellow.testtask.service;

import by.yellow.testtask.exception.PersistentException;
import by.yellow.testtask.model.dao.Transaction;
import by.yellow.testtask.model.dao.TransactionFactory;
import by.yellow.testtask.model.pool.ConnectionPoolRealization;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceFactoryRealization implements ServiceFactory {
    private static final Logger LOGGER
            = LogManager.getLogger(ServiceFactoryRealization.class);

    /**
     * Driver which is required for MySql database.
     */
    private static final String DB_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";

    /**
     * URL of the required database.
     */
    private static final String DB_URL = "jdbc:mysql://localhost:3306/runner_db"
            + "?useUnicode=true&useJDBCCompliantTimezoneShift=true"
            + "&useLegacyDatetimeCode=false&serverTimezone=UTC";


    private static final String DB_USER = "root";

    private static Locale locale = new Locale("en", "US");

    private static ResourceBundle rb = ResourceBundle
            .getBundle("property.text", locale);

    private static final String DB_PASSWORD = rb.getString("dbPassword");

    private static final int DB_POOL_START_SIZE = 10;


    private static final int DB_POOL_MAX_SIZE = 1000;

    private static final Map<Class<? extends Service>,
            Class<? extends RaceServiceRealization>> SERVICES
            = new ConcurrentHashMap<>();

    static {
        SERVICES.put(RunnerRaceService.class, RunnerRaceServiceImplementation.class);
        SERVICES.put(RunnerResultService.class, RunnerResultServiceImplementation.class);

        try {

            ConnectionPoolRealization.getInstance().init(DB_DRIVER_CLASS,
                    DB_URL, DB_USER, DB_PASSWORD, DB_POOL_START_SIZE,
                    DB_POOL_MAX_SIZE);
        } catch (PersistentException e) {
            LOGGER.error("It is impossible to initialize application", e);
        }

    }

    private TransactionFactory factory;

    public ServiceFactoryRealization(
            final TransactionFactory curFactory) {
        this.factory = curFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Service> T getService(
            final Class<T> key) throws PersistentException {
        Class<? extends RaceServiceRealization> value = SERVICES.get(key);
        if (value != null) {
            try {
                ClassLoader classLoader = value.getClassLoader();
                Class<?>[] interfaces = {key};
                Transaction transaction = factory.createTransaction();
                RaceServiceRealization service
                        = value.getConstructor().newInstance();
                service.setTransaction(transaction);
                InvocationHandler handler
                        = new ServiceInvocationHandler(service);
                return (T) Proxy.newProxyInstance(classLoader,
                        interfaces, handler);
            } catch (IllegalAccessException | InstantiationException e) {
                LOGGER.error("It is impossible to instance service class", e);
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
        throw new PersistentException("Service class named "
                + key.getSimpleName() + " wasn't founded");
    }

    @Override
    public void close() {
        factory.close();
    }
}
