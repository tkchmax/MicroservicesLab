package com.lab1.ConnectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool implements IConnectionPool{
    private static final String URL = "jdbc:mysql://localhost:3306/cashbox";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final int INITIAL_POOL_SIZE = 10;
    private static List<Connection> avaliableConnections = new ArrayList<>();
    private static List<Connection> usedConnections = new ArrayList<>();
    private static ConnectionPool instance;

    @Override
    public Connection GetConnection() {
        Connection connection = avaliableConnections.remove(avaliableConnections.size()-1);
        usedConnections.add(connection);
        return connection;
    }

    @Override
    public boolean releaseConnection(Connection connection) {
        avaliableConnections.add(connection);
        return usedConnections.remove(connection);
    }

    private ConnectionPool(List<Connection> pool) {
        ConnectionPool.avaliableConnections = pool;
    }


    private static ConnectionPool CreateConnectionPool() throws SQLException {
        List<Connection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
        for(int i=0; i<INITIAL_POOL_SIZE; i++){
            pool.add(DriverManager.getConnection(URL, USER, PASSWORD));
        }
        return new ConnectionPool(pool);
    }

    public static ConnectionPool GetInstance(){
        if(instance == null){
            try {
                instance = ConnectionPool.CreateConnectionPool();
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return instance;
    }
}

