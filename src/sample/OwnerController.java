package sample;

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

public class OwnerController {
    private MainDAO dao;
    public TableView tableEmployee;
    public TableColumn colEmployees;
    public Button addEmployee;
    public Button cancel;
    public  Button addShop;
    public VBox vBox;
    private ObservableList<Employee> listEmployees;

    /*Prije svega administrator kada pristupa sistemu treba
    moci unijeti radnike i izdati im pristupnu sifru za sistem*/

    OwnerController(MainDAO d) {
        dao = d;
    }

    @FXML
    public void initialize() {
        tableEmployee.setItems(listEmployees);
        colEmployees.setCellValueFactory(new PropertyValueFactory("radnici"));
    }

    public void onActionAddEmoployee(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addEmployee.fxml"));
        loader.setController(new AddEmployeeController(dao));
        Parent root1 = loader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        //postavljanje parametara sceni
        stage.setTitle("Employee");
        stage.setScene(new Scene(root1));
        //prikazivanje scene
        stage.show();
    }


    public void clickCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
}
