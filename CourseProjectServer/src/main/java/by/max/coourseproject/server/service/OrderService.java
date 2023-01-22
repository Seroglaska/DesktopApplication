package by.max.coourseproject.server.service;

import java.util.Map;

public interface OrderService {
    Map<Integer, Map<String, String>> showUserOrders(int user);
    Map<Integer, Map<String, String>> showOrders();
    boolean createOrder(Map<String, String> order);
    int calculateProfitByYear();
    int calculateProfitByMonth();
    Map<String, Integer> calculateProfitByAllMonths();
}
