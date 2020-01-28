package ba.unsa.etf.rpr.projekat.Models;



public abstract class Article {
    private int id;
    private String manufacturer;
    private int price;
    private Shop shop;
    private int quantity;

    public Article(String manufacturer, int price, Shop shop, int quantity) {
        this.manufacturer = manufacturer;
        this.price = price;
        this.shop = shop;
        this.quantity = quantity;
    }



    public Article(int id, String manufacturer, int price, Shop shop, int quantity){
        this.id = id;
        this.manufacturer = manufacturer;
        this.price = price;
        this.shop = shop;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int number) {
        this.quantity = number;
    }
}
