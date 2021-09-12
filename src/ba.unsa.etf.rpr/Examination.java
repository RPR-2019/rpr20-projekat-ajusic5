package ba.unsa.etf.rpr;

import java.time.LocalDateTime;

public class Examination {

    private int id;
    private Patient patient;
    private Doctor doctor;
    private String vrstaPregleda;
    private String dijagnoza;
    private String terapija;
    private LocalDateTime datumIVrijemePregleda;
    private LocalDateTime vrijemeZakazivanjaTermina; // treba za mogućnost otkazivanja u roku od 24h
    private boolean uspjesan;
    private boolean arhiviran;

    public Examination() {
    }

    public Examination(int id, Patient patient, String vrstaPregleda, LocalDateTime datumIVrijemePregleda, LocalDateTime vrijemeZakazivanjaTermina) {
        this.id = id;
        this.patient = patient;
        this.vrstaPregleda = vrstaPregleda;
        this.datumIVrijemePregleda = datumIVrijemePregleda;
        this.vrijemeZakazivanjaTermina = vrijemeZakazivanjaTermina;
    }

    public Examination(int id, Patient patient, Doctor doctor, String vrstaPregleda, String dijagnoza, String terapija, LocalDateTime datumIVrijemePregleda, LocalDateTime vrijemeZakazivanjaTermina, boolean uspjesan, boolean arhiviran) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
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

    public Doctor getLjekar() {
        return doctor;
    }

    public void setLjekar(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPacijent() {
        return patient;
    }

    public void setPacijent(Patient patient) {
        this.patient = patient;
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
        return patient.getPrezime()+" "+ patient.getIme()+" "+datumIVrijemePregleda.toString()+" "+vrstaPregleda;
    }
}
