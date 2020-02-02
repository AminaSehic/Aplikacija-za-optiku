package ba.unsa.etf.rpr.projekat;

import ba.unsa.etf.rpr.projekat.Models.Employee;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class OptikaDAOTest {
    private OptikaDAO dao;
    @BeforeEach
    void setUp() {
        dao = OptikaDAO.getInstance();
    }
//String name, String lastName, String birthDate, String address, String contactNumber, String password_hash, Type type, Shop shop
    @Test
    void dodajZaposlenika() {
        Employee e = new Employee(100, "Haso", "Hasic", "02/02/1970", "Pofalicka 16", "060123456", Employee.Type.OWNER,"882c77869c5291190178e6a7caa7e0d166fbf327d1f60ac070eed0ca2835dc37",  dao.dajRadnju(1) );
        dao.dodajZaposlenika(e);
        Employee zaposlenik;
        zaposlenik = dao.dajZaposlenika("Haso", "haso");
        assertNotNull(zaposlenik);
    }

    @Test
    void dajZaposlenika() {
    }

    @Test
    void dajSveZaposlenike() {
    }

    @Test
    void dajRadnju() {
    }

    @Test
    void dajNaocaleIzRadnje() {
    }


    @Test
    void prodajNaocale() {
    }

    @Test
    void dodajRadnju() {
    }

    @Test
    void deleteEmployee() {
    }

    @Test
    void dajPasswordHash() {
    }

    @Test
    void getAllShops() {
    }

    @Test
    void deleteShop() {
    }

    @Test
    void addGlasses() {
    }

    @Test
    void deleteGlasses() {
    }
}