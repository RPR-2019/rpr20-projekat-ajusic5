package ba.unsa.etf.rpr;

import java.time.LocalDate;

public class Patient extends User {

    private int patientCardNumber;

    public Patient() {
    }

    public Patient(int id, String surname, String name, LocalDate dateOfBirth, String username, String password, ProfileType profileType, SexOfAUser sexOfAUser, int patientCardNumber) {
        super(id, surname, name, dateOfBirth, username, password, profileType, sexOfAUser);
        this.patientCardNumber = patientCardNumber;
    }

    public int getPatientCardNumber() {
        return patientCardNumber;
    }

    public void setPatientCardNumber(int patientCardNumber) {
        this.patientCardNumber = patientCardNumber;
    }
}
