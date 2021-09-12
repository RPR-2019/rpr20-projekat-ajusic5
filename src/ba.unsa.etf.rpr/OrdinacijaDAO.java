package ba.unsa.etf.rpr;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class OrdinacijaDAO {private static OrdinacijaDAO instanca;
    private Connection conn;
    private PreparedStatement pacijentPrijavaUpit, ljekarPrijavaUpit, pacijentRegistracijaUpit, ljekarRegistracijaUpit, zakaziTerminUpit, otkaziTerminUpit, provjeriDaLiPostojiUslugaUpit,
            dodajUsluguUpit, dajSljedeciIdZaPacijentaUpit, dajSljedeciIdZaLjekaraUpit, dajSljedeciIdZaPregledUpit, dajSljedeciIdZaUsluguUpit, dodajUsluguZaLjekaraUpit, dodajTerapijuUpit, poveziLjekaraSaUslugomUpit,
            dajIdZaUsluguUpit, arhivirajPregledUpit, provjeriTerminUpit, dajIdLjekaraUpit, dajSveLjekareUpit, dajSveUslugeUpit, dajUslugeLjekaraUpit, dajZakazanePregledeUpit, dajTrenutnogPrijavljenogPacijentaUpit, dajSveUslugeZaLjekaraUpit,
            dajPregledeKojeLjekarMozeObavitiUpit, dajPregledeKojeJePacijentZakazaoUpit, dajIdLjekaraKojiMoguObavitiPregledUpit, dajIdPacijentaUpit, dajPregledeKojeJePacijentObavioUpit, dajPregledeKojeJeLjekarObavioUpit, dajLjekaraUpit, dajPregledUpit, dodajDijagnozuUpit, dajPacijentaUpit, obrisiPregledUpit, izbrisiUsluguZaLjekaraUpit, provjeriDaLiJeVecDodanaUslugaUpit, dajTrenutnoPrijavljenogLjekaraUpit, dajIdUslugeUpit;

    private OrdinacijaDAO() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:baza.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            pacijentPrijavaUpit = conn.prepareStatement("SELECT * FROM PACIJENT WHERE username=? AND password=?");
            ljekarPrijavaUpit = conn.prepareStatement("SELECT * FROM LJEKAR WHERE username=? AND password=?");
            pacijentRegistracijaUpit = conn.prepareStatement("INSERT INTO PACIJENT VALUES(?,?,?,?,?,?,?,?,?)");
            ljekarRegistracijaUpit = conn.prepareStatement("INSERT INTO LJEKAR VALUES(?,?,?,?,?,?,?,?,?)");
            zakaziTerminUpit = conn.prepareStatement("INSERT INTO PREGLED VALUES(?,?,?,?,?,?,?,?,?,?)");
            otkaziTerminUpit = conn.prepareStatement("UPDATE PREGLED SET successful=0, doctor_id=-2 WHERE id=?");
            provjeriDaLiPostojiUslugaUpit = conn.prepareStatement("SELECT * FROM USLUGE WHERE name=?");
            dodajUsluguUpit = conn.prepareStatement("INSERT INTO USLUGE VALUES(?,?)");
            dajSljedeciIdZaLjekaraUpit = conn.prepareStatement("SELECT MAX(doctor_id)+1 FROM LJEKAR");
            dajSljedeciIdZaPacijentaUpit = conn.prepareStatement("SELECT MAX(patient_id)+1 FROM PACIJENT");
            dajSljedeciIdZaPregledUpit = conn.prepareStatement("SELECT MAX(id)+1 FROM PREGLED");
            dajSljedeciIdZaUsluguUpit = conn.prepareStatement("SELECT  MAX(id)+1 FROM USLUGE");
            dodajUsluguZaLjekaraUpit = conn.prepareStatement("INSERT INTO LJEKAR_USLUGE VALUES(?,?)");
            dodajTerapijuUpit = conn.prepareStatement("UPDATE PREGLED SET therapy=?, doctor_id=? WHERE id=?");
            poveziLjekaraSaUslugomUpit = conn.prepareStatement("INSERT INTO LJEKAR_USLUGE VALUES(?,?)");
            dajIdZaUsluguUpit = conn.prepareStatement("SELECT id FROM USLUGE WHERE name=?");
            dajSveUslugeUpit = conn.prepareStatement("SELECT * FROM USLUGE");
            dajUslugeLjekaraUpit = conn.prepareStatement("SELECT DISTINCT u.name FROM USLUGE u, lJEKAR_USLUGE lju WHERE lju.doctor_id=? AND lju.med_service_id=u.id");
            dajZakazanePregledeUpit = conn.prepareStatement("SELECT date_and_time_of_appointment, type_of_examination, patient_id, doctor_id FROM PREGLED WHERE successful=1"); //DODATI PROVJERU VREMENA
            dajTrenutnogPrijavljenogPacijentaUpit = conn.prepareStatement("SELECT * FROM PACIJENT WHERE username=? AND password=?");
            dajSveLjekareUpit = conn.prepareStatement("SELECT doctor_id FROM LJEKAR");
            provjeriTerminUpit = conn.prepareStatement("SELECT p.id FROM PREGLED p WHERE p.doctor_id=? AND p.date_and_time_of_appointment=?");
            dajSveUslugeZaLjekaraUpit = conn.prepareStatement("SELECT u.name FROM USLUGE u, LJEKAR lj, lJEKAR_USLUGE lju WHERE u.id=lju.med_service_id AND lju.doctor_id=lj.doctor_id AND lj.doctor_id=?");
            dajIdLjekaraUpit = conn.prepareStatement("SELECT doctor_id FROM LJEKAR where username=?");
            dajTrenutnoPrijavljenogLjekaraUpit = conn.prepareStatement("SELECT * FROM LJEKAR WHERE username=?");
            dajIdUslugeUpit = conn.prepareStatement("SELECT id FROM USLUGE WHERE name=?");
            provjeriDaLiJeVecDodanaUslugaUpit = conn.prepareStatement("SELECT med_service_id FROM LJEKAR_USLUGE WHERE doctor_id=?");
            izbrisiUsluguZaLjekaraUpit = conn.prepareStatement("DELETE FROM LJEKAR_USLUGE WHERE med_service_id=? AND doctor_id=?");
            dajPregledeKojeLjekarMozeObavitiUpit = conn.prepareStatement("SELECT * FROM PREGLED p, LJEKAR_USLUGE lju, USLUGE u WHERE p.archived=0 AND p.doctor_id<>-2 AND p.type_of_examination=u.name AND u.id=lju.med_service_id AND SUBSTR(date_and_time_of_appointment,1,10)=CURRENT_DATE AND lju.doctor_id=?");
            dajPacijentaUpit = conn.prepareStatement("SELECT * FROM PACIJENT WHERE patient_id=?");
            arhivirajPregledUpit = conn.prepareStatement("UPDATE PREGLED SET archived=1, successful=1 WHERE id=?");
            obrisiPregledUpit = conn.prepareStatement("DELETE FROM PREGLED WHERE id=?");
            dodajDijagnozuUpit = conn.prepareStatement("UPDATE PREGLED SET diagnosis=?, doctor_id=?, archived=1 WHERE id=?");
            dajPregledUpit = conn.prepareStatement("SELECT * FROM PREGLED WHERE id=?");
            dajLjekaraUpit = conn.prepareStatement("SELECT * FROM LJEKAR WHERE doctor_id=?");
            dajPregledeKojeJeLjekarObavioUpit = conn.prepareStatement("SELECT * FROM PREGLED WHERE doctor_id=?");
            dajPregledeKojeJePacijentObavioUpit = conn.prepareStatement("SELECT * FROM PREGLED WHERE patient_id=? AND doctor_id<>-1 AND doctor_id<>-2");
            dajIdPacijentaUpit = conn.prepareStatement("SELECT patient_id FROM PACIJENT WHERE username=?");
            dajIdLjekaraKojiMoguObavitiPregledUpit = conn.prepareStatement("SELECT doctor_id FROM LJEKAR_USLUGE WHERE med_service_id=?");
            dajPregledeKojeJePacijentZakazaoUpit = conn.prepareStatement("SELECT * FROM PREGLED WHERE patient_id=? AND archived=0 AND doctor_id<>-2");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static OrdinacijaDAO getInstanca() {
        if (instanca == null) instanca = new OrdinacijaDAO();
        return instanca;
    }

    public static void removeInstance() {
        if (instanca == null) return;
        instanca.close();
        instanca = null;
    }

    private void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void regenerisiBazu() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("baza.db.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if (sqlUpit.length() > 1 && sqlUpit.charAt(sqlUpit.length() - 1) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            ulaz.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Ljekar dajLjekaraIzResultSeta(ResultSet set) throws SQLException {

        var surname = set.getString(4);
        var name = set.getString(3);
        var username = set.getString(1);
        var password = set.getString(2);
        var dateOfBirth = set.getString(5).split("-");
        var profileType = set.getString(6);
        var sex = set.getString(7);
        var fieldOfExpertise = set.getString(8);
        var doctorId = set.getInt(9);

        return new Ljekar(doctorId, surname, name, LocalDate.of(Integer.parseInt(dateOfBirth[0]), Integer.parseInt(dateOfBirth[1]), Integer.parseInt(dateOfBirth[2])), username, password, profileType, sex, fieldOfExpertise);
    }

    private Pacijent dajPacijentaIzResultSeta(ResultSet set) throws SQLException {

        var surname = set.getString(4);
        var name = set.getString(3);
        var username = set.getString(1);
        var password = set.getString(2);
        var dateOfBirth = set.getString(5).split("-");
        var profileType = set.getString(6);
        var sex = set.getString(7);
        var patientCardNumber = set.getInt(8);
        var patientId = set.getInt(9);

        return new Pacijent(patientId, surname, name, LocalDate.of(Integer.parseInt(dateOfBirth[0]), Integer.parseInt(dateOfBirth[1]), Integer.parseInt(dateOfBirth[2])), username, password, profileType, sex, patientCardNumber);
    }
}
