package ba.unsa.etf.rpr.projekat.Controllers;

import ba.unsa.etf.rpr.projekat.Models.Employee;
import ba.unsa.etf.rpr.projekat.Models.Shop;
import ba.unsa.etf.rpr.projekat.OptikaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminController {
    private OptikaDAO dao;
    public TableView<Employee> tableEmployee;
    public TableColumn colEmployeeId;
    public TableColumn colEmployeeName;
    public TableColumn colEmployeeLastName;
    public TableColumn colEmployeeBirthDate;
    public TableColumn colEmployeeAddress;
    public TableColumn colEmployeeContact;
    public TableColumn colEmployeeShopName;
    public TableColumn colEmployeeType;
    public Button addEmployee;
    public Button cancelEmployee, cancelShop;
    public Button addShop;
    public VBox vBox;
    private ObservableList<Employee> listEmployees;

    public AdminController(OptikaDAO dao) {
        this.dao = dao;
        ArrayList<Employee> zaposlenici = dao.dajSveZaposlenike();
        listEmployees = FXCollections.observableArrayList(zaposlenici);
    }

    @FXML
    public void initialize() {
        tableEmployee.setItems(listEmployees);
        colEmployeeId.setCellValueFactory(new PropertyValueFactory("id"));
        colEmployeeName.setCellValueFactory(new PropertyValueFactory("name"));
        colEmployeeLastName.setCellValueFactory(new PropertyValueFactory("lastName"));
        colEmployeeBirthDate.setCellValueFactory(new PropertyValueFactory("birthDate"));
        colEmployeeAddress.setCellValueFactory(new PropertyValueFactory("address"));
        colEmployeeContact.setCellValueFactory(new PropertyValueFactory("contactNumber"));
        colEmployeeShopName.setCellValueFactory(new PropertyValueFactory("shopName"));
        colEmployeeType.setCellValueFactory(new PropertyValueFactory("typeName"));
    }

    public void onActionAddEmployee(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addEmployeeView.fxml"));
        AddEmployeeController addEmployeeController = new AddEmployeeController(dao);
        loader.setController(addEmployeeController);
        Parent root1 = loader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setResizable(false);
        stage.setTitle("Employee");
        stage.setScene(new Scene(root1));
        stage.show();

        stage.setOnHiding(event -> {
            Employee e = addEmployeeController.getEmployee();
            if (e != null) {
                dao.dodajZaposlenika(e);
                listEmployees.setAll(dao.dajSveZaposlenike());
            }
        });
    }


    public void onActionDeleteEmployee(ActionEvent actionEvent) {
        Employee employee = tableEmployee.getSelectionModel().getSelectedItem();
        dao.deleteEmployee(employee);

    }

    public void clickCancelEmployeeTab(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelEmployee.getScene().getWindow();
        stage.close();
    }


    public void clickCancelShopTab(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelShop.getScene().getWindow();
        stage.close();
    }

    public void onActionAddShop(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addShopView.fxml"));
        AddShopController addShopController = new AddShopController(dao);
        loader.setController(addShopController);
        Parent root1 = loader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("Add Shop");
        stage.setScene(new Scene(root1));
        stage.setResizable(false);
        stage.show();

        stage.setOnHiding(event -> {
            Shop radnja = addShopController.getShop();
            if (radnja != null) {
                dao.dodajRadnju(radnja);
            }
        });
    }

    public void onActionDeleteShop(ActionEvent actionEvent) throws IOException {
    }

}

