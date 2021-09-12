package ba.unsa.etf.rpr;

import java.time.LocalDate;
import java.util.List;

public class Ljekar extends Korisnik{

    private String specijalizacija;
    private List<String> usluge;

    public Ljekar(int id, String prezime, String ime, LocalDate datumRodjenja, String username, String password, VrstaKorisnickogRacuna vrsta, String spol, String specijalizacija) {
        super(id, prezime, ime, datumRodjenja, username, password, vrsta, spol);
        this.specijalizacija=specijalizacija;
    }

    public Ljekar(int id, String prezime, String ime, LocalDate datumRodjenja, String username, String password, VrstaKorisnickogRacuna vrsta, String spol, String specijalizacija, List<String> usluge) {
        super(id, prezime, ime, datumRodjenja, username, password, vrsta, spol);
        this.specijalizacija = specijalizacija;
        this.usluge = usluge;
    }

    public Ljekar(int id, String prezime, String ime, String username, String password, LocalDate datumRodjenja, VrstaKorisnickogRacuna vrsta, String spol, String specijalizacija) {
        super(id, prezime, ime, datumRodjenja, username, password, vrsta, spol);
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
