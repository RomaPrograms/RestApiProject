package by.yellow.testtask.model.dao;

import by.yellow.testtask.exception.PersistentException;

public interface TransactionFactory {
    Transaction createTransaction() throws PersistentException;

    void close();
}
