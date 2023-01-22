package by.max.coourseproject.server.controller;

import by.max.coourseproject.server.entity.User;
import by.max.coourseproject.server.main.Server;
import by.max.coourseproject.server.service.FurnitureService;
import by.max.coourseproject.server.service.OrderService;
import by.max.coourseproject.server.service.ServiceProvider;
import by.max.coourseproject.server.service.UserService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

public class Controller {
    private final UserService userService = ServiceProvider.getInstance().getUserService();
    private final FurnitureService furnitureService = ServiceProvider.getInstance().getFurnitureService();
    private final OrderService orderService = ServiceProvider.getInstance().getOrderService();

    public void control(ObjectInputStream ois, ObjectOutputStream oos, Socket socket) throws IOException, InterruptedException {
        boolean t = true;
        while(t){
            try {
                String message = (String)ois.readObject();
                switch(message){
                    case "AUTHENTICATION":{
                            Map<String, String> map = (HashMap) ois.readObject();
                            System.out.println(userService.findUser(map));
                            oos.writeObject(userService.findUser(map));
                        }
                        break;
                    case "ADD_USER":
                    case "REGISTRATION":
                    {
                        Map<String, String> map = (HashMap) ois.readObject();
                        userService.addUser(map);
                    }
                    break;
                    case "FIND_USER":
                    {
                        int user = (Integer) ois.readObject();
                        System.out.println(userService.findUser(user));
                        oos.writeObject(userService.findUser(user));
                    }
                    break;
                    case "FIND_USERS":
                    {
                        oos.writeObject(userService.findUsers());
                    }
                    break;
                    case "UPDATE_USER":
                    {
                        Map<String, String> user = (Map) ois.readObject();
                        System.out.println(user);
                        userService.updateUser(user);

                    }
                    break;
                    case "DELETE_USER":
                    {
                        int user = (Integer) ois.readObject();
                        userService.deleteUser(user);
                    }
                    break;
                    case "DELETE_FURNITURE":
                    {
                        int furniture = (Integer) ois.readObject();
                        furnitureService.deleteFurniture(furniture);
                    }
                    break;
                    case "ADD_FURNITURE":
                    {
                        Map<String, String> map = (HashMap) ois.readObject();
                        furnitureService.addFurniture(map);
                    }
                    break;
                    case "UPDATE_FURNITURE":
                    {
                        Map<String, String> furniture = (Map) ois.readObject();
                        System.out.println(furniture);
                        furnitureService.updateFurniture(furniture);

                    }
                    break;
                    case "FIND_FURNITURE":
                    {
                        oos.writeObject(furnitureService.showFurniture());
                    }
                    break;
                    case "CREATE_ORDER":
                    {
                        Map<String, String> order = (HashMap) ois.readObject();
                        int quantity = furnitureService.checkQuantity(Integer.parseInt(order.get("id_furniture")), Integer.parseInt(order.get("quantity")));
                        if(quantity < 0){
                            oos.writeObject(new Boolean(false));
                        }
                        else{
                            furnitureService.editQuantity(Integer.parseInt(order.get("id_furniture")) ,quantity);
                            oos.writeObject(new Boolean(orderService.createOrder(order)));
                        }
                    }
                    break;
                    case "FIND_USER_ORDERS":
                    {
                        int user = (Integer) ois.readObject();
                        oos.writeObject(orderService.showUserOrders(user));
                    }
                    break;
                    case "FIND_ORDERS":
                    {
                        oos.writeObject(orderService.showOrders());
                    }
                    break;
                    case "DO_TASK":
                    {
                        String choice = (String)ois.readObject();
                        switch (choice){
                            case "ALL_PERIOD":{
                                oos.writeObject(new Integer(userService.calculateProfit()));
                            }
                            break;
                            case "YEAR_PERIOD":{
                                oos.writeObject(new Integer(orderService.calculateProfitByYear()));
                            }
                            break;
                            case "MONTH_PERIOD":{
                                oos.writeObject(new Integer(orderService.calculateProfitByMonth()));
                            }
                            break;
                        }

                    }
                    break;
                    case "PROFIT_ALL_MONTHS":
                    {
                        oos.writeObject(orderService.calculateProfitByAllMonths());
                    }
                    break;
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
