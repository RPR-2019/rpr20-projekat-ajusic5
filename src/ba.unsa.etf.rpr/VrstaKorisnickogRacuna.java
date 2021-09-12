package ba.unsa.etf.rpr;

public enum VrstaKorisnickogRacuna {
    PACIJENT("Pacijent"), LJEKAR("Ljekar");

    private final String name;

    VrstaKorisnickogRacuna(String name) {
        this.name = name;
    }
    @Override
    public String toString(){
        return name;
    }
}
