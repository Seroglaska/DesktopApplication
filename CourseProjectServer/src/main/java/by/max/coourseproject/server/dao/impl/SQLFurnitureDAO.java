package by.max.coourseproject.server.dao.impl;


import by.max.coourseproject.server.dao.FurnitureDAO;

import by.max.coourseproject.server.dao.FurnitureDAO;
import by.max.coourseproject.server.dao.ConnectionPool;
import by.max.coourseproject.server.entity.Furniture;

import java.sql.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
public class SQLFurnitureDAO implements FurnitureDAO {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();


    private static final String SHOW_FURNITURE = " SELECT * FROM Furniture ";
    private static final String DELETE_FURNITURE = "delete FROM Furniture WHERE id=?";
    private static final String UPDATE_FURNITURE = "update Furniture set price = ?, sell_price = ?, name = ?, quantity = ? WHERE id=?";
    private static final String ADD_FURNITURE = "insert into Furniture (name, price, sell_price, quantity) values (?,?,?,?)";
    private static final String SHOW_FURNITURE_QUANTITY = "SELECT quantity FROM Furniture WHERE id = ?";
    private static final String UPDATE_FURNITURE_QUANTITY = "update Furniture set quantity = ? WHERE id=?";
    private static final String SHOW_FURNITURE_EXPENSES = " SELECT price, quantity FROM Furniture ";

    @Override
    public Map<Integer, Map<String, String>> showFurniture() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Map<Integer, Map<String, String>> map = null;
        Furniture furniture = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SHOW_FURNITURE);
            resultSet = preparedStatement.executeQuery();
            map = new HashMap<>();
            while (resultSet.next()){
                furniture = new Furniture();

                furniture.setId(resultSet.getInt("id"));
                furniture.setName(resultSet.getString("name"));
                furniture.setPrice(resultSet.getInt("price"));
                furniture.setSell_price(resultSet.getInt("sell_price"));
                furniture.setQuantity(resultSet.getInt("quantity"));
                map.put(furniture.getId(), createFurnitureMap(furniture));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            try {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(map.isEmpty()){
            return Collections.emptyMap();
        }
        else{
            return map;
        }
    }

    @Override
    public void deleteFurniture(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(DELETE_FURNITURE);
            System.out.println(id);
            preparedStatement.setInt(1, id);
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
    public void updateFurniture(Map<String,String> furniture) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(UPDATE_FURNITURE);
            preparedStatement.setInt(1, Integer.parseInt(furniture.get("price")));
            preparedStatement.setInt(2, Integer.parseInt(furniture.get("sellPrice")));
            preparedStatement.setString(3, furniture.get("name"));
            preparedStatement.setInt(4, Integer.parseInt(furniture.get("quantity")));
            System.out.println(Integer.parseInt(furniture.get("id")));
            preparedStatement.setInt(5, Integer.parseInt(furniture.get("id")));
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
    public void addFurniture(Map<String, String> furniture) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(ADD_FURNITURE);
            preparedStatement.setString(1, furniture.get("name"));
            preparedStatement.setInt(2, Integer.parseInt(furniture.get("price")));
            preparedStatement.setInt(3, Integer.parseInt(furniture.get("sellPrice")));
            preparedStatement.setInt(4, Integer.parseInt(furniture.get("quantity")));
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
    public int checkQuantity(int furniture, int quantity) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int quantityFromDb = 0;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SHOW_FURNITURE_QUANTITY);
            preparedStatement.setInt(1, furniture);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return -1;
            }
            quantityFromDb = resultSet.getInt("quantity");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return (quantityFromDb - quantity);
    }

    @Override
    public void editQuantity(int furniture, int quantity) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(UPDATE_FURNITURE_QUANTITY);
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, furniture);
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
            preparedStatement = connection.prepareStatement(SHOW_FURNITURE_EXPENSES);
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

    private Map<String, String> createFurnitureMap(Furniture furniture){
        Map<String, String> furnitureMap = new HashMap<>();
        furnitureMap.put("id", String.valueOf(furniture.getId()));
        furnitureMap.put("name", furniture.getName());
        furnitureMap.put("price", String.valueOf(furniture.getPrice()));
        furnitureMap.put("sellPrice", String.valueOf(furniture.getSell_price()));
        furnitureMap.put("quantity", String.valueOf(furniture.getQuantity()));
        return furnitureMap;
    }
}
