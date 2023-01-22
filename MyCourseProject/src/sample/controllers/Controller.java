package sample.controllers;

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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Main;
import sample.bean.Client;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button authenticationButton;

    @FXML
    private TextField loginField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button registrationButton;

    @FXML
    void clickSign(ActionEvent event) throws IOException, ClassNotFoundException {
        if(isInputValid()){
            Main.getCoos().writeObject("AUTHENTICATION");
            Map<String,String> map = new HashMap<>();
            map.put("login",loginField.getText());
            map.put("password",passwordField.getText());
            Main.getCoos().writeObject(map);
            Map<String, String> user = (Map) Main.getCois().readObject();
            if(user.isEmpty()){
                System.out.println("Idiot");
            }
            else{
                createUser(user);
                System.out.println(Client.getClient().getRole());
                if(Client.getClient().getRole().equals("user")){
                    authenticationButton.getScene().getWindow().hide();
                    Parent root = FXMLLoader.load(getClass().getResource("/sample/files/UserMenu.fxml"));
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                }
                else{
                    authenticationButton.getScene().getWindow().hide();
                    Parent root = FXMLLoader.load(getClass().getResource("/sample/files/AdminMenu.fxml"));
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                }
            }
        }
    }

    @FXML
    void clickRegistration(ActionEvent event) {
        registrationButton.getScene().getWindow().hide();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/sample/files/Registration.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void initialize() {

    }

    private static void createUser(Map<String, String> user) {
        Client.getClient().setId(Integer.parseInt(user.get("id")));
        Client.getClient().setName(user.get("name"));
        Client.getClient().setSurname(user.get("surname"));
        Client.getClient().setYearOfBirth(Integer.parseInt(user.get("yearOfBirth")));
        Client.getClient().setEmail(user.get("email"));
        Client.getClient().setLogin(user.get("login"));
        Client.getClient().setPassword(user.get("login"));
        Client.getClient().setRole(user.get("role"));
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (loginField.getText() == null || loginField.getText().length() == 0) {
            errorMessage += "No valid login!\n";
        }


        if (passwordField.getText() == null || passwordField.getText().length() == 0) {
            errorMessage += "No valid password!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}