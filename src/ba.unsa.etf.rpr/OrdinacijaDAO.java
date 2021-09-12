package ba.unsa.etf.rpr;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
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
        var sex = set.getString(7);
        var fieldOfExpertise = set.getString(8);
        var doctorId = set.getInt(9);

        if(sex.equals("M"))
            return new Ljekar(doctorId, surname, name, LocalDate.of(Integer.parseInt(dateOfBirth[0]), Integer.parseInt(dateOfBirth[1]), Integer.parseInt(dateOfBirth[2])), username, password, VrstaKorisnickogRacuna.LJEKAR, Spol.MUSKI, fieldOfExpertise);
        else
            return new Ljekar(doctorId, surname, name, LocalDate.of(Integer.parseInt(dateOfBirth[0]), Integer.parseInt(dateOfBirth[1]), Integer.parseInt(dateOfBirth[2])), username, password, VrstaKorisnickogRacuna.LJEKAR, Spol.ZENSKI, fieldOfExpertise);

    }

    private Pacijent dajPacijentaIzResultSeta(ResultSet set) throws SQLException {

        var surname = set.getString(4);
        var name = set.getString(3);
        var username = set.getString(1);
        var password = set.getString(2);
        var dateOfBirth = set.getString(5).split("-");
        var sex = set.getString(7);
        var patientCardNumber = set.getInt(8);
        var patientId = set.getInt(9);

        if(sex.equals("M"))
            return new Pacijent(patientId, surname, name, LocalDate.of(Integer.parseInt(dateOfBirth[0]), Integer.parseInt(dateOfBirth[1]), Integer.parseInt(dateOfBirth[2])), username, password, VrstaKorisnickogRacuna.PACIJENT, Spol.MUSKI, patientCardNumber);
        else
            return new Pacijent(patientId, surname, name, LocalDate.of(Integer.parseInt(dateOfBirth[0]), Integer.parseInt(dateOfBirth[1]), Integer.parseInt(dateOfBirth[2])), username, password, VrstaKorisnickogRacuna.PACIJENT, Spol.ZENSKI, patientCardNumber);
    }

    private Pregled dajPregledIzResultSeta(ResultSet rs, Ljekar doctor, Pacijent patient) throws SQLException {

        var id = rs.getInt(1);
        var typeOfExamination = rs.getString(4);
        var therapy = rs.getString(5);
        var dateAndTime1 = rs.getString(6).split("-|T|:");
        var dateAndTime2 = rs.getString(7).split("-|T|:");
        var successful = rs.getBoolean(8);
        var diagnosis = rs.getString(9);
        var archived = rs.getBoolean(10);
        var dateAndTimeOfAppoitment = LocalDateTime.of(LocalDate.of(Integer.parseInt(dateAndTime1[0]), Integer.parseInt(dateAndTime1[1]), Integer.parseInt(dateAndTime1[2])), LocalTime.of(Integer.parseInt(dateAndTime1[3]), Integer.parseInt(dateAndTime1[4])));
        var dateAndTimeOfReservation = LocalDateTime.of(LocalDate.of(Integer.parseInt(dateAndTime2[0]), Integer.parseInt(dateAndTime2[1]), Integer.parseInt(dateAndTime2[2])), LocalTime.of(Integer.parseInt(dateAndTime2[3]), Integer.parseInt(dateAndTime2[4])));

        return new Pregled(id, patient, doctor, typeOfExamination, diagnosis, therapy, dateAndTimeOfAppoitment, dateAndTimeOfReservation, successful, archived);

    }

    public void vratiBazuNaDefault() throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("DELETE FROM LJEKAR");
        stmt.executeUpdate("DELETE FROM PACIJENT");
        stmt.executeUpdate("DELETE FROM PREGLED");
        stmt.executeUpdate("DELETE FROM lJEKAR_USLUGE");
        stmt.executeUpdate("DELETE FROM USLUGE");

        // Regeneriši bazu neće ponovo kreirati tabele jer u .sql datoteci stoji
        // CREATE TABLE IF NOT EXISTS
        // Ali će ponovo napuniti default podacima
        regenerisiBazu();
    }

    public Connection getConn() {
        return conn;
    }

    public int provjeriPrijavu(String username, String password) {
        try {
            ljekarPrijavaUpit.setString(1, username);
            ljekarPrijavaUpit.setString(2, password);
            var rs = ljekarPrijavaUpit.executeQuery();
            if (!rs.next()) {
                pacijentPrijavaUpit.setString(1, username);
                pacijentPrijavaUpit.setString(2, password);
                rs = pacijentPrijavaUpit.executeQuery();
                if (!rs.next()) return -1;
                else return 2;
            } else return 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void dodajPacijenta(Pacijent pacijent) {
        try {
            pacijentRegistracijaUpit.setString(1, pacijent.getUsername());
            pacijentRegistracijaUpit.setString(2, pacijent.getPassword());
            pacijentRegistracijaUpit.setString(3, pacijent.getIme());
            pacijentRegistracijaUpit.setString(4, pacijent.getPrezime());
            pacijentRegistracijaUpit.setString(5, pacijent.getDatumRodjenja().toString());
            pacijentRegistracijaUpit.setString(6, pacijent.getVrsta());
            pacijentRegistracijaUpit.setString(7, pacijent.getSpol());
            pacijentRegistracijaUpit.setInt(8, pacijent.getBrojKartona());
            var id = dajSljedeciIdZaPacijentaUpit.executeQuery().getInt(1);
            pacijentRegistracijaUpit.setInt(9, id);
            pacijentRegistracijaUpit.executeUpdate();
            pacijent.setId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dodajLjekara(Ljekar ljekar) {
        try {
            ljekarRegistracijaUpit.setString(1, ljekar.getUsername());
            ljekarRegistracijaUpit.setString(2, ljekar.getPassword());
            ljekarRegistracijaUpit.setString(3, ljekar.getIme());
            ljekarRegistracijaUpit.setString(4, ljekar.getPrezime());
            ljekarRegistracijaUpit.setString(4, ljekar.getPrezime());
            ljekarRegistracijaUpit.setString(5, ljekar.getDatumRodjenja().toString());
            ljekarRegistracijaUpit.setString(6, ljekar.getVrsta());
            ljekarRegistracijaUpit.setString(7, ljekar.getSpol());
            ljekarRegistracijaUpit.setString(8, ljekar.getSpecijalizacija());
            var id = dajSljedeciIdZaLjekaraUpit.executeQuery().getInt(1);
            ljekarRegistracijaUpit.setInt(9, id);
            ljekarRegistracijaUpit.executeUpdate();
            ljekar.setId(id);

            dajIdZaUsluguUpit.setString(1, ljekar.getSpecijalizacija());
            var rs = dajIdZaUsluguUpit.executeQuery();
            if (!rs.next()) {
                id = dajSljedeciIdZaUsluguUpit.executeQuery().getInt(1);
                dodajUsluguUpit.setInt(1, id);
                dodajUsluguUpit.setString(2, ljekar.getSpecijalizacija());
                dodajUsluguUpit.executeUpdate();
            } else id = rs.getInt(1);
            poveziLjekaraSaUslugomUpit.setInt(1, id);
            poveziLjekaraSaUslugomUpit.setInt(2, ljekar.getId());
            poveziLjekaraSaUslugomUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Usluga> dajSveUsluge() {
        try {
            var rs = dajSveUslugeUpit.executeQuery();

            ArrayList<Usluga> l = new ArrayList<>();

            while (rs.next()) {
                Usluga usluga = new Usluga(rs.getInt(1), rs.getString(2));
                l.add(usluga);
            }

            return l;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<LocalDateTime> dajSveZakazanePreglede() {

        try {
            var rs = dajZakazanePregledeUpit.executeQuery();

            ArrayList<LocalDateTime> zakazaniPregledi = new ArrayList<>();
            while (rs.next()) {
                var stringSplit = rs.getString(1).split("-|T|:");
                zakazaniPregledi.add(LocalDateTime.of(LocalDate.of(Integer.parseInt(stringSplit[0]), Integer.parseInt(stringSplit[1]), Integer.parseInt(stringSplit[2])), LocalTime.of(Integer.parseInt(stringSplit[3]), Integer.parseInt(stringSplit[4]))));
            }

            return zakazaniPregledi;


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Pacijent dajTrenutnoPrijavljenogPacijenta(String username, String password) {

        try {
            dajTrenutnogPrijavljenogPacijentaUpit.setString(1, username);
            dajTrenutnogPrijavljenogPacijentaUpit.setString(2, password);
            var rs = dajTrenutnogPrijavljenogPacijentaUpit.executeQuery();
            return dajPacijentaIzResultSeta(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Integer> dajSveLjekare() {

        try {
            var rs = dajSveLjekareUpit.executeQuery();

            List<Integer> ljekari = new ArrayList<>();

            while (rs.next())
                ljekari.add(rs.getInt(1));

            return ljekari;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<String> dajUslugeZaLjekara(String username) {

        try {
            dajIdLjekaraUpit.setString(1, username);
            var id = dajIdLjekaraUpit.executeQuery().getInt(1);
            dajSveUslugeZaLjekaraUpit.setInt(1, id);
            var rs = dajSveUslugeZaLjekaraUpit.executeQuery();

            ArrayList<String> usluge = new ArrayList<>();

            while (rs.next())
                usluge.add(rs.getString(1));

            return usluge;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Pregled> dajSvePregledeKojeLjekarMozeObaviti(String username) {

        try {
            dajIdLjekaraUpit.setString(1, username);
            var id = dajIdLjekaraUpit.executeQuery().getInt(1);

            dajPregledeKojeLjekarMozeObavitiUpit.setInt(1, id);
            var rs = dajPregledeKojeLjekarMozeObavitiUpit.executeQuery();

            ArrayList<Pregled> pregledi = new ArrayList<>();

            while (rs.next()) {

                id = rs.getInt(2);
                dajPacijentaUpit.setInt(1,id);
                var set = dajPacijentaUpit.executeQuery();

                Pacijent patient = dajPacijentaIzResultSeta(set);

                Pregled appointment = dajPregledIzResultSeta(rs, null, patient);
                pregledi.add(appointment);

            }
            return pregledi;

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Pregled> dajSvePregledeKojeJeLjekarObavio(String username) {
        try {
            dajIdLjekaraUpit.setString(1, username);
            var set = dajIdLjekaraUpit.executeQuery();

            dajPregledeKojeJeLjekarObavioUpit.setInt(1, set.getInt(1));
            var rs = dajPregledeKojeJeLjekarObavioUpit.executeQuery();

            dajLjekaraUpit.setInt(1, set.getInt(1));
            set = dajLjekaraUpit.executeQuery();
            Ljekar d = dajLjekaraIzResultSeta(set);

            ArrayList<Pregled> appointments = new ArrayList<>();

            while (rs.next()){
                dajPacijentaUpit.setInt(1, rs.getInt(2));
                set = dajPacijentaUpit.executeQuery();
                Pacijent p = dajPacijentaIzResultSeta(set);
                appointments.add(dajPregledIzResultSeta(rs, d, p));
            }
            return appointments;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Ljekar dajTrenutnoPrijavljenogLjekara(String username) {
        try {
            dajTrenutnoPrijavljenogLjekaraUpit.setString(1, username);

            var rs = dajTrenutnoPrijavljenogLjekaraUpit.executeQuery();

            var splitString = rs.getString(5).split("-");
            int godina = Integer.parseInt(splitString[0]);
            int mjesec = Integer.parseInt(splitString[1]);
            int dan = Integer.parseInt(splitString[2]);

            if(rs.getString(7).equals("M"))
                return new Ljekar(rs.getInt(9), rs.getString(4), rs.getString(5), LocalDate.of(godina, mjesec, dan), rs.getString(1), rs.getString(2), VrstaKorisnickogRacuna.LJEKAR, Spol.MUSKI, rs.getString(8));
            else
                return new Ljekar(rs.getInt(9), rs.getString(4), rs.getString(5), LocalDate.of(godina, mjesec, dan), rs.getString(1), rs.getString(2), VrstaKorisnickogRacuna.LJEKAR, Spol.ZENSKI, rs.getString(8));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void dodajUsluguZaLjekara(int idLjekara, String u) {
        try {
            dajIdUslugeUpit.setString(1, u);
            var idUsluge = dajIdUslugeUpit.executeQuery().getInt(1);
            dodajUsluguZaLjekaraUpit.setInt(1, idUsluge);
            dodajUsluguZaLjekaraUpit.setInt(2, idLjekara);
            dodajUsluguZaLjekaraUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void obrisiUsluguZaLjekara(int idLjekara, String u) {
        try {
            dajIdUslugeUpit.setString(1, u);
            var idUsluge = dajIdUslugeUpit.executeQuery().getInt(1);

            izbrisiUsluguZaLjekaraUpit.setInt(1, idUsluge);
            izbrisiUsluguZaLjekaraUpit.setInt(2, idLjekara);
            izbrisiUsluguZaLjekaraUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dodajPregled(Pregled pregled) {
        try {
            var id = dajSljedeciIdZaPregledUpit.executeQuery().getInt(1);

            zakaziTerminUpit.setInt(1, id);
            zakaziTerminUpit.setInt(2, pregled.getPacijent().getId());
            zakaziTerminUpit.setInt(3, -1);
            zakaziTerminUpit.setString(4, pregled.getVrstaPregleda());
            zakaziTerminUpit.setString(5,null);
            zakaziTerminUpit.setString(6, pregled.getDatumIVrijemePregleda().toString());
            zakaziTerminUpit.setString(7, pregled.getVrijemeZakazivanjaTermina().toString());
            zakaziTerminUpit.setBoolean(8, false);
            zakaziTerminUpit.setString(9, null);
            zakaziTerminUpit.setBoolean(10, false);
            zakaziTerminUpit.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void obrisiPregled(int id) {

        try {
            obrisiPregledUpit.setInt(1, id);
            obrisiPregledUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dodajTerapiju(int id, int doctorId, String terapija) {

        try {
            dodajTerapijuUpit.setString(1, terapija);
            dodajTerapijuUpit.setInt(2, doctorId);
            dodajTerapijuUpit.setInt(3, id);
            dodajTerapijuUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dodajDijagnozu(int id, int doctorId, String dijagnoza) {

        try {
            dodajDijagnozuUpit.setString(1, dijagnoza);
            dodajDijagnozuUpit.setInt(2, doctorId);
            dodajDijagnozuUpit.setInt(3, id);
            dodajDijagnozuUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Pregled dajPregled(int id) {
        // neki belaj ima ovdje s rs-om
        try {
            dajPregledUpit.setInt(1, id);

            var rs = dajPregledUpit.executeQuery();

            if(rs.next()) {

                id = rs.getInt(1);

                dajPacijentaUpit.setInt(1, rs.getInt(2));

                var set = dajPacijentaUpit.executeQuery();

                Pacijent patient = dajPacijentaIzResultSeta(set);

                dajLjekaraUpit.setInt(1, rs.getInt(3));

                set = dajLjekaraUpit.executeQuery();

                Ljekar doctor = dajLjekaraIzResultSeta(set);

                return dajPregledIzResultSeta(rs, doctor, patient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Pregled> dajSvePregledeKojeJePacijentObavio(String username) {

        try {
            dajIdPacijentaUpit.setString(1, username);
            var rs =dajIdPacijentaUpit.executeQuery();

            dajPacijentaUpit.setInt(1, rs.getInt(1));
            rs = dajPacijentaUpit.executeQuery();

            Pacijent p = dajPacijentaIzResultSeta(rs);

            dajPregledeKojeJePacijentObavioUpit.setInt(1, p.getId());
            rs = dajPregledeKojeJePacijentObavioUpit.executeQuery();

            ArrayList<Pregled> appointments = new ArrayList<>();

            while (rs.next()){
                dajLjekaraUpit.setInt(1, rs.getInt(3));
                var set = dajLjekaraUpit.executeQuery();
                Ljekar d = dajLjekaraIzResultSeta(set);
                appointments.add(dajPregledIzResultSeta(rs, d, p));
            }
            return appointments;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean provjeriPregled(String vrstaPregleda, String datumPregleda) {

        try {
            dajIdUslugeUpit.setString(1, vrstaPregleda);
            var idUsluge = dajIdUslugeUpit.executeQuery().getInt(1);

            dajIdLjekaraKojiMoguObavitiPregledUpit.setInt(1, idUsluge);
            var rs = dajIdLjekaraKojiMoguObavitiPregledUpit.executeQuery();

            while(rs.next()){
                provjeriTerminUpit.setInt(1, rs.getInt(1));
                provjeriTerminUpit.setString(2, datumPregleda);
                var set = provjeriTerminUpit.executeQuery();
                if(!set.next()) return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Pregled> dajPregledeKojeJePacijentZakazao(int id) {

        try {
            dajPregledeKojeJePacijentZakazaoUpit.setInt(1,id);

            var rs = dajPregledeKojeJePacijentZakazaoUpit.executeQuery();
            ArrayList<Pregled> appointments = new ArrayList<>();

            while(rs.next()){
                dajPacijentaUpit.setInt(1, rs.getInt(2));
                var set = dajPacijentaUpit.executeQuery();
                Pacijent patient = dajPacijentaIzResultSeta(set);
                Pregled appointment = dajPregledIzResultSeta(rs, null, patient);
                appointments.add(appointment);
            }
            return appointments;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void otkaziPregled(int id) {
        try {
            otkaziTerminUpit.setInt(1, id);
            otkaziTerminUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> dajNaziveUsluga() {

        try {
            var rs = dajSveUslugeUpit.executeQuery();

            ArrayList<String> l = new ArrayList<>();

            while(rs.next()){
                l.add(rs.getString(2));
            }
            return l;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
