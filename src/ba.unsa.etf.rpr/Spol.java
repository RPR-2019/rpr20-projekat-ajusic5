package ba.unsa.etf.rpr;

public enum Spol {
    ZENSKI("Ž"), MUSKI("M");

    private final String name;

    Spol(String name) {
        this.name=name;
    }

    @Override
    public String toString(){
        return name;
    }
}
