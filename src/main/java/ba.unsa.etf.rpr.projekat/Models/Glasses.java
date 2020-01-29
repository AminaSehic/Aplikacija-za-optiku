package ba.unsa.etf.rpr.projekat.Models;


public abstract class Glasses extends Article {

    private String model;
    private String yearOfProduction;

    public Glasses(String manufacturer, int price, Shop shop, int quantity, String model, String yearOfProduction) {
        super(0, manufacturer, price, shop, quantity);
        this.model = model;
        this.yearOfProduction = yearOfProduction;
    }

    public Glasses(int id, String manufacturer, String model, String yearOfProduction, int price, Shop shop, int quantity){
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

    public String getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(String yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    public abstract String getType();
    @Override
    public String toString() {
        return "Glasses" + "manufacturer=" + getManufacturer() +
                ", model=" + model + ", yearOfProduction=" + yearOfProduction + ", price=" + getPrice() +
                ", shop=" + getShop() + ", number=" + getQuantity();
    }
}
