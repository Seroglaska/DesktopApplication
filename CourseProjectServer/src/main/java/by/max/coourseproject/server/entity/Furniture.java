package by.max.coourseproject.server.entity;

import java.io.Serializable;
public class Furniture implements Serializable {

    private static final long serialVersionUID = 362498820763181265L;
    public Furniture(){

    }

    private int id;
    private String name;
    private int price;
    private int sell_price;
    private int quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSell_price() {
        return sell_price;
    }

    public void setSell_price(int sell_price) {
        this.sell_price = sell_price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
