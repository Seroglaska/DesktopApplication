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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Main;

public class AddFurnitureAdminController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private Button back;

    @FXML
    private TextField name;

    @FXML
    private TextField price;

    @FXML
    private TextField quantity;

    @FXML
    private TextField sellPrice;

    @FXML
    void clickAddButton(ActionEvent event) {
        if (isInputValid()) {
            try {
                Main.getCoos().writeObject("ADD_FURNITURE");
                Map<String, String> map = new HashMap<>();
                map.put("name", name.getText());
                map.put("sellPrice", sellPrice.getText());
                map.put("price", price.getText());
                map.put("quantity", quantity.getText());
                Main.getCoos().writeObject(map);
            } catch (IOException e) {
                e.printStackTrace();
            }
            addButton.getScene().getWindow().hide();
        }
    }

    @FXML
    void clickBack(ActionEvent event) {
        back.getScene().getWindow().hide();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/sample/files/FurnitureAdminMenu.fxml"));
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

    private boolean isInputValid() {
        String errorMessage = "";

        if (name.getText() == null || name.getText().length() == 0) {
            errorMessage += "No valid first name!\n";
        }

        if (price.getText() == null || price.getText().length() == 0) {
            errorMessage += "No valid price!\n";
        } else {
            // пытаемся преобразовать почтовый код в int.
            try {
                Integer.parseInt(price.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid price(must be an integer)!\n";
            }
        }
        if (quantity.getText() == null || quantity.getText().length() == 0) {
            errorMessage += "No valid quantity!\n";
        } else {
            // пытаемся преобразовать почтовый код в int.
            try {
                Integer.parseInt(quantity.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid quantity(must be an integer)!\n";
            }
        }
        if (sellPrice.getText() == null || sellPrice.getText().length() == 0) {
            errorMessage += "No valid sell price!\n";
        } else {
            // пытаемся преобразовать почтовый код в int.
            try {
                Integer.parseInt(sellPrice.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid sell price(must be an integer)!\n";
            }
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
