package ba.unsa.etf.rpr.projekat.Models;

import ba.unsa.etf.rpr.projekat.OptikaDAO;


public abstract class Glasses extends Article {

    private String model;
    private int yearOfProduction;


    public Glasses(int id, String manufacturer, String model, int yearOfProduction, int price, Shop shop, int quantity){
        super(id, manufacturer, price, shop, quantity);
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

    public abstract String getType();
    @Override
    public String toString() {
        return "Glasses" + "manufacturer='" + getManufacturer() +
                ", model='" + model + ", yearOfProduction=" + yearOfProduction + ", price=" + getPrice() +
                ", shop=" + getShop() + ", number=" + getQuantity();
    }
}
