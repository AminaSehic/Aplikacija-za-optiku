package ba.unsa.etf.rpr.projekat.Controllers;

import ba.unsa.etf.rpr.projekat.Exceptions.InvalidEmployeeDataException;
import ba.unsa.etf.rpr.projekat.Models.Employee;
import ba.unsa.etf.rpr.projekat.Models.Shop;
import ba.unsa.etf.rpr.projekat.OptikaDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ba.unsa.etf.rpr.projekat.OptikaDAO.dajPasswordHash;

public class AddEmployeeController {
    private OptikaDAO dao;
    public TextField idField, nameField, lastNameField, dateField, addressField;
    public TextField contactField, passwordField;
    public ComboBox<Shop> shopIdField;
    public ComboBox<Employee.Type> typeField;
    public Label labelGreska;

    public Button cancelButton, addEmployee;


    private Employee employee;

    public AddEmployeeController(OptikaDAO dao) {
        this.dao = dao;
    }

    @FXML
    public void initialize() {
        shopIdField.getItems().addAll(dao.getAllShops());
        typeField.getItems().addAll(Employee.Type.values());
    }

    public Employee getEmployee() {
        return employee;
    }

    public void addEmployeeAction(ActionEvent actionEvent) {
        try {
            String name = validateName(nameField.getText());
            String lastName = validateLastName(lastNameField.getText());
            String date = validateDate(dateField.getText());
            String address = validateAddress(addressField.getText());
            String contact = validateContact(contactField.getText());
            String password = validatePassword(passwordField.getText());
            String password_hash = dajPasswordHash(password);
            Employee.Type type = validateType(typeField.getValue());
            int shop = Integer.parseInt(shopIdField.getValue().toString().split(" ")[0]);
            Shop s = validateShop(dao.dajRadnju(shop));
            employee = new Employee(name, lastName, date, address, contact, password_hash, type, s);
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.close();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidEmployeeDataException e) {
            labelGreska.setTextFill(Color.web("FF0000"));
            labelGreska.setText(e.getMessage());
        }

    }

    public void clickCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private String validateName(String name) throws InvalidEmployeeDataException {
        Pattern pattern = Pattern.compile("^([a-zA-Z]+|[a-zA-Z]+\\s{1}[a-zA-Z]{1,}|[a-zA-Z]+\\s{1}[a-zA-Z]{3,}\\s{1}[a-zA-Z]{1,})$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);
        if (!matcher.matches()) {
            throw new InvalidEmployeeDataException("Invalid name");
        }
        return name;
    }

    private String validateLastName(String lastName) throws InvalidEmployeeDataException {
        Pattern pattern = Pattern.compile("^([a-zA-Z]+|[a-zA-Z]+\\s{1}[a-zA-Z]{1,}|[a-zA-Z]+\\s{1}[a-zA-Z]{3,}\\s{1}[a-zA-Z]{1,})$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(lastName);
        if (!matcher.matches()) {
            throw new InvalidEmployeeDataException("Invalid last name");
        }
        return lastName;
    }

    private String validateDate(String date) throws InvalidEmployeeDataException {
        Pattern pattern = Pattern.compile("^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$");
        Matcher matcher = pattern.matcher(date);
        if (!matcher.matches()) {
            throw new InvalidEmployeeDataException("Invalid date");
        }
        return date;
    }

    private String validateAddress(String address) throws InvalidEmployeeDataException {
        if (address.length() < 5 || address.length() > 40) {
            throw new InvalidEmployeeDataException("Invalid address");
        }
        return address;
    }

    private String validateContact(String number) throws InvalidEmployeeDataException {
        if (number.isEmpty()) throw new InvalidEmployeeDataException("Invalid phone number");
        Pattern pattern = Pattern.compile("^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$");
        Matcher matcher = pattern.matcher(number);
        if (!matcher.matches()) {
            throw new InvalidEmployeeDataException("Invalid phone number");
        }
        return number;
    }

    private String validatePassword(String password) throws InvalidEmployeeDataException {
        if (password.length() < 4 || password.length() > 32) {
            throw new InvalidEmployeeDataException("Password must be between 5 and 31 characters!");
        }
        return password;
    }
    private Employee.Type validateType(Employee.Type type) throws  InvalidEmployeeDataException{
        if(type==null) throw new InvalidEmployeeDataException("Invalid employee type");
        return type;
    }
    private Shop validateShop(Shop shop) throws InvalidEmployeeDataException{
        if(shop==null) throw new InvalidEmployeeDataException("Invalid shop selected");
        return shop;
    }


}
