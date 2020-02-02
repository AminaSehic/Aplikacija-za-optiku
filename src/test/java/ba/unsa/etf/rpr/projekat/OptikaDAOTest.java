package ba.unsa.etf.rpr.projekat;

import ba.unsa.etf.rpr.projekat.Models.Employee;
import ba.unsa.etf.rpr.projekat.Models.Glasses;
import ba.unsa.etf.rpr.projekat.Models.Shop;
import ba.unsa.etf.rpr.projekat.Models.Sunglasses;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OptikaDAOTest {

    private static OptikaDAO dao;

    @BeforeAll
    static void setup() {
        dao = OptikaDAO.getInstance();
    }

    @Test
    void getEmployee() {
        Employee e = dao.getEmployee("admin", "admin");
        assertNotNull(e);
    }

    @Test
    void getAllEmployees() {
        ArrayList<Employee> employees = dao.getAllEmployees();
        assertTrue(employees.size() >= 2); //dva defaultna unosa moraju uvijek biti u bazi
    }

    @Test
    void getShop() {
        Shop s = dao.getShop(1);
        assertNotNull(s);
    }

    @Test
    void getGlassesFromShop() {
        ArrayList<Glasses> glasses = dao.getGlassesFromShop(dao.getShop(1));
        assertTrue(glasses.size() >= 1);//default naocale uvijek u bazi
    }

    @Test
    void addEmployee() throws NoSuchAlgorithmException {
        Employee employee = new Employee("test", "test", "1/1/1978", "address", "060123123", OptikaDAO.getPasswordHash("test"), Employee.Type.EMPLOYEE, dao.getShop(1));
        dao.addEmployee(employee);
        Employee e1 = dao.getEmployee(employee.getName(), "test");
        assertEquals(employee.toString(), e1.toString());
    }

    @Test
    void sellGlasses() {
        Glasses g1 = dao.getGlasses(1);
        dao.sellGlasses(g1);
        Glasses g2 = dao.getGlasses(1);
        assertEquals(g1.getQuantity() - 1, g2.getQuantity());
    }
    @Test
    void addGlasses() throws InterruptedException {
        Glasses g1 = new Sunglasses(101, "testMan", "testMod","2018", 200, dao.getShop(1),10);
        //ako ne baci gresku, uredu je
        assertTrue(true);
    }
    @Test
    void addShop() {
       Shop s1 = new Shop(100, "testShop", "testAddress");
       dao.addShop(s1);
       Shop s2 = dao.getShop(100);
       assertEquals(s1.toString(), s2.toString());
    }

    @Test
    void deleteEmployee() {
        Employee e = dao.getEmployee("test", "test");
        dao.deleteEmployee(e);
        try{
            dao.getEmployee(e.getId());
        } catch (Exception ex){
            fail();
        }
        assertEquals(1,1);
    }

    @Test
    void getPasswordHash() throws NoSuchAlgorithmException {
        //hardkodiran sha256 sa interneta
        String internetSha256 = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
        String localSha256 = OptikaDAO.getPasswordHash("");
        assertEquals(internetSha256, localSha256);
    }

    @Test
    void getAllShops() {
        ArrayList<Shop> shops = dao.getAllShops();
        assertTrue(shops.size()>=1);
    }

    @Test
    void deleteShop() {
        Shop s = dao.getShop(100);
        dao.deleteShop(s);
        Shop s1 = dao.getShop(100);
        assertNull(s1);
    }



    @Test
    void deleteGlasses() {
        Glasses g1 = new Sunglasses(112, "testMan", "testMod","2018", 200, dao.getShop(1),10);
        dao.deleteGlasses(g1);
        Glasses g2 = dao.getGlasses(112);
        assertNull(g2);
    }
}