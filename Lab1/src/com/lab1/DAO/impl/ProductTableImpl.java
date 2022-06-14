package com.lab1.DAO.impl;

import com.lab1.ConnectionPool.ConnectionPool;
import com.lab1.DAO.ProductTableDAO;
import com.lab1.entities.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductTableImpl implements ProductTableDAO {

    private Connection connection = null;
    private final int ID_COLUMN = 1;
    private final int NAME_COLUMN = 2;
    private final int PRICE_COLUMN = 3;

    public ProductTableImpl(){
        connection = ConnectionPool.GetInstance().GetConnection();
    }

    public boolean releaseConnection(){
        return ConnectionPool.GetInstance().releaseConnection(connection);
    }

    private Product parse(ResultSet resultSet) throws SQLException {
        return new Product(
                resultSet.getInt(ID_COLUMN),
                resultSet.getString(NAME_COLUMN),
                resultSet.getInt(PRICE_COLUMN)
        );
    }

    @Override
    public List<Product> findAll() {
        String query = "select * from products;";
        List<Product> products = new ArrayList();
        try{
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            while(resultSet.next()){
                products.add(parse(resultSet));
            }
        }catch(SQLException e){
            System.err.println("product:findAll: " + e.getMessage());
        }
        return products;
    }

    @Override
    public Product findById(long id) {
        String query = "select * from products where id="+id+";";
        Product product = null;
        try{
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            if(resultSet.next()) {
                product = parse(resultSet);
            }
        }catch(SQLException e){
            System.err.println("product:findById: "+e.getMessage());
        }
        return product;
    }

    @Override
    public void create(int id, String name, int price) {
        String query = String.format("insert into products(id, name, price) values (%d, \"%s\", %d)",
                id, name, price);
        try{
            connection.createStatement().executeUpdate(query);
        }catch(SQLException e){
            System.err.println("product:create: "+e.getMessage());
        }
    }

    @Override
    public void create(Product product){
        create(product.getId(), product.getName(), product.getPrice());
    }


    @Override
    public void updateName(int id, String newName) {
        String query = String.format("update products set name='%s' where id=%d", newName, id);
        try{
            connection.createStatement().executeUpdate(query);
        }catch(SQLException e){
            System.err.println("product:deleteById: "+e.getMessage());
        }
    }

    @Override
    public void updatePrice(int id, int newPrice) {
        String query = String.format("update products set price=%d where id=%d", newPrice, id);
        try{
            connection.createStatement().executeUpdate(query);
        }catch(SQLException e){
            System.err.println("product:deleteById: "+e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String query = String.format("delete from products where id=%d", id);
        try{
            connection.createStatement().executeUpdate(query);
        }catch(SQLException e){
            System.err.println("product:deleteById: "+e.getMessage());
        }
    }

    @Override
    public void delete(String name) {
        String query = String.format("delete from products where name=\"%s\"", name);
        try{
            connection.createStatement().executeUpdate(query);
        }catch(SQLException e){
            System.err.println("product:deleteByName: "+e.getMessage());
        }
    }
}
