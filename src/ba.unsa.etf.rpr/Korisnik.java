package ba.unsa.etf.rpr;

import java.time.LocalDate;

public class Korisnik {
    private int id;
    private String prezime;
    private String ime;
    private LocalDate datumRodjenja;
    private String username;
    private String password;
    private String vrsta;
    private String spol;

    public Korisnik() {
    }

    public Korisnik(int id, String prezime, String ime, LocalDate datumRodjenja, String username, String password, String vrsta, String spol) {
        this.id = id;
        this.prezime = prezime;
        this.ime = ime;
        this.datumRodjenja = datumRodjenja;
        this.username = username;
        this.password = password;
        this.vrsta = vrsta;
        this.spol = spol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public LocalDate getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(LocalDate datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVrsta() {
        return vrsta;
    }

    public void setVrsta(String vrsta) {
        this.vrsta = vrsta;
    }

    public String getSpol() {
        return spol;
    }

    public void setSpol(String spol) {
        this.spol = spol;
    }
}
