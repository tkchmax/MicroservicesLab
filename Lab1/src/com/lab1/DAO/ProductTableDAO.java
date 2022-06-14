package com.lab1.DAO;

import com.lab1.entities.Product;
import java.util.List;

/*create table products (
    id int primary key not null,
    name varchar(255) not null,
    price int not null
);*/

public interface ProductTableDAO {
    List<Product> findAll();
    Product findById(long id);
    void create(int id, String name, int price);
    void create(Product product);
    void updateName(int id, String newName);
    void updatePrice(int id, int newPrice);
    void delete(int id);
    void delete(String name);
}
