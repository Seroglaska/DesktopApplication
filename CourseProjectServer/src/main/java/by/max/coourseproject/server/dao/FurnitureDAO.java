package by.max.coourseproject.server.dao;

import java.util.Map;

public interface FurnitureDAO {
    Map<Integer, Map<String, String>> showFurniture();
    void deleteFurniture(int id);
    void updateFurniture(Map<String,String> furniture);
    void addFurniture(Map<String, String> furniture);
    int checkQuantity(int furniture, int quantity);
    void editQuantity(int furniture, int quantity);
    int calculateExpenses();
}
