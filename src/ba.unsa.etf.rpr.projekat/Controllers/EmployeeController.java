package ba.unsa.etf.rpr.projekat.Controllers;

import ba.unsa.etf.rpr.projekat.Models.Employee;
import ba.unsa.etf.rpr.projekat.Models.Glasses;
import ba.unsa.etf.rpr.projekat.OptikaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class EmployeeController {
    private OptikaDAO dao;
    private Employee e;
    public TableView<Glasses> tabelaNaocala;
    public TableColumn colId;
    public TableColumn colModel;
    public TableColumn colManufacturer;
    public TableColumn colNumber;
    public ObservableList<Glasses> listNaocala;
    public Button close;
    public Button prodajNaocale;
    public VBox vBox;

    public EmployeeController(OptikaDAO dao, Employee employee) {
        this.dao = dao;
        this.e = employee;
        ArrayList<Glasses> glasses = dao.dajNaocaleIzRadnje(e.getShop().getId());
        listNaocala = FXCollections.observableArrayList(glasses);
    }
    @FXML
    public void initialize() {

        tabelaNaocala.setItems(listNaocala);
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colModel.setCellValueFactory(new PropertyValueFactory("model"));
        colManufacturer.setCellValueFactory(new PropertyValueFactory("manufacturer"));
        colNumber.setCellValueFactory(new PropertyValueFactory("number"));

    }
    public void sellGlasses(ActionEvent actionEvent) {
//        Glasses g = tabelaNaocala.getSelectionModel().getSelectedItem();
//        dao.prodajNaocale(g.getId());
//        ArrayList<Glasses> glasses = dao.dajNaocaleIzRadnje(e.getShop().getId());
//        listNaocala = FXCollections.observableArrayList(glasses);
//        tabelaNaocala.setItems(listNaocala);
    }

    public void clickCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }


}
