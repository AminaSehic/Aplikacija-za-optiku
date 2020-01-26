package ba.unsa.etf.rpr.projekat.Models;


public class Sunglasses extends Glasses {
    public Sunglasses(int id, String manufacturer, String model, int yearOfProduction, int price, Shop shop, int quantity){
        super(id, manufacturer, model, yearOfProduction,  price, shop, quantity);
    }

    @Override
    public String getType() {
        return "Sunglasses";
    }
}
