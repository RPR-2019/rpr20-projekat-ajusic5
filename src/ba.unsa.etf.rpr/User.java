package ba.unsa.etf.rpr;

import java.time.LocalDate;

public class User {
    private int id;
    private String surname;
    private String name;
    private LocalDate dateOfBirth;
    private String username;
    private String password;
    private ProfileType profileType;
    private SexOfAUser sexOfAUser;

    public User() {
    }

    public User(int id, String surname, String name, LocalDate dateOfBirth, String username, String password, ProfileType profileType, SexOfAUser sexOfAUser) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.username = username;
        this.password = password;
        this.profileType = profileType;
        this.sexOfAUser = sexOfAUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() throws EmptyStringException {
        if(surname ==null || surname.equals(""))
            throw new EmptyStringException("Nemoguće da osoba nema prezime!");
        return surname;
    }

    public void setSurname(String surname) throws EmptyStringException {
        if(surname ==null || surname.equals(""))
            throw new EmptyStringException("Nemoguće da osoba nema prezime!");
        this.surname = surname;
    }

    public String getName() throws EmptyStringException {
        if(name ==null || name.equals(""))
            throw new EmptyStringException("Nemoguće da osoba nema ime!");
        return name;
    }

    public void setName(String name) throws EmptyStringException {

        if(name ==null || name.equals(""))
            throw new EmptyStringException("Nemoguće da osoba nema ime!");

        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public String getProfileType() {
        return profileType.toString();
    }

    public void setProfileType(ProfileType profileType) {
        this.profileType = profileType;
    }

    public String getSpol() {
        return sexOfAUser.toString();
    }

    public void setSpol(SexOfAUser sexOfAUser) {
        this.sexOfAUser = sexOfAUser;
    }
}
