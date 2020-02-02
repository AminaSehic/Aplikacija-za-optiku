package ba.unsa.etf.rpr.projekat.Controllers;

import ba.unsa.etf.rpr.projekat.Exceptions.InvalidGlassesDataException;
import ba.unsa.etf.rpr.projekat.Exceptions.InvalidShopDataException;
import ba.unsa.etf.rpr.projekat.Models.Glasses;
import ba.unsa.etf.rpr.projekat.Models.PrescriptionGlasses;
import ba.unsa.etf.rpr.projekat.Models.Shop;
import ba.unsa.etf.rpr.projekat.Models.Sunglasses;
import ba.unsa.etf.rpr.projekat.OptikaDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.time.Year;

public class AddGlassesController {
    private OptikaDAO dao;
    private Glasses glasses;
    public TextField manufacturerField, modelField, yearField, priceField;
    public TextField quantityField;
    public ComboBox<Shop> shopIdField;
    public ComboBox<String> typeField;
    public Label labelGreska;

    public Button cancelButton, addEmployee;

    public AddGlassesController(OptikaDAO optikaDAO) {
        dao = optikaDAO;

    }
    @FXML
    public void initialize() {
        shopIdField.getItems().addAll(dao.getAllShops());
        typeField.getItems().addAll("Sunglasses", "Prescription");
    }

    public void addGlassesAction(ActionEvent actionEvent) {
        try {
            String manufacturer = validateManufacturer(manufacturerField.getText());
            String model = validateModel(modelField.getText());
            String yearOfProduction = validateYear(yearField.getText());
            Integer price = validatePrice(Integer.parseInt(priceField.getText()));
            Integer quantity = validateQuantity(Integer.parseInt(quantityField.getText()));
            String type = validateType(typeField.getValue());
            String sh = validateShop(shopIdField.getValue());
            int shop = Integer.parseInt(shopIdField.getValue().toString().split(" ")[0]);
            Shop s = dao.getShop(shop);
            if (type.toLowerCase().equals("sunglasses")) {
                glasses = new Sunglasses(manufacturer, price, s, quantity, model, yearOfProduction);
            } else if(type.toLowerCase().equals("prescription")){
                glasses = new PrescriptionGlasses(manufacturer, price, s, quantity, model, yearOfProduction);
            }
            Stage stage = (Stage) manufacturerField.getScene().getWindow();
            stage.close();
        } catch (InvalidShopDataException | InvalidGlassesDataException e) {
            labelGreska.setTextFill(Color.web("FF0000"));
            labelGreska.setText(e.getMessage());
        }

    }

    private Integer validateQuantity(int quantity) throws InvalidGlassesDataException {
        if(quantity<1 || quantity>999999) throw new InvalidGlassesDataException("Invalid quantity");
        return quantity;
    }

    private Integer validatePrice(int price) throws InvalidGlassesDataException {
        if (price <1 || price>999999) throw new InvalidGlassesDataException("Invalid price");
        return price;
    }

    private String validateYear(String text) throws InvalidGlassesDataException {
        Integer year = Integer.parseInt(text);
        if(year<1900 || year> Year.now().getValue()){
            throw new InvalidGlassesDataException("Invalid year");
        }
        return text;

    }

    private String validateModel(String text) throws InvalidGlassesDataException {
        if (text.isEmpty()) throw new InvalidGlassesDataException("Set model!");
        return text;
    }

    private String validateManufacturer(String text) throws InvalidGlassesDataException {
        if (text.isEmpty()) throw new InvalidGlassesDataException("Set manufacturer!");
        return text;
    }

    public void clickCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private String validateType(String value) throws InvalidGlassesDataException {
        if(!value.toString().toLowerCase().equals("sunglasses") && !value.toString().toLowerCase().equals("prescription")){
            throw new InvalidGlassesDataException("Invalid glasses type selected");
        }
        return value.toString();
    }

    public Glasses getGlasses() {
        return glasses;
    }

    private String validateShop(Shop shop) throws InvalidShopDataException {
        if (shop == null) throw new InvalidShopDataException("Invalid shop selected");
        return shop.toString();
    }
}
