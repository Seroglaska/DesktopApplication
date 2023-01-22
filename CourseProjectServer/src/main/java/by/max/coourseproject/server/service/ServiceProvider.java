package by.max.coourseproject.server.service;

import by.max.coourseproject.server.service.impl.FurnitureImpl;
import by.max.coourseproject.server.service.impl.OrderImpl;
import by.max.coourseproject.server.service.impl.UserImpl;


/**
 * The ${@code ServiceProvider} class provides implementations of service-level interfaces
 */
public class ServiceProvider {
    private static final ServiceProvider instance = new ServiceProvider();
    private final UserService userService = new UserImpl();
    private final FurnitureService furnitureService = new FurnitureImpl();
    private final OrderService orderService = new OrderImpl();

    private ServiceProvider() {
    }

    public static ServiceProvider getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return userService;
    }

    public FurnitureService getFurnitureService() {
        return furnitureService;
    }

    public OrderService getOrderService() {
        return orderService;
    }
}

