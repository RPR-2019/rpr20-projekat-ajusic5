package ba.unsa.etf.rpr;

import java.time.LocalDate;

public class Pacijent extends Korisnik{

    private int brojKartona;

    public Pacijent() {
    }

    public Pacijent(int id, String prezime, String ime, LocalDate datumRodjenja, String username, String password, VrstaKorisnickogRacuna vrsta, String spol, int brojKartona) {
        super(id, prezime, ime, datumRodjenja, username, password, vrsta, spol);
        this.brojKartona = brojKartona;
    }

    public int getBrojKartona() {
        return brojKartona;
    }

    public void setBrojKartona(int brojKartona) {
        this.brojKartona = brojKartona;
    }
}
