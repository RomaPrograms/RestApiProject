package by.yellow.testtask.service;

import by.yellow.testtask.exception.PersistentException;

public interface ServiceFactory {
    <T extends Service> T getService(
            Class<T> key) throws PersistentException;

    void close();
}
