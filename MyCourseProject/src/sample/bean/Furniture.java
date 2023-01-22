package sample.bean;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Furniture {
    private int id;
    private SimpleStringProperty name;
    private SimpleStringProperty sellPrice;
    private SimpleStringProperty price;
    private SimpleStringProperty quantity;




    public Furniture (int id, String name, String sellPrice, String price, String quantity){
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.sellPrice = new SimpleStringProperty(sellPrice);
        this.price = new SimpleStringProperty(price);
        this.quantity = new SimpleStringProperty(quantity);
    }

    public Furniture(int id, String name, String sellPrice, String quantity) {
        this.id = id;
        this.name = new SimpleStringProperty(name);;
        this.sellPrice = new SimpleStringProperty(sellPrice);;
        this.quantity = new SimpleStringProperty(quantity);;
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

    public String getSellPrice() {
        return sellPrice.get();
    }

    public SimpleStringProperty sellPriceProperty() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice.set(sellPrice);
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
}
