package by.max.coourseproject.server.dao;


import by.max.coourseproject.server.dao.impl.SQLFurnitureDAO;
import by.max.coourseproject.server.dao.impl.SQLOrderDAO;
import by.max.coourseproject.server.dao.impl.SQLUserDAO;

/**
 * The ${@code DAOProvider} class provides implementations of DAO-level interfaces
 */
public class DAOProvider {
    private static final DAOProvider instance = new DAOProvider();
    private final UserDAO userDAOImpl = new SQLUserDAO();
    private final FurnitureDAO furnitureDAO = new SQLFurnitureDAO();
    private final OrderDAO orderDAO = new SQLOrderDAO();


    private DAOProvider() {
    }

    public static DAOProvider getInstance() {
        return instance;
    }

    public OrderDAO getOrderDAO() {
        return orderDAO;
    }

    public UserDAO getUserDAO() {
        return userDAOImpl;
    }

    public FurnitureDAO getFurnitureDAO() {
        return furnitureDAO;
    }
}
