package sample.controllers.admincontrollers;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sample.Main;
import sample.bean.Furniture;

public class UpdateFurnitureAdminController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button buttonUpdate;

    @FXML
    private TextField name;

    @FXML
    private TextField price;

    @FXML
    private TextField quantity;

    @FXML
    private TextField sellPrice;

    private static Furniture furniture;

    @FXML
    void clickButtonUpdate(ActionEvent event) {
        if(isInputValid()){
            try {
                Main.getCoos().writeObject("UPDATE_FURNITURE");
                Map<String, String> map = new HashMap<>();
                map.put("id", String.valueOf(furniture.getId()));
                map.put("name", name.getText());
                map.put("sellPrice", sellPrice.getText());
                map.put("price", price.getText());
                map.put("quantity", quantity.getText());
                Main.getCoos().writeObject(map);
            } catch (IOException e) {
                e.printStackTrace();
            }
            buttonUpdate.getScene().getWindow().hide();
        }
    }

    @FXML
    void initialize() {
        name.setText(furniture.getName());
        sellPrice.setText(furniture.getSellPrice());
        price.setText(furniture.getPrice());
        quantity.setText(furniture.getQuantity());
    }

    public static void setFurniture(Furniture furniture){
        UpdateFurnitureAdminController.furniture = furniture;
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
