package ba.unsa.etf.rpr;

import org.junit.jupiter.api.Test;

import javax.print.Doc;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DoctorTest {

    @Test
    void doctorConstructorTest() {
        Doctor doctor = new Doctor(1, "Hanić", "Hana", LocalDate.of(1999, 9, 7), "hhanic1", "hana", ProfileType.LJEKAR, SexOfAUser.ZENSKI, "Oftamologija");

        assertEquals(1, doctor.getId());
        assertNull(doctor.getServices());
    }

    @Test
    void doctorConstructorTest2(){
        ArrayList<String> services = new ArrayList<>();
        services.add("Ultrazvuk srca");

        Doctor doctor = new Doctor(1, "Hanić", "Hana", LocalDate.of(1999, 9, 7), "hhanic1", "hana", ProfileType.LJEKAR, SexOfAUser.ZENSKI, "Oftamologija", services);

        assertEquals("Hanić", doctor.getSurname());
        assertNotNull(doctor.getServices());
    }
}