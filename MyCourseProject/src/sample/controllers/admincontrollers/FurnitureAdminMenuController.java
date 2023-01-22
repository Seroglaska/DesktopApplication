package sample.controllers.admincontrollers;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
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

public class FurnitureAdminMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Furniture> FurnitureTable;

    @FXML
    private Button addButton;

    @FXML
    private Button backButton;

    @FXML
    private Button delButton;

    @FXML
    private Button editButton;

    @FXML
    private TableColumn<Furniture, String> toName;

    @FXML
    private TableColumn<Furniture, String> toPrice;

    @FXML
    private TableColumn<Furniture, String> toQuantity;

    @FXML
    private TableColumn<Furniture, String> toSellPrice;

    @FXML
    void clickAddButton(ActionEvent event) {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/sample/files/AddFurnitureAdmin.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
            FurnitureTable.getItems().removeAll(FurnitureTable.getItems());
            initialize();
    }

    @FXML
    void clickBackButton(ActionEvent event) {
        backButton.getScene().getWindow().hide();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/sample/files/AdminMenu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void clickDelButton(ActionEvent event) {
        if(FurnitureTable.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText("Please choose furniture!");

            alert.showAndWait();
        }
        else{
            int i = FurnitureTable.getSelectionModel().getSelectedIndex();
            try {
                Main.getCoos().writeObject("DELETE_FURNITURE");
                Main.getCoos().writeObject(new Integer(FurnitureTable.getItems().get(i).getId()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            FurnitureTable.getItems().remove(i);
        }
    }

    @FXML
    void clickEditButton(ActionEvent event) {
        if(FurnitureTable.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText("Please choose furniture!");

            alert.showAndWait();
        }
        else{
            Parent root = null;
            int i = FurnitureTable.getSelectionModel().getSelectedIndex();
            Furniture furniture = FurnitureTable.getItems().get(i);
            UpdateFurnitureAdminController.setFurniture(furniture);;
            try {
                root = FXMLLoader.load(getClass().getResource("/sample/files/UpdateFurnitureAdmin.fxml"));
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
                toPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
                toQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
                for(Map.Entry<Integer, Map<String, String>> mapEntry : furnitureMap.entrySet()){
                    FurnitureTable.getItems().add((new Furniture(Integer.parseInt(mapEntry.getValue().get("id")),
                            mapEntry.getValue().get("name"),
                            mapEntry.getValue().get("sellPrice"),
                            mapEntry.getValue().get("price"),
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
