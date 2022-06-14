package com.lab1.ConnectionPool;

import java.sql.Connection;

public interface IConnectionPool {
    Connection GetConnection();
    boolean releaseConnection(Connection connection);
}
