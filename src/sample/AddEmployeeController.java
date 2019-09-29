package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddEmployeeController {
    private MainDAO dao;
    public TextField idField, nameField, lastNameField, dateField, addressField;
    public TextField contactField, passwordField, typeField, shopIdField;
    public Button cancelButton, addEmployee;

    AddEmployeeController(MainDAO d){
        dao = d;
    }


    public void addEmployeeAction (ActionEvent actionEvent){
        int id=Integer.parseInt(idField.getText());
        String name = nameField.getText();
        String lastName = lastNameField.getText();
        String date = dateField.getText();
        String address = addressField.getText();
        String contact = contactField.getText();
        String password = passwordField.getText();
        int type = Integer.parseInt(typeField.getText());
        int shop = Integer.parseInt(shopIdField.getText());
        System.out.println(id+" "+name+" "+lastName+" "+date+" "+address+" "+contact+" "+password+" "+type+" "+shop);
        Employee e = new Employee(id, name, lastName, date, address, contact, type, password, shop);
        dao.dodajZaposlenika(e);
        Stage stage = (Stage) idField.getScene().getWindow();
        stage.close();
    }
    public void clickCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}
