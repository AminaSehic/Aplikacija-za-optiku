package ba.unsa.etf.rpr.projekat.Models;


public class Employee {
    private int id;
    private String name;
    private String lastName;
    private String birthDate;
    private String address;
    private String contactNumber;
    private String password_hash;

    public enum Type {ADMIN, OWNER, EMPLOYEE}

    private Type type;
    private Shop shop;

    public Employee() {
    }

    public Employee(String name, String lastName, String birthDate, String address, String contactNumber, String password_hash, Type type, Shop shop) {
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
        this.contactNumber = contactNumber;
        this.password_hash = password_hash;
        this.type = type;
        this.shop = shop;
    }

    public Employee(int id, String name, String lastName, String birthDate, String address, String contactNumber, Type type, String password_hash, Shop shop) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
        this.contactNumber = contactNumber;
        this.type = type;
        this.password_hash = password_hash;
        this.shop = shop;
    }


    public String getShopName() {
        return this.shop.getShopName();
    }

    public String getTypeName() {
        if (this.type == Type.ADMIN) {
            return "Admin";
        } else if (this.type == Type.EMPLOYEE) {
            return "Employee";
        } else {
            return "Owner";
        }
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type tip) {
        type = tip;
    }

    @Override
    public String toString() {
        return "Employee: " + "name=" + name + ", lastName=" + lastName + ", birthDate=" + birthDate +
                ", address=" + address + ", contactNumber=" + contactNumber;
    }

}
