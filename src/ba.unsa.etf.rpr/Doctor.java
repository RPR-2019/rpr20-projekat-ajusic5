package ba.unsa.etf.rpr;

import java.time.LocalDate;
import java.util.List;

public class Doctor extends User {

    private String specijalizacija;
    private List<String> usluge;

    public Doctor(int id, String prezime, String ime, LocalDate datumRodjenja, String username, String password, ProfileType vrsta, SexOfAUser sexOfAUser, String specijalizacija) {
        super(id, prezime, ime, datumRodjenja, username, password, vrsta, sexOfAUser);
        this.specijalizacija=specijalizacija;
    }

    public Doctor(int id, String prezime, String ime, LocalDate datumRodjenja, String username, String password, ProfileType vrsta, SexOfAUser sexOfAUser, String specijalizacija, List<String> usluge) {
        super(id, prezime, ime, datumRodjenja, username, password, vrsta, sexOfAUser);
        this.specijalizacija = specijalizacija;
        this.usluge = usluge;
    }

    public Doctor(int id, String prezime, String ime, String username, String password, LocalDate datumRodjenja, ProfileType vrsta, SexOfAUser sexOfAUser, String specijalizacija) {
        super(id, prezime, ime, datumRodjenja, username, password, vrsta, sexOfAUser);
        this.specijalizacija = specijalizacija;
    }


    public String getSpecijalizacija() {
        return specijalizacija;
    }

    public void setSpecijalizacija(String specijalizacija) {
        this.specijalizacija = specijalizacija;
    }

    public List<String> getUsluge() {
        return usluge;
    }

    public void setUsluge(List<String> usluge) {
        this.usluge = usluge;
    }
}
