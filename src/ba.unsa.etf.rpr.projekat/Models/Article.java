package ba.unsa.etf.rpr.projekat.Models;

import ba.unsa.etf.rpr.projekat.OptikaDAO;


public abstract class Article {
    private int id;
    private String manufacturer;
    private int price;
    private Shop shop;
    private int number;
    private OptikaDAO dao = OptikaDAO.getInstance();

    public Article(int id, String manufacturer, int price, int shop, int number){
        this.id = id;
        this.manufacturer = manufacturer;
        this.price = price;
        this.shop = dao.dajRadnju(shop);
        this.number = number;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
