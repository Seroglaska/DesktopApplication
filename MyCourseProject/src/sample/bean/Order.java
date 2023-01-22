package sample.bean;

import javafx.beans.property.SimpleStringProperty;

public class Order {
    int id;
    private SimpleStringProperty name;
    private SimpleStringProperty fullName;
    private SimpleStringProperty price;
    private SimpleStringProperty quantity;
    private SimpleStringProperty finalPrice;




    public Order(int id, String name, String price, String quantity, String finalPrice){
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleStringProperty(price);
        this.quantity = new SimpleStringProperty(quantity);
        this.finalPrice = new SimpleStringProperty(finalPrice);
    }

    public Order(int id, String fullName, String name, String price, String quantity, String finalPrice){
        this.id = id;
        this.fullName = new SimpleStringProperty(fullName);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleStringProperty(price);
        this.quantity = new SimpleStringProperty(quantity);
        this.finalPrice = new SimpleStringProperty(finalPrice);
    }

    public String getFullName() {
        return fullName.get();
    }

    public SimpleStringProperty fullNameProperty() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName.set(fullName);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPrice() {
        return price.get();
    }

    public SimpleStringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public String getQuantity() {
        return quantity.get();
    }

    public SimpleStringProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity.set(quantity);
    }

    public String getFinalPrice() {
        return finalPrice.get();
    }

    public SimpleStringProperty finalPriceProperty() {
        return finalPrice;
    }

    public void setFinalPrice(String finalPrice) {
        this.finalPrice.set(finalPrice);
    }
}
