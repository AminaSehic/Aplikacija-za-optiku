package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Controller {

    public TextField nameField;
    public PasswordField passwordField;
    private MainDAO dao;
    public Button buttonOK;

    public Controller() {
        dao = MainDAO.getInstance();
    }

    //OK - da li postoji taj korisnik
    //ako postoji onda jel radnik il vlasnik
    // otvris prpozore za tu ulogu


    public void buttonOK(ActionEvent actionEvent) throws IOException {
       try{
           String name = nameField.getText();
           String password = passwordField.getText();
           System.out.println("name: "+name+", password: "+password);
           Employee e = dao.dajZaposlenika(name, password);
           if (e.getType() == 1) {
               //otvori mu prozor za radnike

               FXMLLoader loader = new FXMLLoader(getClass().getResource("/employee.fxml"));
               loader.setController(new EmployeeController(dao, e));
               Parent root1 = loader.load();
               Stage stage = new Stage();
               stage.initModality(Modality.APPLICATION_MODAL);
               stage.initStyle(StageStyle.UNDECORATED);
               //postavljanje parametara sceni
               stage.setTitle("Employee");
               stage.setScene(new Scene(root1));
               //prikazivanje scene
               stage.show();


           } else if (e.getType() == 2) {
               //otvori prozor za vlasnika
               FXMLLoader loader = new FXMLLoader(getClass().getResource("/owner.fxml"));
               loader.setController(new OwnerController(dao));
               Parent root1 = loader.load();
               Stage stage = new Stage();
               stage.initModality(Modality.APPLICATION_MODAL);
               stage.initStyle(StageStyle.UNDECORATED);
               //postavljanje parametara sceni
               stage.setTitle("Owner: ");
               stage.setScene(new Scene(root1));
               //prikazivanje scene
               stage.show();
           } else {
               System.out.println(e);
               System.out.println("Greska. nepostojeca vrijednost");
               System.exit(1);
           }
       } catch(Exception e){
           e.printStackTrace();
       }


    }

}
