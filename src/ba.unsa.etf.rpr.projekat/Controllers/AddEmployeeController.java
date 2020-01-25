package ba.unsa.etf.rpr.projekat.Controllers;

import ba.unsa.etf.rpr.projekat.Exceptions.InvalidEmployeeTypeException;
import ba.unsa.etf.rpr.projekat.Models.Employee;
import ba.unsa.etf.rpr.projekat.Models.Shop;
import ba.unsa.etf.rpr.projekat.OptikaDAO;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.security.NoSuchAlgorithmException;

import static ba.unsa.etf.rpr.projekat.OptikaDAO.dajPasswordHash;

public class AddEmployeeController {
    private OptikaDAO dao;
    public TextField idField, nameField, lastNameField, dateField, addressField;
    public TextField contactField, passwordField, typeField, shopIdField;
    public Button cancelButton, addEmployee;



    private Employee employee;

    public AddEmployeeController(OptikaDAO dao) {
        this.dao = dao;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void addEmployeeAction(ActionEvent actionEvent) {
        try {
            String name = nameField.getText();
            String lastName = lastNameField.getText();
            String date = dateField.getText();
            String address = addressField.getText();
            String contact = contactField.getText();
            String password = passwordField.getText();
            String password_hash=dajPasswordHash(password);
            Employee.Type type = null;
            type = odrediVrstu(typeField.getText());
            int shop = Integer.parseInt(shopIdField.getText());
            Shop s = dao.dajRadnju(shop);
            employee = new Employee(name, lastName, date, address, contact, password_hash, type, s);
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.close();
        } catch (InvalidEmployeeTypeException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    public void clickCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private Employee.Type odrediVrstu(String v) throws InvalidEmployeeTypeException {
        if (Employee.Type.ADMIN.toString().toLowerCase().equals(v.toLowerCase())) {
            return Employee.Type.ADMIN;
        } else if (Employee.Type.OWNER.toString().toLowerCase().equals(v.toLowerCase())) {
            return Employee.Type.OWNER;
        } else if (Employee.Type.EMPLOYEE.toString().toLowerCase().equals(v.toLowerCase())) {
            return Employee.Type.EMPLOYEE;
        } else {
            throw new InvalidEmployeeTypeException("Nepostojeci tip zaposlenika");
        }
    }
}
