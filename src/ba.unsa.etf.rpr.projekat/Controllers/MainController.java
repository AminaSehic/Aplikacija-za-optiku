package ba.unsa.etf.rpr.projekat.Controllers;

import ba.unsa.etf.rpr.projekat.OptikaDAO;
import javafx.event.ActionEvent;

public class MainController {

    private OptikaDAO dao;
    public MainController(){
        dao = OptikaDAO.getInstance();
    }

    public void clickOnButtonOK(ActionEvent actionEvent) {

    }
}

