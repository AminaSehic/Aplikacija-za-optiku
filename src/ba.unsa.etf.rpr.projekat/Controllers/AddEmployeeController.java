package ba.unsa.etf.rpr.projekat.Controllers;

import ba.unsa.etf.rpr.projekat.Exceptions.InvalidEmployeeTypeException;
import ba.unsa.etf.rpr.projekat.Models.Employee;
import ba.unsa.etf.rpr.projekat.Models.Shop;
import ba.unsa.etf.rpr.projekat.OptikaDAO;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddEmployeeController {
    private OptikaDAO dao;
    public TextField idField, nameField, lastNameField, dateField, addressField;
    public TextField contactField, passwordField, typeField, shopIdField;
    public Button cancelButton, addEmployee;

    public enum Vrsta {ADMIN, VLASNIK, UPOSLENIK}

    ;
    private Employee employee;

    public AddEmployeeController(OptikaDAO dao) {
        this.dao = dao;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void addEmployeeAction(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String lastName = lastNameField.getText();
            String date = dateField.getText();
            String address = addressField.getText();
            String contact = contactField.getText();
            String password = passwordField.getText();
            Vrsta type = null;
            type = odrediVrstu(typeField.getText());
            int shop = Integer.parseInt(shopIdField.getText());
            Shop s = dao.dajRadnju(shop);
            employee = new Employee(id, name, lastName, date, address, contact, type, password, s);
            Stage stage = (Stage) idField.getScene().getWindow();
            stage.close();
        } catch (InvalidEmployeeTypeException e) {
            e.printStackTrace();
        }

    }

    public void clickCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private Vrsta odrediVrstu(String v) throws InvalidEmployeeTypeException {
        if (Vrsta.ADMIN.toString().toLowerCase().equals(v.toLowerCase())) {
            return Vrsta.ADMIN;
        } else if (Vrsta.VLASNIK.toString().toLowerCase().equals(v.toLowerCase())) {
            return Vrsta.VLASNIK;
        } else if (Vrsta.UPOSLENIK.toString().toLowerCase().equals(v.toLowerCase())) {
            return Vrsta.UPOSLENIK;
        } else {
            throw new InvalidEmployeeTypeException("Nepostojeci tip zaposlenika");
        }
    }
}
