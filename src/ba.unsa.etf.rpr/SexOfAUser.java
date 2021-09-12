package ba.unsa.etf.rpr;

public enum SexOfAUser {
    ZENSKI("Ž"), MUSKI("M");

    private final String name;

    SexOfAUser(String name) {
        this.name=name;
    }

    @Override
    public String toString(){
        return name;
    }
}
