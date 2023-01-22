package sample.controllers.admincontrollers;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import sample.bean.User;
import sample.controllers.admincontrollers.UpdateUserAdminController;

public class UserAdminController{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<User> UsersTable;

    @FXML
    private Button addButton;

    @FXML
    private Button backButton;

    @FXML
    private Button delButton;

    @FXML
    private Button editButton;

    @FXML
    private TableColumn<User, String> toEmail;


    @FXML
    private TableColumn<User, String> toName;

    @FXML
    private TableColumn<User, String> toSurname;

    @FXML
    private TableColumn<User, String> toYearOfBirth;

    @FXML
    void clickAddButton(ActionEvent event) {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/sample/files/AddUserAdmin.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
            UsersTable.getItems().removeAll(UsersTable.getItems());
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
        if(UsersTable.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText("Please choose user!");

            alert.showAndWait();
        }
        else{
            int i = UsersTable.getSelectionModel().getSelectedIndex();
            try {
                Main.getCoos().writeObject("DELETE_USER");
                Main.getCoos().writeObject(new Integer(UsersTable.getItems().get(i).getId()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            UsersTable.getItems().remove(i);
        }
    }

    @FXML
    void clickEditButton(ActionEvent event) {
        if(UsersTable.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText("Please choose user!");

            alert.showAndWait();
        }
        else{
            Parent root = null;
            int i = UsersTable.getSelectionModel().getSelectedIndex();
            User user = UsersTable.getItems().get(i);
            UpdateUserAdminController.setUser(user);
            try {
                root = FXMLLoader.load(getClass().getResource("/sample/files/UpdateUserAdmin.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
            UsersTable.getItems().removeAll(UsersTable.getItems());
            initialize();
//            Map<String, String> userMap = null;
//            try {
//                Main.getCoos().writeObject("FIND_USER");
//                Main.getCoos().writeObject(UsersTable.getItems().get(i).getId());
//                userMap = (HashMap) Main.getCois().readObject();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//            UsersTable.getItems().remove(i);
//            UsersTable.getItems().add(new User(Integer.parseInt(userMap.get("id")),
//                    userMap.get("name"),
//                    userMap.get("surname"),
//                    userMap.get("login"),
//                    userMap.get("password"),
//                    userMap.get("yearOfBirth"),
//                    userMap.get("email")));
        }
    }

    private ObservableList<User> data = FXCollections.observableArrayList();
    private TableView.TableViewSelectionModel<User> selectionModel;

    @FXML
    void initialize() {
        Map<Integer, Map<String, String>> usersMap = null;
        try {
            Main.getCoos().writeObject("FIND_USERS");
            usersMap = (Map) Main.getCois().readObject();
            if (usersMap.isEmpty()){
                System.out.println("idiot");
            }
            else{
                toName.setCellValueFactory(new PropertyValueFactory<>("name"));
                toSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
                toYearOfBirth.setCellValueFactory(new PropertyValueFactory<>("yearOfBirth"));
                toEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
                for(Map.Entry<Integer, Map<String, String>> mapEntry : usersMap.entrySet()){
                    UsersTable.getItems().add((new User(Integer.parseInt(mapEntry.getValue().get("id")),
                            mapEntry.getValue().get("name"),
                            mapEntry.getValue().get("surname"),
                            mapEntry.getValue().get("login"),
                            mapEntry.getValue().get("password"),
                            mapEntry.getValue().get("yearOfBirth"),
                            mapEntry.getValue().get("email"))));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}

