package sample.controllers.usercontrollers;

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
import sample.bean.Furniture;
import sample.controllers.admincontrollers.UpdateFurnitureAdminController;

public class BuyFurnitureController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button back;

    @FXML
    private Button buyButton;

    @FXML
    private TextField quantity;

    @FXML
    void clickBack(ActionEvent event) {
        back.getScene().getWindow().hide();
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

    private static Furniture furniture;

    @FXML
    void clickBuyButton(ActionEvent event) {
        if(isInputValid()){
            try {
                Main.getCoos().writeObject("CREATE_ORDER");
                Map<String, String> order = new HashMap<>();
                order.put("id_user", String.valueOf(Client.getClient().getId()));
                order.put("id_furniture", String.valueOf(furniture.getId()));
                order.put("quantity", quantity.getText());
                Main.getCoos().writeObject(order);
                boolean bool = (Boolean) Main.getCois().readObject();
                if(bool){
                    buyButton.getScene().getWindow().hide();
                }
                else{
                    System.out.println("idiot");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void initialize() {
        quantity.setText(furniture.getQuantity());
    }

    public static void setFurniture(Furniture furniture){
        BuyFurnitureController.furniture = furniture;
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (quantity.getText() == null || quantity.getText().length() == 0) {
            errorMessage += "No valid quantity!\n";
        } else {
            // пытаемся преобразовать почтовый код в int.
            try {
                int boughtQuantity = Integer.parseInt(quantity.getText());
                int quantity = Integer.parseInt(furniture.getQuantity());
                if(boughtQuantity > quantity){
                    errorMessage += "No valid quantity(more than have shop)!\n";
                }
            } catch (NumberFormatException e) {
                errorMessage += "No valid quantity(must be an integer)!\n";
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
