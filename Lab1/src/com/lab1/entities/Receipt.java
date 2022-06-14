package com.lab1.entities;

import com.lab1.DAO.impl.ReceiptTableImpl;
import com.lab1.Types.Pair;

import java.util.ArrayList;
import java.util.List;


public class Receipt {
    private static int id=0;
    private int myId;
    private List<Pair<Product, Integer>> products = new ArrayList<>();

    public Receipt(int id){
        myId = id;
    }

    public Receipt(){
        myId = id++;
    }

    public void AddProduct(Product product, int amount) {
        for(int i=0; i<products.size(); ++i){
            if(products.get(i).first.equal(product)){
                int n = products.get(i).second;
                products.get(i).second = n + amount;
                return;
            }
        }
        products.add(Pair.of(product, amount));
    }
    public void CancelProduct(int id){
        for(int i=0;i<products.size(); ++i){
            if(products.get(i).first.getId() == id){
                if(products.get(i).second == 1) {products.remove(i);}
                else {products.get(i).second--;}
                return;
            }
        }
    }

    public void Close() {
        ReceiptTableImpl receiptTable = new ReceiptTableImpl();
        receiptTable.create(this);
        receiptTable.releaseConnection();
    }

    int getTotalPrice(){
        int sum=0;
        for(int i=0;i<products.size();++i){
            sum += products.get(i).first.getPrice() * products.get(i).second;
        }
        return sum;
    }

    public int getId() {
        return myId;
    }

    void setId(int id){
        myId = id;
    }

    public List<Pair<Product, Integer>> getProducts() {
        return products;
    }

    public void setProducts(List<Pair<Product, Integer>> products) {
        this.products = products;
    }

    public String toString() {
        String str="Receipt #"+myId+":\n";
        for(int i=0; i<products.size(); ++i){
            str += i+1+".\tamount="+products.get(i).second +"\n\t"+ products.get(i).first.toString() + "\n";
        }
        return str;
    }
}
