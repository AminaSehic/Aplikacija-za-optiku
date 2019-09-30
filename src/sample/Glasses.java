package sample;


public class Glasses {

    private int id;
    private String manufacturer;
    private String model;
    private int yearOfProduction;
    private int type;
    private int prise;
    private Shop shop;
    private int number;
    private MainDAO dao = MainDAO.getInstance();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Glasses(int id, String manufacturer, String model, int yearOfProduction, int type, int prise, int shop, int number) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.yearOfProduction = yearOfProduction;
        this.prise = prise;
        this.shop = dao.dajRadnju(shop);
        this.number = number;
        this.type = type;
    }

    public Glasses() {
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
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

    public int getPrise() {
        return prise;
    }

    public void setPrise(int prise) {
        this.prise = prise;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Glasses" + "manufacturer='" + manufacturer +
                ", model='" + model + ", yearOfProduction=" + yearOfProduction + ", type=" + type + ", prise=" + prise +
                ", shop=" + shop + ", number=" + number;
    }
}
