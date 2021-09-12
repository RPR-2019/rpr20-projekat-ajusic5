package ba.unsa.etf.rpr;

import java.time.LocalDate;
import java.util.List;

public class Doctor extends User {

    private String specialization;
    private List<String> services;

    public Doctor(int id, String prezime, String ime, LocalDate datumRodjenja, String username, String password, ProfileType vrsta, SexOfAUser sexOfAUser, String specialization) {
        super(id, prezime, ime, datumRodjenja, username, password, vrsta, sexOfAUser);
        this.specialization = specialization;
    }

    public Doctor(int id, String prezime, String ime, LocalDate datumRodjenja, String username, String password, ProfileType vrsta, SexOfAUser sexOfAUser, String specialization, List<String> services) {
        super(id, prezime, ime, datumRodjenja, username, password, vrsta, sexOfAUser);
        this.specialization = specialization;
        this.services = services;
    }


    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }
}
