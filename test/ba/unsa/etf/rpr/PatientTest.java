package ba.unsa.etf.rpr;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PatientTest {

    @Test
    void patientConstructorTest() {

        Patient p = new Patient(1, "Jusić", "Amna", LocalDate.of(1999, 5, 18), "ajusic5", "amna", ProfileType.PACIJENT, SexOfAUser.ZENSKI, 79999);

        assertEquals("Amna", p.getName());
        assertEquals("Jusić", p.getSurname());
    }


}