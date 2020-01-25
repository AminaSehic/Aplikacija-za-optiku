package ba.unsa.etf.rpr.projekat.Controllers;

import ba.unsa.etf.rpr.projekat.Models.Employee;
import ba.unsa.etf.rpr.projekat.OptikaDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ba.unsa.etf.rpr.projekat.Controllers.AddEmployeeController.Vrsta;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class MainController {

    private OptikaDAO dao;
    public TextField nameField;
    public PasswordField passwordField;
    public MainController(){
        dao = new OptikaDAO();
    }

    public void clickOnButtonOK(ActionEvent actionEvent) throws IOException {
        String name = nameField.getText();
        String password = passwordField.getText();
        Employee e = dao.dajZaposlenika(name, password);
        if (e.getType() == AddEmployeeController.Vrsta.VLASNIK ||e.getType() == Vrsta.ADMIN) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/adminView.fxml"));
            loader.setController(new AdminController(dao));
            Parent root1 = loader.load();
            Stage myStage = new Stage();
            myStage.initStyle(StageStyle.DECORATED);
            myStage.setTitle("Admin dashboard");
            myStage.setScene(new Scene(root1, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            myStage.show();
            myStage.setResizable(false);
        } else if (e.getType() == Vrsta.UPOSLENIK) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/employeeView.fxml"));
            loader.setController(new EmployeeController(dao, e));
            Parent root1 = loader.load();
            Stage myStage = new Stage();
            myStage.initStyle(StageStyle.DECORATED);
            myStage.setTitle("Employee view");
            myStage.setScene(new Scene(root1, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            myStage.show();
            myStage.setResizable(false);
        }
    }
}

