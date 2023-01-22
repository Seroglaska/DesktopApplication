package by.max.coourseproject.server.service;

import by.max.coourseproject.server.entity.User;

import java.util.Map;

public interface UserService {
    Map<String, String> findUser(Map<String, String> map);
    Map<String, String> findUser(int user);
    Map<Integer, Map<String, String>> findUsers();
    void addUser(Map<String,String> user);
    void deleteUser(int id);
    void updateUser(Map<String,String> user);
    int calculateProfit();
}
