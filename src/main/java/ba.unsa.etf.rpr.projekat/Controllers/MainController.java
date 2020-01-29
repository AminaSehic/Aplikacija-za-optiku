package ba.unsa.etf.rpr.projekat.Controllers;

import ba.unsa.etf.rpr.projekat.Models.Employee;
import ba.unsa.etf.rpr.projekat.OptikaDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class MainController {

    private OptikaDAO dao;
    public TextField nameField;
    public PasswordField passwordField;
    public Label labelGreska;
    public MainController(){
        dao = OptikaDAO.getInstance();
    }

    public void clickOnButtonOK(ActionEvent actionEvent) throws IOException {
        String name = nameField.getText();
        String password = passwordField.getText();
        try{
            Employee e = dao.dajZaposlenika(name, password);
            if (e.getType() == Employee.Type.OWNER ||e.getType() == Employee.Type.ADMIN) {
                labelGreska.setText("");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/adminView.fxml"));
                loader.setController(new AdminController(dao));
                Parent root1 = loader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.DECORATED);
                stage.setTitle("Admin dashboard");
                stage.setScene(new Scene(root1, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                stage.show();
                stage.setResizable(false);
            } else if (e.getType() == Employee.Type.EMPLOYEE) {
                labelGreska.setText("");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/employeeView.fxml"));
                loader.setController(new EmployeeController(dao, e));
                Parent root1 = loader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.DECORATED);
                stage.setTitle("Employee view");
                stage.setScene(new Scene(root1, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                stage.show();
                stage.setResizable(false);
            }
            else{
                labelGreska.setTextFill(Color.web("FF0000"));
                labelGreska.setText("Invalid username or password!");
            }
        } catch (NullPointerException n){
            labelGreska.setTextFill(Color.web("FF0000"));
            labelGreska.setText("Invalid username or password!");
        }

    }
}

