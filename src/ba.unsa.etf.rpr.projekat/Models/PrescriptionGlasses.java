package ba.unsa.etf.rpr.projekat.Models;


public class PrescriptionGlasses extends Glasses {
    public PrescriptionGlasses(int id, String manufacturer, String model, int yearOfProduction, int price, Shop shop, int quantity){
        super(id, manufacturer, model, yearOfProduction, price, shop, quantity);
    }

    @Override
    public String getType() {
        return "Prescription";
    }
}
