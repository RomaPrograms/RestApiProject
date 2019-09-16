package by.yellow.testtask.model.mysql;

import java.sql.Connection;

abstract class BaseDaoRealization {
    private Connection connection;

    void setConnection(Connection curConnection) {
        this.connection = curConnection;
    }

    Connection getConnection() {
        return connection;
    }
}
