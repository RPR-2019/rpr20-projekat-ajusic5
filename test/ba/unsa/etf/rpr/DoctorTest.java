package ba.unsa.etf.rpr;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DoctorTest {

    @Test
    void doctorConstructorTest() {
        Doctor doctor = new Doctor(1, "Hanić", "Hana", LocalDate.of(1999, 9, 7), "hhanic1", "hana", ProfileType.LJEKAR, SexOfAUser.ZENSKI, "Oftamologija");

        assertEquals(1, doctor.getId());
        assertNull(doctor.getServices());
    }
}