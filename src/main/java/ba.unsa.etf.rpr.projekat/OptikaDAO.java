package ba.unsa.etf.rpr.projekat;


import ba.unsa.etf.rpr.projekat.Exceptions.InvalidEmployeeDataException;
import ba.unsa.etf.rpr.projekat.Models.Employee;
import ba.unsa.etf.rpr.projekat.Models.Glasses;
import ba.unsa.etf.rpr.projekat.Models.PrescriptionGlasses;
import ba.unsa.etf.rpr.projekat.Models.Shop;
import ba.unsa.etf.rpr.projekat.Models.Sunglasses;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class OptikaDAO {
    private static OptikaDAO instance;
    private static int idEmployee = -1, idShop = -1, idGlasses = -1;
    private Connection conn;
    private PreparedStatement testQuery, addShopQuery, getEmoployeeQuery, getAllEmployeeQuery,
            getShopQuery, getGlassesQuery, sellGlassesQuery, addEmoloyeeQuery,
            getEmployeeIndexQuery, getShopIndexQuery,
            getGlassesIndexQuery, deleteEmployeeQuery, setFirstEmployeeQuery, setFirstShopQuery,
            setFirstGlassesQuery, getAllShopsQuery, deleteShopQuery, addGlassesQuery, deleteGlassesQuery;

    public static OptikaDAO getInstance() {
        if (instance == null) instance = new OptikaDAO();
        return instance;
    }

    private OptikaDAO() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:optika.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            testQuery = conn.prepareStatement("select * from employee, shop, glasses");
        } catch (Exception e) {
            resetDatabase();
            try {
                testQuery = conn.prepareStatement("select * from employee, shop, glasses");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        try {
            getEmoployeeQuery = conn.prepareStatement("select * from employee where name=? and password_hash=?");
            getAllEmployeeQuery = conn.prepareStatement("select * from employee");
            getShopQuery = conn.prepareStatement("select * from shop where id=?");
            getGlassesQuery = conn.prepareStatement("select * from glasses where shop_id=?");
            sellGlassesQuery = conn.prepareStatement("update glasses set quantity=(quantity-1) where id=?");
            addEmoloyeeQuery = conn.prepareStatement("insert into employee values(?,?,?,?,?,?,?,?,?)");
            addShopQuery = conn.prepareStatement("insert into shop values(?,?,?)");
            getEmployeeIndexQuery = conn.prepareStatement("select max(id) from employee");
            getShopIndexQuery = conn.prepareStatement("select max(id) from shop");
            getGlassesIndexQuery = conn.prepareStatement("select max(id) from glasses");
            deleteEmployeeQuery = conn.prepareStatement("DELETE from Employee where id=?");
            setFirstShopQuery = conn.prepareStatement("insert or replace into shop values(?,?,?)");
            setFirstEmployeeQuery = conn.prepareStatement("insert or replace into employee values(?,?,?,?,?,?,?,?,?)");
            setFirstGlassesQuery = conn.prepareStatement("insert or replace into glasses values(?,?,?,?,?,?,?,?)");
            getAllShopsQuery = conn.prepareStatement("Select * from shop");
            deleteShopQuery = conn.prepareStatement("Delete from shop where id=?");
            addGlassesQuery = conn.prepareStatement("INSERT INTO glasses values(?,?,?,?,?,?,?,?)");
            deleteGlassesQuery = conn.prepareStatement("DELETE from glasses where id=?");
            setDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Employee getEmployee(String name, String password) throws NullPointerException {
        Employee employee = new Employee();
        try {
            getEmoployeeQuery.setString(1, name);
            getEmoployeeQuery.setString(2, getPasswordHash(password));
            ResultSet rs = getEmoployeeQuery.executeQuery();
            if (rs.next()) {
                employee.setId(rs.getInt("id"));
                employee.setName(rs.getString("name"));
                employee.setName(rs.getString("name"));

                employee.setLastName(rs.getString("last_name"));
                employee.setBirthDate(rs.getString("birth_date"));

                employee.setAddress(rs.getString("address"));
                employee.setContactNumber(rs.getString("phone_number"));

                employee.setType(parseEmployeeType(rs.getString("type")));
                employee.setPassword_hash(rs.getString("password_hash"));

                employee.setShop(getShop(rs.getInt("shop_id")));
            }

        } catch (SQLException | NoSuchAlgorithmException | InvalidEmployeeDataException e) {
            e.printStackTrace();
        }
        return employee;
    }

    private Employee.Type parseEmployeeType(String v) throws InvalidEmployeeDataException {
        if (Employee.Type.ADMIN.toString().toLowerCase().equals(v.toLowerCase())) {
            return Employee.Type.ADMIN;
        } else if (Employee.Type.OWNER.toString().toLowerCase().equals(v.toLowerCase())) {
            return Employee.Type.OWNER;
        } else if (Employee.Type.EMPLOYEE.toString().toLowerCase().equals(v.toLowerCase())) {
            return Employee.Type.EMPLOYEE;
        } else {
            throw new InvalidEmployeeDataException("Nepostojeci tip zaposlenika");
        }

    }

    public ArrayList<Employee> getAllEmployees() {
        ArrayList<Employee> zaposlenici = new ArrayList<>();
        try {
            ResultSet rs = getAllEmployeeQuery.executeQuery();
            if (!rs.next()) {
                return zaposlenici;
            }
            do {
                Employee e = new Employee(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("last_name"),
                        rs.getString("birth_date"),
                        rs.getString("address"),
                        rs.getString("phone_number"),
                        parseEmployeeType(rs.getString("type")),
                        rs.getString("password_hash"),
                        getShop(rs.getInt("shop_id")));
                zaposlenici.add(e);
            } while (rs.next());
        } catch (SQLException | InvalidEmployeeDataException e) {
            e.printStackTrace();
        }
        return zaposlenici;
    }

    public Shop getShop(int id) {
        Shop s = null;
        try {
            getShopQuery.setInt(1, id);
            ResultSet rs = getShopQuery.executeQuery();
            if (!rs.next()) return null;
            s = new Shop(rs.getInt("id"), rs.getString("shop_name"), rs.getString("address"));
            return s;
        } catch (SQLException e) {
            e.printStackTrace();
            return s;
        }
    }

    public ArrayList<Glasses> getGlassesFromShop(Shop shop) {
        ArrayList<Glasses> naocale = new ArrayList<>();
        try {
            getGlassesQuery.setInt(1, shop.getId());
            ResultSet rs = getGlassesQuery.executeQuery();
            while (rs.next()) {
                if (rs.getString("type").toLowerCase().equals("sunglasses")) {
                    Sunglasses g = new Sunglasses(rs.getInt("id"), rs.getString("manufacturer"),
                            rs.getString("model"), rs.getString("year_of_production"),
                            rs.getInt("price"), getShop(rs.getInt("shop_id")),
                            rs.getInt("quantity"));
                    naocale.add(g);
                } else if (rs.getString("type").toLowerCase().equals("prescription")) {
                    PrescriptionGlasses g = new PrescriptionGlasses(rs.getInt("id"), rs.getString("manufacturer"),
                            rs.getString("model"), rs.getString("year_of_production"), rs.getInt("price"), getShop(rs.getInt("shop_id")),
                            rs.getInt("quantity"));
                    naocale.add(g);
                }
            }
            return naocale;
        } catch (SQLException e) {
            e.printStackTrace();
            return naocale;
        }
    }

    public void addEmployee(Employee e) {
        try {
            //int id, String name, String lastName, String birthDate, String address, String contactNumber, int type, String password, int shop
            addEmoloyeeQuery.setInt(1, getNewEmployeeId());
            addEmoloyeeQuery.setString(2, e.getName());
            addEmoloyeeQuery.setString(3, e.getLastName());
            addEmoloyeeQuery.setString(4, e.getBirthDate());
            addEmoloyeeQuery.setString(5, e.getAddress());
            addEmoloyeeQuery.setString(6, e.getContactNumber());
            addEmoloyeeQuery.setString(7, e.getPassword_hash());
            addEmoloyeeQuery.setString(8, e.getType().toString());
            addEmoloyeeQuery.setInt(9, e.getShop().getId());
            addEmoloyeeQuery.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void sellGlasses(Glasses glasses) {
        try {
            sellGlassesQuery.setInt(1, glasses.getId());
            sellGlassesQuery.executeUpdate();
            System.out.println("uspjesno prodane naocale");
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " greska");
        }
    }
    public void addShop(Shop s) {
        try {
            addShopQuery.setInt(1, getNewShopId());
            addShopQuery.setString(2, s.getShopName());
            addShopQuery.setString(3, s.getAddress());
            addShopQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteEmployee(Employee employee) {
        try {
            deleteEmployeeQuery.setInt(1, employee.getId());
            deleteEmployeeQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String getPasswordHash(String password) throws NoSuchAlgorithmException {
        byte[] hash = getSHA(password);
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }


    private void resetDatabase() {
        Scanner ulaz = null;
        try {
            System.out.println(Paths.get(".").toAbsolutePath().normalize().toString());
            ulaz = new Scanner(new FileInputStream("classes/database/optika.db.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if (sqlUpit.charAt(sqlUpit.length() - 1) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            ulaz.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void removeInstance() {
        if (instance == null) return;
        instance.close();
        instance = null;
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setDatabase() {
        try {
            setFirstShopQuery.setInt(1, 1);
            setFirstShopQuery.setString(2, "Oko");
            setFirstShopQuery.setString(3, "Trg Alije Izetbegovica bb");
            setFirstShopQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            setFirstEmployeeQuery.setInt(1, 1);
            setFirstEmployeeQuery.setString(2, "admin");
            setFirstEmployeeQuery.setString(3, "admin");
            setFirstEmployeeQuery.setString(4, "1/1/1970");
            setFirstEmployeeQuery.setString(5, "Zmaja od Bosne bb");
            setFirstEmployeeQuery.setString(6, "911");
            setFirstEmployeeQuery.setString(7, getPasswordHash("admin"));
            setFirstEmployeeQuery.setString(8, "admin");
            setFirstEmployeeQuery.setInt(9, 1);
            setFirstEmployeeQuery.executeUpdate();
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            setFirstEmployeeQuery.setInt(1, 2);
            setFirstEmployeeQuery.setString(2, "employee");
            setFirstEmployeeQuery.setString(3, "employee");
            setFirstEmployeeQuery.setString(4, "1/1/1987");
            setFirstEmployeeQuery.setString(5, "Employee st.20");
            setFirstEmployeeQuery.setString(6, "060123123");
            setFirstEmployeeQuery.setString(7, getPasswordHash("employee"));
            setFirstEmployeeQuery.setString(8, "employee");
            setFirstEmployeeQuery.setInt(9, 1);
            setFirstEmployeeQuery.executeUpdate();
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            setFirstGlassesQuery.setInt(1, 1);
            setFirstGlassesQuery.setString(2, "rayban");
            setFirstGlassesQuery.setString(3, "aviator");
            setFirstGlassesQuery.setString(4, "2010");
            setFirstGlassesQuery.setInt(5, 256);
            setFirstGlassesQuery.setString(6, "Sunglasses");
            setFirstGlassesQuery.setInt(7, 1);
            setFirstGlassesQuery.setInt(8, 11);
            setFirstGlassesQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getNewEmployeeId() {
        try {
            ResultSet rs = getEmployeeIndexQuery.executeQuery();
            idEmployee = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ++idEmployee;
    }

    private int getNewShopId() {
        try {
            ResultSet rs = getShopIndexQuery.executeQuery();
            idShop = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ++idShop;
    }

    private int getNewGlassesId() {
        try {
            ResultSet rs = getGlassesIndexQuery.executeQuery();
            idGlasses = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ++idGlasses;
    }

    public ArrayList<Shop> getAllShops() {
        ArrayList<Shop> shops = new ArrayList<>();
        try {
            ResultSet rs = getAllShopsQuery.executeQuery();
            while (rs.next()) {
                Shop shop = new Shop(rs.getInt("id"), rs.getString("shop_name"), rs.getString("address"));
                shops.add(shop);
            }
            return shops;
        } catch (SQLException e) {
            e.printStackTrace();
            return shops;
        }
    }

    public void deleteShop(Shop shop) {
        try {
            deleteShopQuery.setInt(1, shop.id);
            deleteShopQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addGlasses(Glasses glasses) {
        try {
            addGlassesQuery.setInt(1, getNewGlassesId());
            addGlassesQuery.setString(2, glasses.getManufacturer());
            addGlassesQuery.setString(3, glasses.getModel());
            addGlassesQuery.setString(4, glasses.getYearOfProduction());
            addGlassesQuery.setInt(5, glasses.getPrice());
            addGlassesQuery.setString(6, glasses.getType());
            addGlassesQuery.setInt(7, glasses.getShop().getId());
            addGlassesQuery.setInt(8, glasses.getQuantity());
            addGlassesQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteGlasses(Glasses g) {
        try{
            deleteGlassesQuery.setInt(1, g.getId());
            deleteGlassesQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}