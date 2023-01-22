package by.max.coourseproject.server.dao;

import java.util.Map;

public interface OrderDAO {
    Map<Integer, Map<String, String>> showUserOrders(int user);
    Map<Integer, Map<String, String>> showOrders();
    void createOrder(Map<String, String> order);
    int calculateExpenses();
    int calculateProfit();
    int calculateExpensesByMonth();
    int calculateProfitByMonth();
    int calculateExpensesByYear();
    int calculateProfitByYear();
    Map<String, Integer> calculateProfitByAllMonths();
}
