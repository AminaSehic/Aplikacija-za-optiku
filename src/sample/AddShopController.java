package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddShopController {
    public TextField fieldID, fieldShopName,fieldAddress;
    private MainDAO dao;
    public Button addShopButton, cancelButton;
    private Shop shop;


    AddShopController(MainDAO d) {
        d = dao;
    }
    public void addShopAction (ActionEvent actionEvent){

        int id = Integer.parseInt(fieldID.getText());
        String name = fieldShopName.getText();
        String address = fieldAddress.getText();
        shop = new Shop(id, name, address);
        dao.dodajRadnju(shop);
        Stage stage = (Stage) fieldID.getScene().getWindow();
        stage.close();
    }

    public void clickCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    public Shop getShop(){
        return shop;
    }
}
