package ba.unsa.etf.rpr.projekat.Models;


public class Sunglasses extends Glasses {
    public Sunglasses(int id, String manufacturer, String model, String yearOfProduction, int price, Shop shop, int quantity){
        super(id, manufacturer, model, yearOfProduction,  price, shop, quantity);
    }

    public Sunglasses(String manufacturer, int price, Shop shop, int quantity, String model, String yearOfProduction) {
        super(manufacturer, price, shop, quantity, model, yearOfProduction);
    }

    @Override
    public String getType() {
        return "Sunglasses";
    }

    @Override
    public String toString() {
        return "Glasses"+getId()+" "+getManufacturer()+" "+getModel()+" "+getYearOfProduction()+" "+ getPrice()+" "+getType()+ " " +getShop().getId()+" " +getQuantity();
    }
}
