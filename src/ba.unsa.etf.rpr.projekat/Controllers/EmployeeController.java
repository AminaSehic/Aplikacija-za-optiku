package ba.unsa.etf.rpr.projekat.Controllers;

import ba.unsa.etf.rpr.projekat.Models.Employee;
import ba.unsa.etf.rpr.projekat.Models.Glasses;
import ba.unsa.etf.rpr.projekat.OptikaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

public class EmployeeController {
    private OptikaDAO dao;
    private Employee e;
    public TableView<Glasses> tabelaNaocala;
    public TableColumn colId;
    public TableColumn colModel;
    public TableColumn colType;
    public TableColumn colManufacturer;
    public TableColumn colQuantity;
    public ObservableList<Glasses> listNaocala;
    public Button buttonClose;
    public Button buttonSellGlasses;
    public VBox vBox;
    public Label labelGreska;

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
        colType.setCellValueFactory(new PropertyValueFactory("type"));
        colManufacturer.setCellValueFactory(new PropertyValueFactory("manufacturer"));
        colQuantity.setCellValueFactory(new PropertyValueFactory("quantity"));

    }

    public void clickSellGlasses(ActionEvent actionEvent) {
        try {
            Glasses g = tabelaNaocala.getSelectionModel().getSelectedItem();
            try {
                dao.prodajNaocale(g.getId());
            } catch (Exception e) {
                dao.prodajNaocale(g.getId());
            }
            ArrayList<Glasses> glasses = dao.dajNaocaleIzRadnje(e.getShop().getId());
            listNaocala = FXCollections.observableArrayList(glasses);
            tabelaNaocala.setItems(listNaocala);
            labelGreska.setText("");
        } catch (NullPointerException e) {
            labelGreska.setTextFill(Color.web("#FF0000"));
            labelGreska.setText("Nisu odabrane naocale");
        }

    }

    public void clickAddGlasses(ActionEvent actionEvent) {
    }

    public void clickCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) buttonClose.getScene().getWindow();
        stage.close();
    }


}
