package ba.unsa.etf.rpr.projekat.Controllers;

import ba.unsa.etf.rpr.projekat.Models.Shop;
import ba.unsa.etf.rpr.projekat.OptikaDAO;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddShopController {
    public TextField fieldID, fieldShopName, fieldAddress;
    private OptikaDAO dao;
    public Button addShopButton, cancelButton;
    private Shop shop;


    public AddShopController(OptikaDAO dao) {
        this.dao = dao;
    }

    public Shop getShop() {
        return shop;
    }

    public void clickCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void addShopAction(ActionEvent actionEvent) {

        String name = fieldShopName.getText();
        String address = fieldAddress.getText();
        shop = new Shop(name, address);
        Stage stage = (Stage) fieldShopName.getScene().getWindow();
        stage.close();
    }

}
