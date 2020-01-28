package ba.unsa.etf.rpr.projekat.Controllers;

import ba.unsa.etf.rpr.projekat.Models.Employee;
import ba.unsa.etf.rpr.projekat.Models.Glasses;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
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

    }

    @FXML
    public void initialize() {
        ArrayList<Glasses> glasses = dao.dajNaocaleIzRadnje(e.getShop().getId());
        listNaocala = FXCollections.observableArrayList(glasses);
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
                dao.prodajNaocale(g);
            } catch (Exception e) {
                System.out.println("ovdje smo dosli do problema");
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

    public void clickAddGlasses(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addGlassesView.fxml"));
        AddGlassesController addGlassesController = new AddGlassesController(dao);
        loader.setController(addGlassesController);
        Parent root1 = loader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("Add Shop");
        stage.setScene(new Scene(root1));
        stage.setResizable(false);
        stage.show();

        stage.setOnHiding(event -> {
            Glasses glasses = addGlassesController.getGlasses();
            if (glasses != null) {
                dao.addGlasses(glasses);
                listNaocala.setAll(dao.dajNaocaleIzRadnje(e.getId()));
            }
        });
    }

    public void clickCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) buttonClose.getScene().getWindow();
        stage.close();
    }


}
