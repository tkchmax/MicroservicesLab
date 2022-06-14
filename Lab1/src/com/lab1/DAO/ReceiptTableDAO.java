package com.lab1.DAO;

import com.lab1.entities.Receipt;

import java.util.List;

/*create table receipt (
        id int not null,
        amount int not null,
        productId int not null,
        foreign key(productId) references products(id)
);*/

public interface ReceiptTableDAO {
    List<Receipt> findAll();
    Receipt findById(int id);
    void create(Receipt receipt);
    void delete(int id);

}
