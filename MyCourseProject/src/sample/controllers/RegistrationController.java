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

public class RegistrationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backButton;

    @FXML
    private TextField email;

    @FXML
    private TextField login;

    @FXML
    private TextField name;

    @FXML
    private TextField password;

    @FXML
    private Button registrationButton;

    @FXML
    private TextField surname;

    @FXML
    private TextField yearOfBirth;

    @FXML
    void clickBackButton(ActionEvent event) {
        backButton.getScene().getWindow().hide();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/sample/files/sample.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void clickRegistrationButton(ActionEvent event) {
        if(isInputValid()){
            try {
                Main.getCoos().writeObject("REGISTRATION");
                Map<String, String> map = new HashMap<>();
                map.put("password", password.getText());
                map.put("login", login.getText());
                map.put("email", email.getText());
                map.put("yearOfBirth", yearOfBirth.getText());
                map.put("name", name.getText());
                map.put("surname", surname.getText());
                Main.getCoos().writeObject(map);
            } catch (IOException e) {
                e.printStackTrace();
            }
            registrationButton.getScene().getWindow().hide();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/sample/files/sample.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    @FXML
    void initialize() {
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (name.getText() == null || name.getText().length() == 0) {
            errorMessage += "No valid first name!\n";
        }
        if (surname.getText() == null || surname.getText().length() == 0) {
            errorMessage += "No valid last name!\n";
        }
        if (login.getText() == null || login.getText().length() == 0) {
            errorMessage += "No valid login!\n";
        }

        if (yearOfBirth.getText() == null || yearOfBirth.getText().length() == 0) {
            errorMessage += "No valid year of birth!\n";
        } else {
            // пытаемся преобразовать почтовый код в int.
            try {
                Integer.parseInt(yearOfBirth.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid year of birth (must be an integer)!\n";
            }
        }

        if (password.getText() == null || password.getText().length() == 0) {
            errorMessage += "No valid password!\n";
        }
        if (email.getText() == null || email.getText().length() == 0) {
            errorMessage += "No valid email!\n";
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
