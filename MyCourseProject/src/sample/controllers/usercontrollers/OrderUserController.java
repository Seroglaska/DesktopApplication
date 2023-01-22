package sample.controllers.usercontrollers;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Main;
import sample.bean.Client;
import sample.bean.Order;
import sample.bean.User;

public class OrderUserController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Order> OrderTable;

    @FXML
    private Button backButton;

    @FXML
    private TableColumn<Order, String> toName;

    @FXML
    private TableColumn<Order, String> toPrice;

    @FXML
    private TableColumn<Order, String> toQuantity;

    @FXML
    private TableColumn<Order, String> toaFinalPrice;

    @FXML
    void clickBackButton(ActionEvent event) {
        backButton.getScene().getWindow().hide();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/sample/files/UserMenu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void initialize() {
        Map<Integer, Map<String, String>> orderMap = null;
        try {
            Main.getCoos().writeObject("FIND_USER_ORDERS");
            Main.getCoos().writeObject(Client.getClient().getId());
            orderMap = (Map) Main.getCois().readObject();
            System.out.println(orderMap);
            if (orderMap.isEmpty()){
                System.out.println("idiot");
            }
            else{
                toName.setCellValueFactory(new PropertyValueFactory<>("name"));
                toQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
                toPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
                toaFinalPrice.setCellValueFactory(new PropertyValueFactory<>("finalPrice"));
                for(Map.Entry<Integer, Map<String, String>> mapEntry : orderMap.entrySet()){
                    OrderTable.getItems().add((new Order(mapEntry.getKey(),
                            mapEntry.getValue().get("name"),
                            mapEntry.getValue().get("price"),
                            mapEntry.getValue().get("quantity"),
                            mapEntry.getValue().get("finalPrice"))));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
