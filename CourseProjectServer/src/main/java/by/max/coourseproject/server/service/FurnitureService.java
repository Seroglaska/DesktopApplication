package by.max.coourseproject.server.service;

import java.util.Map;

public interface FurnitureService {
    Map<Integer, Map<String, String>> showFurniture();
    void deleteFurniture(int id);
    void addFurniture(Map<String,String> furniture);
    void updateFurniture(Map<String,String> furniture);
    int checkQuantity(int furniture, int quantity);
    void editQuantity(int furniture, int quantity);
}
