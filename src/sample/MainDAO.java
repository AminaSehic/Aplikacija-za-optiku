package sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MainDAO {
    private static MainDAO instance;
    private Connection conn;
    private PreparedStatement dodajRadnjuUpit,  dajZaposlenikaUpit, dajSveZaposlenikeUpit,
            dajZaposlenikeIzRadnjeUpit, dajRadnjuUpit, dajNaocaleIzRadnjeUpit, prodajNaocaleUpit, dodajZaposlenikaUpit;

    public static MainDAO getInstance() {
        if (instance == null) instance = new MainDAO();
        return instance;
    }

    private MainDAO() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:optika.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            dajZaposlenikaUpit = conn.prepareStatement("select * from employee where name=? and password=?");
        } catch (Exception e) {
            regenerisiBazu();
            try {
                dajZaposlenikaUpit = conn.prepareStatement("select * from employee where name=? and password=?");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        try {
            dajSveZaposlenikeUpit = conn.prepareStatement("select * from employee");
            dajZaposlenikeIzRadnjeUpit = conn.prepareStatement("select * from employee where shop_id=?");
            dajRadnjuUpit = conn.prepareStatement("select * from shop where id=?");
            dajNaocaleIzRadnjeUpit = conn.prepareStatement("select * from glasses where shop_id=?");
            prodajNaocaleUpit = conn.prepareStatement("update glasses set number=(number-1) where id=?");
            dodajZaposlenikaUpit = conn.prepareStatement("insert into employee values(?,?,?,?,?,?,?,?,?)");
            dodajRadnjuUpit = conn.prepareStatement("insert into shop values(?,?,?)");
            System.out.println("zdravo");
        } catch (SQLException e) {
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

    public Employee dajZaposlenika(String name, String password) {
        Employee korisnik = new Employee();
        try {
            dajZaposlenikaUpit.setString(1, name);
            dajZaposlenikaUpit.setString(2, password);
            ResultSet rs = dajZaposlenikaUpit.executeQuery();
            try {
                return new Employee(rs.getString("name"),
                        rs.getString("lastName"), rs.getString("birthDate"),
                        rs.getString("address"), rs.getString("contactNumber"),
                        rs.getInt("type"), rs.getString("password"),
                        rs.getInt("shop_id"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Employee> dajSveZaposlenike() {
        ArrayList<Employee> zaposlenici = new ArrayList<>();
        try {
            ResultSet rs = dajSveZaposlenikeUpit.executeQuery();
            if (!rs.next()) {
                return null;
            }
            do{
                Employee e = new Employee(rs.getString("name"), rs.getString("lastName"), rs.getString("birthDate"), rs.getString("address"), rs.getString("contactNumber"), rs.getInt("type"), rs.getString("password"), rs.getInt("shop_id"));
                zaposlenici.add(e);
            } while(rs.next());
            return zaposlenici;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Employee> dajZaposlenikeIzRadnje() {
        ArrayList<Employee> zaposlenici = new ArrayList<>();
        try {
            ResultSet rs = dajSveZaposlenikeUpit.executeQuery();
            if (!rs.next()) {
                return null;
            }
            while (rs.next()) {
                Employee e = new Employee(rs.getString("name"), rs.getString("lastName"), rs.getString("birthDate"), rs.getString("address"), rs.getString("contactNumber"), rs.getInt("type"), rs.getString("password"), rs.getInt("shop_id"));
                zaposlenici.add(e);
            }
            return zaposlenici;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Shop dajRadnju(int id) {
        try {
            System.out.println("id poslan " + id);
            dajRadnjuUpit.setInt(1, id);
            ResultSet rs = dajRadnjuUpit.executeQuery();
            if(!rs.next()) return null;
            Shop s = new Shop(rs.getInt("id"),rs.getString("shopName"), rs.getString("address"));
            System.out.println(s.getId() + " " + s.getShopName() +" " + s.getAddress());
            return s;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Glasses> dajNaocaleIzRadnje(int id_radnje) {
        ArrayList<Glasses> naocale = new ArrayList<>();
        try {
            dajNaocaleIzRadnjeUpit.setInt(1, id_radnje);
            ResultSet rs = dajNaocaleIzRadnjeUpit.executeQuery();
            while (rs.next()) {
                Glasses g = new Glasses(rs.getInt("id"),rs.getString("manufacturer"),
                        rs.getString("model"), rs.getInt("yearOfProduction"),
                        rs.getInt("type"), rs.getInt("price"), rs.getInt("shop_id"),
                        rs.getInt("number"));
                naocale.add(g);
            }
            return naocale;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void prodajNaocale(int id) {
        try {
            prodajNaocaleUpit.setInt(1, id);
            prodajNaocaleUpit.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dodajZaposlenika(Employee e) {
        try {
            //int id, String name, String lastName, String birthDate, String address, String contactNumber, int type, String password, int shop
            dodajZaposlenikaUpit.setInt(1, e.getId());
            dodajZaposlenikaUpit.setString(2, e.getName());
            dodajZaposlenikaUpit.setString(3, e.getLastName());
            dodajZaposlenikaUpit.setString(4, e.getBirthDate());
            dodajZaposlenikaUpit.setString(5, e.getAddress());
            dodajZaposlenikaUpit.setString(6, e.getContactNumber());
            dodajZaposlenikaUpit.setString(7, e.getPassword());
            dodajZaposlenikaUpit.setInt(8, e.getShop().getId());
            dodajZaposlenikaUpit.setInt(9, e.getType());
            System.out.println("Uspjesno dodan radnik");
            dodajZaposlenikaUpit.execute();
            System.out.println("Uspjesno dodan radnik");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void dodajRadnju(Shop s){
        try{
            dodajRadnjuUpit.setInt(1, s.getId());
            dodajRadnjuUpit.setString(2, s.getShopName());
            dodajRadnjuUpit.setString(3, s.getAddress());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void regenerisiBazu() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("optika.db.sql"));
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


}
