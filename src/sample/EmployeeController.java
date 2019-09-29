package sample;

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
    private MainDAO dao;
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

    EmployeeController(MainDAO dao, Employee e) {
        this.dao = dao;
        this.e = e;
        int a = e.getShop().getId();
        System.out.println(e);
        ArrayList<Glasses> glasses = dao.dajNaocaleIzRadnje(e.getShop().getId());
        System.out.println(glasses);
        listNaocala=FXCollections.observableArrayList(glasses);
    }

    @FXML
    public void initialize() {

        tabelaNaocala.setItems(listNaocala);
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colModel.setCellValueFactory(new PropertyValueFactory("model"));
        colManufacturer.setCellValueFactory(new PropertyValueFactory("manufacturer"));
        colNumber.setCellValueFactory(new PropertyValueFactory("number"));

    }


    public void actionProdaj(ActionEvent actionEvent) {
        Glasses glasses = tabelaNaocala.getSelectionModel().getSelectedItem();
        if (glasses == null) return;
        dao.prodajNaocale(glasses.getId());
    }

    public void clickCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }
    public void sellGlasses(ActionEvent actionEvent){
        Glasses  g = tabelaNaocala.getSelectionModel().getSelectedItem();
        dao.prodajNaocale(g.getId());
        ArrayList<Glasses> glasses = dao.dajNaocaleIzRadnje(e.getShop().getId());
        listNaocala=FXCollections.observableArrayList(glasses);
        tabelaNaocala.setItems(listNaocala);
    }




    /*
 Kada radnik pristupi sistemu, treba da mu se prikaze
 katalog postojecih proizvoda u njegovoj radnji.

  Pri prikazu kataloga, treba biti moguce pretrazivati
  naocale prema zeljama kupca, a to je vecinom pretraga
   po modelu, proizvodjacu,
  cijeni (od-do). Treba moci vrsiti prodaju naocala.*/

}
