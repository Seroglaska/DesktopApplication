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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Main;
import sample.bean.Furniture;
import sample.bean.User;
import sample.controllers.admincontrollers.UpdateUserAdminController;

public class FurnitureUserController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Furniture> FurnitureTable;

    @FXML
    private Button backButton;

    @FXML
    private Button buyButton;

    @FXML
    private TableColumn<Furniture,String> toName;

    @FXML
    private TableColumn<Furniture,String> toQuantity;

    @FXML
    private TableColumn<Furniture,String> toSellPrice;

    @FXML
    void clickBuyButton(ActionEvent event) {
        if(FurnitureTable.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText("Please choose furniture!");

            alert.showAndWait();
        }
        else{
            int i = FurnitureTable.getSelectionModel().getSelectedIndex();
            Furniture furniture = FurnitureTable.getItems().get(i);
            BuyFurnitureController.setFurniture(furniture);
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/sample/files/BuyFurnitureUser.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
            FurnitureTable.getItems().removeAll(FurnitureTable.getItems());
            initialize();
        }
    }

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
        Map<Integer, Map<String, String>> furnitureMap = null;;
        try {
            Main.getCoos().writeObject("FIND_FURNITURE");
            furnitureMap = (Map) Main.getCois().readObject();
            if (furnitureMap.isEmpty()){
                System.out.println("idiot");
            }
            else{
                toName.setCellValueFactory(new PropertyValueFactory<>("name"));
                toSellPrice.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));
                toQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
                for(Map.Entry<Integer, Map<String, String>> mapEntry : furnitureMap.entrySet()){
                    FurnitureTable.getItems().add((new Furniture(Integer.parseInt(mapEntry.getValue().get("id")),
                            mapEntry.getValue().get("name"),
                            mapEntry.getValue().get("sellPrice"),
                            mapEntry.getValue().get("quantity"))));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
