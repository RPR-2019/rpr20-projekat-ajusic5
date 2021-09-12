package ba.unsa.etf.rpr;

public enum ProfileType {
    PACIJENT("Pacijent"), LJEKAR("Ljekar");

    private final String name;

    ProfileType(String name) {
        this.name = name;
    }
    @Override
    public String toString(){
        return name;
    }
}
