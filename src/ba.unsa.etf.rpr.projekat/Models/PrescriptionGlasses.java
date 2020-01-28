package ba.unsa.etf.rpr.projekat.Models;


public class PrescriptionGlasses extends Glasses {
    public PrescriptionGlasses(int id, String manufacturer, String model, String yearOfProduction, int price, Shop shop, int quantity){
        super(id, manufacturer, model, yearOfProduction, price, shop, quantity);
    }

    public PrescriptionGlasses(String manufacturer, int price, Shop shop, int quantity, String model, String yearOfProduction) {
        super(manufacturer, price, shop, quantity, model, yearOfProduction);
    }

    @Override
    public String getType() {
        return "Prescription";
    }
    @Override
    public String toString() {
        return "naocale"+getId()+" "+getManufacturer()+" "+getModel()+" "+getYearOfProduction()+" "+ getPrice()+" "+getType()+ " " +getShop().getId()+" " +getQuantity();
    }
}
