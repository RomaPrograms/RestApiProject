package by.yellow.testtask.service;

import by.yellow.testtask.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ServiceInvocationHandler implements InvocationHandler {
    private static final Logger LOGGER
            = LogManager.getLogger(ServiceInvocationHandler.class);

    private RaceServiceRealization service;

    ServiceInvocationHandler(
            final RaceServiceRealization serviceRealization) {
        this.service = serviceRealization;
    }

    @Override
    public Object invoke(final Object proxy, final Method method,
                         final Object[] args) throws Throwable {
        try {
            Object result = method.invoke(service, args);
            service.transaction.commit();
            return result;
        } catch (PersistentException e) {
            rollback();
            throw e;
        } catch (InvocationTargetException e) {
            rollback();
            throw e.getCause();
        }
    }

    private void rollback() {
        try {
            service.transaction.rollback();
        } catch (PersistentException e) {
            LOGGER.warn("It is impossible to rollback transaction", e);
        }
    }
}
