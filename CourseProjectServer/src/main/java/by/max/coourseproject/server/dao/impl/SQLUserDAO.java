package by.max.coourseproject.server.dao.impl;

import by.max.coourseproject.server.dao.ConnectionPool;
import by.max.coourseproject.server.dao.UserDAO;
import by.max.coourseproject.server.entity.User;

import java.sql.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SQLUserDAO implements UserDAO {
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String FIND_USER = "SELECT * FROM \"User\" WHERE login=?";
    private static final String FIND_USER_BY_ID = "SELECT * FROM \"User\" WHERE id=?";
    private static final String FIND_USERS = " SELECT * FROM \"User\" ";
    private static final String DELETE_USER = "delete FROM \"User\" WHERE id=?";
    private static final String UPDATE_USER = "update \"User\" set password = ?, login = ?, year_of_birth = ?, name = ?, surname = ?, email = ? WHERE id=?";
    private static final String ADD_USER = "insert into \"User\"(password, login, year_of_birth, name, surname, email, role) values (?,?,?,?,?,?,?)";


    @Override
    public Map<String, String> findUser(Map<String, String> map) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(FIND_USER);
            preparedStatement.setString(1, map.get("login"));
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return Collections.emptyMap();
            }


            if (map.get("password").equals(resultSet.getString("password"))) {
                User user = new User();

                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setYearOfBirth(resultSet.getInt("year_of_birth"));
                user.setEmail(resultSet.getString("email"));
                user.setRole(resultSet.getString("role"));
                Map<String, String> userMap = createUserMap(user);
                userMap.put("role", user.getRole());
                return userMap;
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
    public Map<String, String> findUser(int userID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(FIND_USER_BY_ID);
            preparedStatement.setInt(1, userID);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return Collections.emptyMap();
            }
            else {
                User user = new User();

                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setYearOfBirth(resultSet.getInt("year_of_birth"));
                user.setEmail(resultSet.getString("email"));
                return createUserMap(user);
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
    public Map<Integer, Map<String, String>> findUsers() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Map<Integer, Map<String, String>> map = null;
        User user = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(FIND_USERS);
            resultSet = preparedStatement.executeQuery();
            map = new HashMap<>();
            while (resultSet.next()){
                user = new User();

                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setYearOfBirth(resultSet.getInt("year_of_birth"));
                user.setEmail(resultSet.getString("email"));
                map.put(user.getId(), createUserMap(user));
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
    public void deleteUser(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(DELETE_USER);
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
    public void updateUser(Map<String,String> user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(UPDATE_USER);
            preparedStatement.setString(1, user.get("password"));
            preparedStatement.setString(2, user.get("login"));
            preparedStatement.setInt(3, Integer.parseInt(user.get("yearOfBirth")));
            preparedStatement.setString(4, user.get("name"));
            preparedStatement.setString(5, user.get("surname"));
            preparedStatement.setString(6, user.get("email"));
            System.out.println(Integer.parseInt(user.get("id")));
            preparedStatement.setInt(7, Integer.parseInt(user.get("id")));
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
    public void addUser(Map<String, String> user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(ADD_USER);
            preparedStatement.setString(1, user.get("password"));
            preparedStatement.setString(2, user.get("login"));
            preparedStatement.setInt(3, Integer.parseInt(user.get("yearOfBirth")));
            preparedStatement.setString(4, user.get("name"));
            preparedStatement.setString(5, user.get("surname"));
            preparedStatement.setString(6, user.get("email"));
            preparedStatement.setString(7, "user");
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



    private Map<String, String> createUserMap(User user){
        Map<String, String> userMap = new HashMap<>();
        userMap.put("id", String.valueOf(user.getId()));
        userMap.put("password", user.getPassword());
        userMap.put("login", user.getLogin());
        userMap.put("yearOfBirth", String.valueOf(user.getYearOfBirth()));
        userMap.put("email", user.getEmail());
        userMap.put("name", user.getName());
        userMap.put("surname", user.getSurname());
        return userMap;
    }
}

