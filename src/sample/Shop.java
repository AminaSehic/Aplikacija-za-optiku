package sample;

import java.util.ArrayList;
import java.util.Map;

public class Shop {
    public int id;
    public String shopName;
    public String address;
    public Map<Shop, ArrayList<Employee>> map;

    public Shop() {
    }

    public Shop(int id, String shopName, String address) {
        this.id = id;
        this.shopName = shopName;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Map<Shop, ArrayList<Employee>> getMap() {
        return map;
    }

    public void setMap(Map<Shop, ArrayList<Employee>> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "Shop " + "shopName='" + shopName + ", address='" + address;
    }
}
