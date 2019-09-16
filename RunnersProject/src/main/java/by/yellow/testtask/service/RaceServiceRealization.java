package by.yellow.testtask.service;

import by.yellow.testtask.model.dao.Transaction;

abstract class RaceServiceRealization implements Service {
    Transaction transaction = null;

    void setTransaction(final Transaction curTransaction) {
        this.transaction = curTransaction;
    }
}
