package sample.controllers.usercontrollers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.bean.Client;

public class UserProfileController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backButton;

    @FXML
    private Label email;

    @FXML
    private Label login;

    @FXML
    private Label name;

    @FXML
    private Label password;

    @FXML
    private Label surname;

    @FXML
    private Label yearOfBirth;

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
        name.setText(Client.getClient().getName());
        surname.setText(Client.getClient().getSurname());
        login.setText(Client.getClient().getLogin());
        password.setText(Client.getClient().getPassword());
        email.setText(Client.getClient().getEmail());
        yearOfBirth.setText(String.valueOf(Client.getClient().getYearOfBirth()));
    }

}

