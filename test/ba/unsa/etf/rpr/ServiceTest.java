package ba.unsa.etf.rpr;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    @Test
    void serviceConstructorTest(){
        Service service = new Service(1, "Ultrazvuk srca");

        assertEquals("Ultrazvuk srca", service.getName());
    }



}