package com.lab1.DAO.impl;

import com.lab1.ConnectionPool.ConnectionPool;
import com.lab1.DAO.ReceiptTableDAO;
import com.lab1.Types.Pair;
import com.lab1.entities.Product;
import com.lab1.entities.Receipt;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReceiptTableImpl implements ReceiptTableDAO {
    private Connection connection = null;
    private final int ID_COLUMN = 1;
    private final int AMOUNT_COLUMN = 2;
    private final int PRODUCT_ID_COLUMN = 3;

    public ReceiptTableImpl(){
        connection = ConnectionPool.GetInstance().GetConnection();
    }

    public boolean releaseConnection(){
        return ConnectionPool.GetInstance().releaseConnection(connection);
    }

    private Pair<Product, Integer> parse(ResultSet resultSet) throws SQLException{
        int productId = resultSet.getInt(PRODUCT_ID_COLUMN);
        int amount = resultSet.getInt(AMOUNT_COLUMN);

        ProductTableImpl productTable = new ProductTableImpl();
        Product product = productTable.findById(productId);
        productTable.releaseConnection();

        return Pair.of(product, amount);
    }

    @Override
    public List<Receipt> findAll() {
        String query = "select distinct(id) from receipt;";
        List<Receipt> receipts = new ArrayList();
        try{
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            while(resultSet.next()){
                int id = resultSet.getInt(ID_COLUMN);
                receipts.add(findById(id));
            }
        }catch(SQLException e){
            System.err.println("receipt:findAll: " + e.getMessage());
        }
        return receipts;
    }

    @Override
    public Receipt findById(int id) {
        String query = String.format("select * from receipt where id=%d", id);
        Receipt receipt = new Receipt(id);

        try{
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            while(resultSet.next()) {
                Pair<Product, Integer> pair = parse(resultSet);
                receipt.AddProduct(pair.first, pair.second);
            }
        }catch(SQLException e){
            System.err.println("receipt:findById: "+e.getMessage());
        }

        return receipt;
    }

    @Override
    public void create(Receipt receipt) {
        List<Pair<Product, Integer>> products = receipt.getProducts();
        List<String> queries = new ArrayList<>();
        for(int i=0; i<products.size(); ++i) {
            queries.add(String.format("insert into receipt(id, amount, productId) values (%d,%d,%d)",
                    receipt.getId(), products.get(i).second, products.get(i).first.getId()));
        }

        try{
            for(String query : queries) {
                connection.createStatement().executeUpdate(query);
            }
        }catch(SQLException e){
            System.err.println("receipt:create: "+e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String query = String.format("delete from receipt where id=%d", id);
        try{
            connection.createStatement().executeUpdate(query);
        }catch(SQLException e){
            System.err.println("receipt:delete " + e.getMessage());
        }
    }
}
