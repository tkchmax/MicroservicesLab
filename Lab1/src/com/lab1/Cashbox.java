package com.lab1;

import com.lab1.DAO.impl.ProductTableImpl;
import com.lab1.DAO.impl.ReceiptTableImpl;
import com.lab1.entities.Product;
import com.lab1.entities.Receipt;

import java.util.List;

public class Cashbox {
    public static void main(String[] args){
        ProductTableImpl productTable = new ProductTableImpl();
        ReceiptTableImpl receiptTable = new ReceiptTableImpl();

        Product p1 = new Product(101, "bread", 30);
        Product p2 = new Product(201,"cheese", 52);
        Product p3 = new Product(202, "milk", 29);
        productTable.create(p1);
        productTable.create(p2);
        productTable.create(p3);
        List<Product> products = productTable.findAll();
        for (Product p : products) {System.out.println(p.toString());}
        System.out.println("---------------");

        Receipt receipt = new Receipt();
        int id = receipt.getId();
        receipt.AddProduct(p1, 1);
        receipt.AddProduct(p2, 2);
        receipt.AddProduct(p3, 3);
        receipt.Close();

        Receipt receipt_ = receiptTable.findById(id);
        System.out.println(receipt_.toString());
        System.out.println("---------------");


        Receipt receipt2 = new Receipt();
        int id2 = receipt2.getId();
        receipt2.AddProduct(p1, 10);
        receipt2.AddProduct(p2, 20);
        receipt2.AddProduct(p3, 30);
        receipt2.Close();

        List<Receipt> receipts = receiptTable.findAll();
        for(Receipt r : receipts) {System.out.println(r.toString());}

        productTable.updateName(101, "adasda");
        productTable.updatePrice(101, 100);

        receiptTable.delete(id);
        receiptTable.delete(id2);
        productTable.delete(101);
        productTable.delete(201);
        productTable.delete(202);


    }
}
