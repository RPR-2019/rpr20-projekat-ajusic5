package ba.unsa.etf.rpr;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ExaminationTest {

    @Test
    void examinationConstructorTest() {
        Patient patient = new Patient(1, "Jusić", "Amna", LocalDate.of(1999, 5, 18), "ajusic5", "amna", ProfileType.PACIJENT, SexOfAUser.ZENSKI, 79999);
        Doctor doctor = new Doctor(1, "Hanić", "Hana", LocalDate.of(1999, 9, 7), "hhanic1", "hana", ProfileType.LJEKAR, SexOfAUser.ZENSKI, "Oftamologija");

        Examination examination = new Examination(1, patient, doctor, "Oftamološki pregled", null, null, LocalDateTime.now(), LocalDateTime.now(), false, false);

        assertEquals(1, examination.getPatient().getId());
        assertEquals("Hana", examination.getDoctor().getName());
    }

    @Test
    void examinationConstructorTest2(){
        Examination examination = new Examination();

        assertNull(examination.getDoctor());
    }
}