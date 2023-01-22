package by.max.coourseproject.server.dao.impl;

import by.max.coourseproject.server.dao.ConnectionPool;
import by.max.coourseproject.server.dao.OrderDAO;
import by.max.coourseproject.server.entity.Order;
import by.max.coourseproject.server.entity.User;

import java.sql.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SQLOrderDAO implements OrderDAO {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final static String CREATE_ORDER = "INSERT INTO \"Order\" (id_user, id_furniture, quantity, created_at) VALUES(?,?,?,?)";
    private final static String SHOW_USER_ORDERS = "SELECT \"Order\".id, F.name, F.sell_price, \"Order\".quantity from \"Order\" join Furniture F on F.id = \"Order\".id_furniture where id_user = ?";
    private final static String SHOW_ORDERS = "SELECT  u.name,  u.surname, \"Order\".id, F.name, F.sell_price, \"Order\".quantity from \"User\" u join \"Order\" on u.id = \"Order\".id_user join Furniture F on \"Order\".id_furniture = F.id";
    private final static String SHOW_ORDERS_EXPENSES = "SELECT F.price, \"Order\".quantity from \"Order\" join Furniture F on F.id = \"Order\".id_furniture";
    private final static String SHOW_ORDERS_PROFIT = "SELECT F.sell_price, \"Order\".quantity from \"Order\" join Furniture F on F.id = \"Order\".id_furniture";
    private final static String SHOW_ORDERS_EXPENSES_MONTH = "SELECT F.price, \"Order\".quantity, \"Order\".created_at from \"Order\" join Furniture F on F.id = \"Order\".id_furniture";
    private final static String SHOW_ORDERS_PROFIT_MONTH = "SELECT F.sell_price, \"Order\".quantity, \"Order\".created_at from \"Order\" join Furniture F on F.id = \"Order\".id_furniture";
    private final static String SHOW_ORDERS_PROFIT_EXPENSES = "SELECT F.price, F.sell_price, \"Order\".quantity, \"Order\".created_at from \"Order\" join Furniture F on F.id = \"Order\".id_furniture";


    @Override
    public Map<Integer, Map<String, String>> showUserOrders(int user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SHOW_USER_ORDERS);
            preparedStatement.setInt(1, user);
            resultSet = preparedStatement.executeQuery();
            Map<Integer, Map<String, String>> orderMap = new HashMap<>();
            Map<String, String> order = null;

            while(resultSet.next()){
                order = new HashMap<>();
                order.put("name", resultSet.getString("name"));
                order.put("price", resultSet.getString("sell_price"));
                order.put("quantity", resultSet.getString("quantity"));
                int finalPrice = (resultSet.getInt("sell_price") * resultSet.getInt("quantity"));
                order.put("finalPrice", String.valueOf(finalPrice));
                orderMap.put(resultSet.getInt("id"), order);
            }

            if(orderMap.isEmpty()){
                return Collections.emptyMap();
            }
            else{
                return orderMap;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return Collections.emptyMap();
    }

    @Override
    public Map<Integer, Map<String, String>> showOrders() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SHOW_ORDERS);
            resultSet = preparedStatement.executeQuery();
            Map<Integer, Map<String, String>> orderMap = new HashMap<>();
            Map<String, String> order = null;

            while(resultSet.next()){
                order = new HashMap<>();
                order.put("name", resultSet.getString(4));
                order.put("fullName", resultSet.getString(1) + " " + resultSet.getString(2));
                order.put("sell_price", resultSet.getString(5));
                order.put("quantity", resultSet.getString(6));
                int finalPrice = (resultSet.getInt(5) * resultSet.getInt(6));
                order.put("finalPrice", String.valueOf(finalPrice));
                System.out.println(order);
                orderMap.put(resultSet.getInt("id"), order);
            }

            if(orderMap.isEmpty()){
                return Collections.emptyMap();
            }
            else{
                return orderMap;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return Collections.emptyMap();
    }

    @Override
    public void createOrder(Map<String, String> order) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(CREATE_ORDER);
            preparedStatement.setInt(1, Integer.parseInt(order.get("id_user")));
            preparedStatement.setInt(2, Integer.parseInt(order.get("id_furniture")));
            preparedStatement.setInt(3, Integer.parseInt(order.get("quantity")));
            preparedStatement.setDate(4, new Date(System.currentTimeMillis()));
            preparedStatement.execute();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                connectionPool.closeConnection(connection, preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int calculateExpenses() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SHOW_ORDERS_EXPENSES);
            resultSet = preparedStatement.executeQuery();

            Integer finalPrice = 0;

            while(resultSet.next()){
                finalPrice += (resultSet.getInt("price") * resultSet.getInt("quantity"));

            }
            System.out.println(finalPrice);

            if(finalPrice == 0){
                return 0;
            }
            else{
                return finalPrice;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public int calculateProfit() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SHOW_ORDERS_PROFIT);
            resultSet = preparedStatement.executeQuery();

            Integer finalPrice = 0;

            while(resultSet.next()){

                finalPrice += (resultSet.getInt("sell_price") * resultSet.getInt("quantity"));

            }
            System.out.println(finalPrice);

            if(finalPrice == 0){
                return 0;
            }
            else{
                return finalPrice;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public int calculateExpensesByMonth() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SHOW_ORDERS_EXPENSES_MONTH);
            resultSet = preparedStatement.executeQuery();

            Integer finalPrice = 0;

            int monthNow = getMonth(new Date(System.currentTimeMillis()).toString());
            int month;

            while(resultSet.next()){
                month = getMonth(resultSet.getDate("created_at").toString());
                if(month == monthNow){
                    finalPrice += (resultSet.getInt("price") * resultSet.getInt("quantity"));
                }
            }
            System.out.println(finalPrice);

            if(finalPrice == 0){
                return 0;
            }
            else{
                return finalPrice;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public int calculateProfitByMonth() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SHOW_ORDERS_PROFIT_MONTH);
            resultSet = preparedStatement.executeQuery();

            Integer finalPrice = 0;

            int monthNow = getMonth(new Date(System.currentTimeMillis()).toString());
            int month;
            System.out.println(monthNow);

            while(resultSet.next()){
                month = getMonth(resultSet.getDate("created_at").toString());
                System.out.println(month);
                if(month == monthNow){
                    finalPrice += (resultSet.getInt("sell_price") * resultSet.getInt("quantity"));
                }
            }
            System.out.println(finalPrice);

            if(finalPrice == 0){
                return 0;
            }
            else{
                return finalPrice;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public int calculateExpensesByYear() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SHOW_ORDERS_EXPENSES_MONTH);
            resultSet = preparedStatement.executeQuery();

            Integer finalPrice = 0;

            int yearNow = getYear(new Date(System.currentTimeMillis()).toString());
            int year;

            while(resultSet.next()){
                year = getYear(resultSet.getDate("created_at").toString());
                if(year == yearNow){
                    finalPrice += (resultSet.getInt("price") * resultSet.getInt("quantity"));
                }
            }
            System.out.println(finalPrice);

            if(finalPrice == 0){
                return 0;
            }
            else{
                return finalPrice;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public int calculateProfitByYear() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SHOW_ORDERS_PROFIT_MONTH);
            resultSet = preparedStatement.executeQuery();

            Integer finalPrice = 0;

            int yearNow = getYear(new Date(System.currentTimeMillis()).toString());
            int year;
            System.out.println(yearNow);

            while(resultSet.next()){
                year = getYear(resultSet.getDate("created_at").toString());
                System.out.println(year);
                if(year == yearNow){
                    finalPrice += (resultSet.getInt("sell_price") * resultSet.getInt("quantity"));
                }
            }
            System.out.println(finalPrice);

            if(finalPrice == 0){
                return 0;
            }
            else{
                return finalPrice;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public Map<String, Integer> calculateProfitByAllMonths() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SHOW_ORDERS_PROFIT_EXPENSES);
            resultSet = preparedStatement.executeQuery();

            Map<String, Integer> map = new HashMap<>();
            int month;
            int sum;
            int money;
            int profit = 0;
            int expenses = 0;
            int yearNow = getYear(new Date(System.currentTimeMillis()).toString());
            int year;
            while(resultSet.next()){
                year = getYear(resultSet.getDate("created_at").toString());
                if(year == yearNow){
                    month = getMonth(resultSet.getDate("created_at").toString());
                    profit = (resultSet.getInt("sell_price") * resultSet.getInt("quantity"));
                    expenses = (resultSet.getInt("price") * resultSet.getInt("quantity"));
                    money = profit - expenses;
                    switch (month){
                        case 1:{
                            if(map.containsKey("January")){
                                sum = map.get("January") + money;
                                map.put("January", sum);
                            }
                            else {
                                map.put("January", money);
                            }
                        }
                        break;
                        case 2:{

                            if(map.containsKey("February")){
                                sum = map.get("February") + money;
                                map.put("February", sum);
                            }
                            else {
                                map.put("February", money);
                            }
                        }
                        break;
                        case 3:{
                            if(map.containsKey("March")){
                                sum = map.get("March") + money;
                                map.put("March", sum);
                            }
                            else {
                                map.put("March", money);
                            }
                        }
                        break;
                        case 4:{
                            if(map.containsKey("April")){
                                sum = map.get("April") + money;
                                map.put("April", sum);
                            }
                            else {
                                map.put("April", money);
                            }
                        }
                        break;
                        case 5:{
                            if(map.containsKey("May")){
                                sum = map.get("May") + money;
                                map.put("May", sum);
                            }
                            else {
                                map.put("May", money);
                            }
                        }
                        break;
                        case 6:{
                            if(map.containsKey("June")){
                                sum = map.get("June") + money;
                                map.put("June", sum);
                            }
                            else {
                                map.put("June", money);
                            }
                        }
                        break;
                        case 7:{
                            if(map.containsKey("July")){
                                sum = map.get("July") + money;
                                map.put("July", sum);
                            }
                            else {
                                map.put("July", money);
                            }
                        }
                        break;
                        case 8:{
                            if(map.containsKey("August")){
                                sum = map.get("August") + money;
                                map.put("August", sum);
                            }
                            else {
                                map.put("August", money);
                            }
                        }
                        break;
                        case 9:{
                            if(map.containsKey("September")){
                                sum = map.get("September") + money;
                                map.put("September", sum);
                            }
                            else {
                                map.put("September", money);
                            }
                        }
                        break;
                        case 10:{
                            if(map.containsKey("October")){
                                sum = map.get("October") + money;
                                map.put("October", sum);
                            }
                            else {
                                map.put("October", money);
                            }
                        }
                        break;
                        case 11:{
                            if(map.containsKey("November")){
                                sum = map.get("November") + money;
                                map.put("November", sum);
                            }
                            else {
                                map.put("November", money);
                            }
                        }
                        break;
                        case 12:{
                            if(map.containsKey("December")){
                                sum = map.get("December") + money;
                                map.put("December", sum);
                            }
                            else {
                                map.put("December", money);
                            }
                        }
                        break;
                    }
                }
            }

            if(map.isEmpty()){
                return Collections.emptyMap();
            }
            else{
                return map;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return Collections.emptyMap();
    }

    int getYear(String date){
        String [] mas = date.split("-");
        return Integer.parseInt(mas[0]);
    }

    int getMonth(String date){
        String [] mas = date.split("-");
        System.out.println(mas[1]);
        return Integer.parseInt(mas[1]);
    }
}
