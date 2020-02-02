package ba.unsa.etf.rpr.projekat.Controllers;

import ba.unsa.etf.rpr.projekat.Exceptions.InvalidEmployeeDataException;
import ba.unsa.etf.rpr.projekat.Exceptions.InvalidShopDataException;
import ba.unsa.etf.rpr.projekat.Models.Shop;
import ba.unsa.etf.rpr.projekat.OptikaDAO;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddShopController {
    public TextField fieldID, fieldShopName, fieldAddress;
    private OptikaDAO dao;
    public Button addShopButton, cancelButton;
    public Label labelGreska;
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
        String name = null;
        try {
            name = validateName(fieldShopName.getText());
            String address = validateAddress(fieldAddress.getText());
            shop = new Shop(name, address);
            Stage stage = (Stage) fieldShopName.getScene().getWindow();
            stage.close();
        } catch (InvalidShopDataException e) {

            labelGreska.setTextFill(Color.web("FF0000"));
            labelGreska.setText(e.getMessage());
        }
    }

    private String validateName(String name) throws InvalidShopDataException {
        Pattern pattern = Pattern.compile("^([a-zA-Z0-9]+|[a-zA-Z0-9]+\\s{1}[a-zA-Z0-9]{1,}|[a-zA-Z0-9]+\\s{1}[a-zA-Z0-9]{3,}\\s{1}[a-zA-Z0-9]{1,})$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);
        if (!matcher.matches()) {
            throw new InvalidShopDataException("Invalid name");
        }
        return name;
    }

    private String validateAddress(String address) throws InvalidShopDataException {
        if (address.length() < 5 || address.length() > 40) {
            throw new InvalidShopDataException("Invalid address");
        }
        return address;
    }

}
