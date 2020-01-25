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
import java.util.ArrayList;

public class AdminController {
    private OptikaDAO dao;
    public TableView<Employee> tableEmployee;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colLastName;
    public TableColumn colBirthDate;
    public TableColumn colAddress;
    public TableColumn colContact;
    public TableColumn colShopName;
    public TableColumn colType;
    public Button addEmployee;
    public Button cancel;
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
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colLastName.setCellValueFactory(new PropertyValueFactory("lastName"));
        colBirthDate.setCellValueFactory(new PropertyValueFactory("birthDate"));
        colAddress.setCellValueFactory(new PropertyValueFactory("address"));
        colContact.setCellValueFactory(new PropertyValueFactory("contactNumber"));
        colShopName.setCellValueFactory(new PropertyValueFactory("shopName"));
        colType.setCellValueFactory(new PropertyValueFactory("typeName"));
    }

    public void onActionAddEmoployee(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addEmployeeView.fxml"));
        AddEmployeeController addEmployeeController = new AddEmployeeController(dao);
        loader.setController(addEmployeeController);
        Parent root1 = loader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.DECORATED);
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

    public void clickCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    public void onActionAddShop(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addShopView.fxml"));
        AddShopController addShopController = new AddShopController(dao);
        loader.setController(addShopController);
        Parent root1 = loader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("Add Shop");
        stage.setScene(new Scene(root1));
        stage.show();

        stage.setOnHiding(event -> {
            Shop radnja = addShopController.getShop();
            if (radnja != null) {
                dao.dodajRadnju(radnja);
            }
        });
    }

}

