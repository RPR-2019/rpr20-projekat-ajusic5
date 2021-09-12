package ba.unsa.etf.rpr;

import java.time.LocalDateTime;

public class Pregled {

    private int id;
    private Pacijent pacijent;
    private Ljekar ljekar;
    private String vrstaPregleda;
    private String dijagnoza;
    private String terapija;
    private LocalDateTime datumIVrijemePregleda;
    private LocalDateTime vrijemeZakazivanjaTermina; // treba za mogućnost otkazivanja u roku od 24h
    private boolean uspjesan;
    private boolean arhiviran;

    public Pregled() {
    }

    public Pregled(int id, Pacijent pacijent, String vrstaPregleda, LocalDateTime datumIVrijemePregleda, LocalDateTime vrijemeZakazivanjaTermina) {
        this.id = id;
        this.pacijent = pacijent;
        this.vrstaPregleda = vrstaPregleda;
        this.datumIVrijemePregleda = datumIVrijemePregleda;
        this.vrijemeZakazivanjaTermina = vrijemeZakazivanjaTermina;
    }

    public Pregled(int id, Pacijent pacijent, Ljekar ljekar, String vrstaPregleda, String dijagnoza, String terapija, LocalDateTime datumIVrijemePregleda, LocalDateTime vrijemeZakazivanjaTermina, boolean uspjesan, boolean arhiviran) {
        this.id = id;
        this.pacijent = pacijent;
        this.ljekar = ljekar;
        this.vrstaPregleda = vrstaPregleda;
        this.dijagnoza = dijagnoza;
        this.terapija = terapija;
        this.datumIVrijemePregleda = datumIVrijemePregleda;
        this.vrijemeZakazivanjaTermina = vrijemeZakazivanjaTermina;
        this.uspjesan = uspjesan;
        this.arhiviran = arhiviran;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ljekar getLjekar() {
        return ljekar;
    }

    public void setLjekar(Ljekar ljekar) {
        this.ljekar = ljekar;
    }

    public Pacijent getPacijent() {
        return pacijent;
    }

    public void setPacijent(Pacijent pacijent) {
        this.pacijent = pacijent;
    }

    public String getVrstaPregleda() {
        return vrstaPregleda;
    }

    public void setVrstaPregleda(String vrstaPregleda) {
        this.vrstaPregleda = vrstaPregleda;
    }

    public String getDijagnoza() {
        return dijagnoza;
    }

    public void setDijagnoza(String dijagnoza) {
        this.dijagnoza = dijagnoza;
    }

    public String getTerapija() {
        return terapija;
    }

    public void setTerapija(String terapija) {
        this.terapija = terapija;
    }

    public LocalDateTime getDatumIVrijemePregleda() {
        return datumIVrijemePregleda;
    }

    public void setDatumIVrijemePregleda(LocalDateTime datumIVrijemePregleda) {
        this.datumIVrijemePregleda = datumIVrijemePregleda;
    }

    public LocalDateTime getVrijemeZakazivanjaTermina() {
        return vrijemeZakazivanjaTermina;
    }

    public void setVrijemeZakazivanjaTermina(LocalDateTime vrijemeZakazivanjaTermina) {
        this.vrijemeZakazivanjaTermina = vrijemeZakazivanjaTermina;
    }

    public boolean isUspjesan() {
        return uspjesan;
    }

    public void setUspjesan(boolean uspjesan) {
        this.uspjesan = uspjesan;
    }

    public boolean isArhiviran() {
        return arhiviran;
    }

    public void setArhiviran(boolean arhiviran) {
        this.arhiviran = arhiviran;
    }

    @Override
    public String toString(){
        return pacijent.getPrezime()+" "+pacijent.getIme()+" "+datumIVrijemePregleda.toString()+" "+vrstaPregleda;
    }
}
