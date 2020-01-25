package ba.unsa.etf.rpr.projekat.Models;

import ba.unsa.etf.rpr.projekat.OptikaDAO;


public class Glasses extends Article {

    private String model;
    private int yearOfProduction;
    private OptikaDAO dao = OptikaDAO.getInstance();


    public Glasses(int id, String manufacturer, String model, int yearOfProduction, int type, int price, int shop, int number){
        super(id, manufacturer, price, shop, number);
        this.model = model;
        this.yearOfProduction = yearOfProduction;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(int yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    @Override
    public String toString() {
        return "Glasses" + "manufacturer='" + getManufacturer() +
                ", model='" + model + ", yearOfProduction=" + yearOfProduction + ", price=" + getPrice() +
                ", shop=" + getShop() + ", number=" + getNumber();
    }
}
