package ba.unsa.etf.rpr;

import java.time.LocalDate;

public class Korisnik {
    private int id;
    private String prezime;
    private String ime;
    private LocalDate datumRodjenja;
    private String username;
    private String password;
    private VrstaKorisnickogRacuna vrsta;
    private Spol spol;

    public Korisnik() {
    }

    public Korisnik(int id, String prezime, String ime, LocalDate datumRodjenja, String username, String password, VrstaKorisnickogRacuna vrsta, Spol spol) {
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

    public String getPrezime() throws EmptyStringException {
        if(prezime==null || prezime.equals(""))
            throw new EmptyStringException("Nemoguće da osoba nema prezime!");
        return prezime;
    }

    public void setPrezime(String prezime) throws EmptyStringException {
        if(prezime==null || prezime.equals(""))
            throw new EmptyStringException("Nemoguće da osoba nema prezime!");
        this.prezime = prezime;
    }

    public String getIme() throws EmptyStringException {
        if(ime==null || ime.equals(""))
            throw new EmptyStringException("Nemoguće da osoba nema ime!");
        return ime;
    }

    public void setIme(String ime) throws EmptyStringException {

        if(ime==null || ime.equals(""))
            throw new EmptyStringException("Nemoguće da osoba nema ime!");

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
        return vrsta.toString();
    }

    public void setVrsta(VrstaKorisnickogRacuna vrsta) {
        this.vrsta = vrsta;
    }

    public String getSpol() {
        return spol.toString();
    }

    public void setSpol(Spol spol) {
        this.spol = spol;
    }
}
