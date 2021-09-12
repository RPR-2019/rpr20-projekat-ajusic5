package ba.unsa.etf.rpr;

import java.time.LocalDate;

public class Patient extends User {

    private int brojKartona;

    public Patient() {
    }

    public Patient(int id, String prezime, String ime, LocalDate datumRodjenja, String username, String password, ProfileType vrsta, SexOfAUser sexOfAUser, int brojKartona) {
        super(id, prezime, ime, datumRodjenja, username, password, vrsta, sexOfAUser);
        this.brojKartona = brojKartona;
    }

    public int getBrojKartona() {
        return brojKartona;
    }

    public void setBrojKartona(int brojKartona) {
        this.brojKartona = brojKartona;
    }
}
