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

public class DoctorsOfficeDAO {private static DoctorsOfficeDAO instanca;
    private Connection conn;
    private PreparedStatement patientSignInQuery, doctorSignInQuery, patientRegistrationQuery, doctorRegistrationQuery, scheduleAnAppointmentQuery, cancelTheAppointmentQuery,
            addAServiceQuery, getNextPatientIdQuery, getNextDoctorIdQuery, getNextExaminationIdQuery, getNextServiceIdQuery, addAServiceForADoctorQuery, addTherapyQuery, linkDoctorAndServiceQuery,
            getServiceIdQuery, checkTheAppointmentQuery, getDoctorIdQuery, getAllDoctorsQuery, getAllServicesQuery,  getAllAppointmentsQuery, getCurrentPatient, getDoctorsServicesQuery,
            getAppointmentsDoctorCanDoQuery, getAppointmentsThePatientScheduled, getIdsOfDoctorsThatCanDoTheExaminationQuery, getPatienIdQuery, getAppointmentsThatPatientDidQuery, getAppointmentsDoctorDidQuery,
            getDoctorQuery, getAppointmentQuery, addDiagnosisQuery, getPatientQuery, deleteAppointmentQuery, deleteServiceForADoctorQuery, getCurrentDoctorQuery;

    private DoctorsOfficeDAO() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:baza.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            patientSignInQuery = conn.prepareStatement("SELECT * FROM PACIJENT WHERE username=? AND password=?");
            doctorSignInQuery = conn.prepareStatement("SELECT * FROM LJEKAR WHERE username=? AND password=?");
            patientRegistrationQuery = conn.prepareStatement("INSERT INTO PACIJENT VALUES(?,?,?,?,?,?,?,?,?)");
            doctorRegistrationQuery = conn.prepareStatement("INSERT INTO LJEKAR VALUES(?,?,?,?,?,?,?,?,?)");
            scheduleAnAppointmentQuery = conn.prepareStatement("INSERT INTO PREGLED VALUES(?,?,?,?,?,?,?,?,?,?)");
            cancelTheAppointmentQuery = conn.prepareStatement("UPDATE PREGLED SET successful=0, doctor_id=-2 WHERE id=?");
            addAServiceQuery = conn.prepareStatement("INSERT INTO USLUGE VALUES(?,?)");
            getNextDoctorIdQuery = conn.prepareStatement("SELECT MAX(doctor_id)+1 FROM LJEKAR");
            getNextPatientIdQuery = conn.prepareStatement("SELECT MAX(patient_id)+1 FROM PACIJENT");
            getNextExaminationIdQuery = conn.prepareStatement("SELECT MAX(id)+1 FROM PREGLED");
            getNextServiceIdQuery = conn.prepareStatement("SELECT  MAX(id)+1 FROM USLUGE");
            addAServiceForADoctorQuery = conn.prepareStatement("INSERT INTO LJEKAR_USLUGE VALUES(?,?)");
            addTherapyQuery = conn.prepareStatement("UPDATE PREGLED SET therapy=?, doctor_id=? WHERE id=?");
            linkDoctorAndServiceQuery = conn.prepareStatement("INSERT INTO LJEKAR_USLUGE VALUES(?,?)");
            getServiceIdQuery = conn.prepareStatement("SELECT id FROM USLUGE WHERE name=?");
            getAllServicesQuery = conn.prepareStatement("SELECT * FROM USLUGE");
            getAllAppointmentsQuery = conn.prepareStatement("SELECT date_and_time_of_appointment, type_of_examination, patient_id, doctor_id FROM PREGLED WHERE successful=1"); //DODATI PROVJERU VREMENA
            getCurrentPatient = conn.prepareStatement("SELECT * FROM PACIJENT WHERE username=? AND password=?");
            getAllDoctorsQuery = conn.prepareStatement("SELECT doctor_id FROM LJEKAR");
            checkTheAppointmentQuery = conn.prepareStatement("SELECT p.id FROM PREGLED p WHERE p.doctor_id=? AND p.date_and_time_of_appointment=?");
            getDoctorsServicesQuery = conn.prepareStatement("SELECT u.name FROM USLUGE u, LJEKAR lj, lJEKAR_USLUGE lju WHERE u.id=lju.med_service_id AND lju.doctor_id=lj.doctor_id AND lj.doctor_id=?");
            getDoctorIdQuery = conn.prepareStatement("SELECT doctor_id FROM LJEKAR where username=?");
            getCurrentDoctorQuery = conn.prepareStatement("SELECT * FROM LJEKAR WHERE username=?");
            deleteServiceForADoctorQuery = conn.prepareStatement("DELETE FROM LJEKAR_USLUGE WHERE med_service_id=? AND doctor_id=?");
            getAppointmentsDoctorCanDoQuery = conn.prepareStatement("SELECT * FROM PREGLED p, LJEKAR_USLUGE lju, USLUGE u WHERE p.archived=0 AND p.doctor_id<>-2 AND p.type_of_examination=u.name AND u.id=lju.med_service_id AND SUBSTR(date_and_time_of_appointment,1,10)=CURRENT_DATE AND lju.doctor_id=?");
            getPatientQuery = conn.prepareStatement("SELECT * FROM PACIJENT WHERE patient_id=?");
            deleteAppointmentQuery = conn.prepareStatement("DELETE FROM PREGLED WHERE id=?");
            addDiagnosisQuery = conn.prepareStatement("UPDATE PREGLED SET diagnosis=?, doctor_id=?, archived=1 WHERE id=?");
            getAppointmentQuery = conn.prepareStatement("SELECT * FROM PREGLED WHERE id=?");
            getDoctorQuery = conn.prepareStatement("SELECT * FROM LJEKAR WHERE doctor_id=?");
            getAppointmentsDoctorDidQuery = conn.prepareStatement("SELECT * FROM PREGLED WHERE doctor_id=?");
            getAppointmentsThatPatientDidQuery = conn.prepareStatement("SELECT * FROM PREGLED WHERE patient_id=? AND doctor_id<>-1 AND doctor_id<>-2");
            getPatienIdQuery = conn.prepareStatement("SELECT patient_id FROM PACIJENT WHERE username=?");
            getIdsOfDoctorsThatCanDoTheExaminationQuery = conn.prepareStatement("SELECT doctor_id FROM LJEKAR_USLUGE WHERE med_service_id=?");
            getAppointmentsThePatientScheduled = conn.prepareStatement("SELECT * FROM PREGLED WHERE patient_id=? AND archived=0 AND doctor_id<>-2");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DoctorsOfficeDAO getInstanca() {
        if (instanca == null) instanca = new DoctorsOfficeDAO();
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

    private Doctor getADoctorFromResultSet(ResultSet set) throws SQLException {

        var surname = set.getString(4);
        var name = set.getString(3);
        var username = set.getString(1);
        var password = set.getString(2);
        var dateOfBirth = set.getString(5).split("-");
        var sex = set.getString(7);
        var fieldOfExpertise = set.getString(8);
        var doctorId = set.getInt(9);

        if(sex.equals("M"))
            return new Doctor(doctorId, surname, name, LocalDate.of(Integer.parseInt(dateOfBirth[0]), Integer.parseInt(dateOfBirth[1]), Integer.parseInt(dateOfBirth[2])), username, password, ProfileType.LJEKAR, SexOfAUser.MUSKI, fieldOfExpertise);
        else
            return new Doctor(doctorId, surname, name, LocalDate.of(Integer.parseInt(dateOfBirth[0]), Integer.parseInt(dateOfBirth[1]), Integer.parseInt(dateOfBirth[2])), username, password, ProfileType.LJEKAR, SexOfAUser.ZENSKI, fieldOfExpertise);

    }

    private Patient getPatientFromResultSet(ResultSet set) throws SQLException {

        var surname = set.getString(4);
        var name = set.getString(3);
        var username = set.getString(1);
        var password = set.getString(2);
        var dateOfBirth = set.getString(5).split("-");
        var sex = set.getString(7);
        var patientCardNumber = set.getInt(8);
        var patientId = set.getInt(9);

        if(sex.equals("M"))
            return new Patient(patientId, surname, name, LocalDate.of(Integer.parseInt(dateOfBirth[0]), Integer.parseInt(dateOfBirth[1]), Integer.parseInt(dateOfBirth[2])), username, password, ProfileType.PACIJENT, SexOfAUser.MUSKI, patientCardNumber);
        else
            return new Patient(patientId, surname, name, LocalDate.of(Integer.parseInt(dateOfBirth[0]), Integer.parseInt(dateOfBirth[1]), Integer.parseInt(dateOfBirth[2])), username, password, ProfileType.PACIJENT, SexOfAUser.ZENSKI, patientCardNumber);
    }

    private Examination getAppointmentFromResultSet(ResultSet rs, Doctor doctor, Patient patient) throws SQLException {

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

        return new Examination(id, patient, doctor, typeOfExamination, diagnosis, therapy, dateAndTimeOfAppoitment, dateAndTimeOfReservation, successful, archived);

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

    public int checkSignIn(String username, String password) {
        try {
            doctorSignInQuery.setString(1, username);
            doctorSignInQuery.setString(2, password);
            var rs = doctorSignInQuery.executeQuery();
            if (!rs.next()) {
                patientSignInQuery.setString(1, username);
                patientSignInQuery.setString(2, password);
                rs = patientSignInQuery.executeQuery();
                if (!rs.next()) return -1;
                else return 2;
            } else return 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void addAPatient(Patient patient) {
        try {
            patientRegistrationQuery.setString(1, patient.getUsername());
            patientRegistrationQuery.setString(2, patient.getPassword());
            patientRegistrationQuery.setString(3, patient.getName());
            patientRegistrationQuery.setString(4, patient.getSurname());
            patientRegistrationQuery.setString(5, patient.getDateOfBirth().toString());
            patientRegistrationQuery.setString(6, patient.getProfileType());
            patientRegistrationQuery.setString(7, patient.getSpol());
            patientRegistrationQuery.setInt(8, patient.getPatientCardNumber());
            var id = getNextPatientIdQuery.executeQuery().getInt(1);
            patientRegistrationQuery.setInt(9, id);
            patientRegistrationQuery.executeUpdate();
            patient.setId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addADoctor(Doctor doctor) {
        try {
            doctorRegistrationQuery.setString(1, doctor.getUsername());
            doctorRegistrationQuery.setString(2, doctor.getPassword());
            doctorRegistrationQuery.setString(3, doctor.getName());
            doctorRegistrationQuery.setString(4, doctor.getSurname());
            doctorRegistrationQuery.setString(4, doctor.getSurname());
            doctorRegistrationQuery.setString(5, doctor.getDateOfBirth().toString());
            doctorRegistrationQuery.setString(6, doctor.getProfileType());
            doctorRegistrationQuery.setString(7, doctor.getSpol());
            doctorRegistrationQuery.setString(8, doctor.getSpecialization());
            var id = getNextDoctorIdQuery.executeQuery().getInt(1);
            doctorRegistrationQuery.setInt(9, id);
            doctorRegistrationQuery.executeUpdate();
            doctor.setId(id);

            getServiceIdQuery.setString(1, doctor.getSpecialization());
            var rs = getServiceIdQuery.executeQuery();
            if (!rs.next()) {
                id = getNextServiceIdQuery.executeQuery().getInt(1);
                addAServiceQuery.setInt(1, id);
                addAServiceQuery.setString(2, doctor.getSpecialization());
                addAServiceQuery.executeUpdate();
            } else id = rs.getInt(1);
            linkDoctorAndServiceQuery.setInt(1, id);
            linkDoctorAndServiceQuery.setInt(2, doctor.getId());
            linkDoctorAndServiceQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Service> getAllServices() {
        try {
            var rs = getAllServicesQuery.executeQuery();

            ArrayList<Service> l = new ArrayList<>();

            while (rs.next()) {
                Service service = new Service(rs.getInt(1), rs.getString(2));
                l.add(service);
            }

            return l;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<LocalDateTime> getAllAppointments() {

        try {
            var rs = getAllAppointmentsQuery.executeQuery();

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

    public Patient getCurrentPatient(String username, String password) {

        try {
            getCurrentPatient.setString(1, username);
            getCurrentPatient.setString(2, password);
            var rs = getCurrentPatient.executeQuery();
            return getPatientFromResultSet(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Integer> getAllDoctors() {

        try {
            var rs = getAllDoctorsQuery.executeQuery();

            List<Integer> doctors = new ArrayList<>();

            while (rs.next())
                doctors.add(rs.getInt(1));

            return doctors;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<String> getDoctorsServices(String username) {

        try {
            getDoctorIdQuery.setString(1, username);
            var id = getDoctorIdQuery.executeQuery().getInt(1);
            getDoctorsServicesQuery.setInt(1, id);
            var rs = getDoctorsServicesQuery.executeQuery();

            ArrayList<String> services = new ArrayList<>();

            while (rs.next())
                services.add(rs.getString(1));

            return services;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Examination> getAllExaminationsDoctorCanDo(String username) {

        try {
            getDoctorIdQuery.setString(1, username);
            var id = getDoctorIdQuery.executeQuery().getInt(1);

            getAppointmentsDoctorCanDoQuery.setInt(1, id);
            var rs = getAppointmentsDoctorCanDoQuery.executeQuery();

            ArrayList<Examination> examinations = new ArrayList<>();

            while (rs.next()) {

                id = rs.getInt(2);
                getPatientQuery.setInt(1,id);
                var set = getPatientQuery.executeQuery();

                Patient patient = getPatientFromResultSet(set);

                Examination appointment = getAppointmentFromResultSet(rs, null, patient);
                examinations.add(appointment);

            }
            return examinations;

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Examination> getAllExaminationsDoctorDid(String username) {
        try {
            getDoctorIdQuery.setString(1, username);
            var set = getDoctorIdQuery.executeQuery();

            getAppointmentsDoctorDidQuery.setInt(1, set.getInt(1));
            var rs = getAppointmentsDoctorDidQuery.executeQuery();

            getDoctorQuery.setInt(1, set.getInt(1));
            set = getDoctorQuery.executeQuery();
            Doctor d = getADoctorFromResultSet(set);

            ArrayList<Examination> appointments = new ArrayList<>();

            while (rs.next()){
                getPatientQuery.setInt(1, rs.getInt(2));
                set = getPatientQuery.executeQuery();
                Patient p = getPatientFromResultSet(set);
                appointments.add(getAppointmentFromResultSet(rs, d, p));
            }
            return appointments;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Doctor getCurrentDoctor(String username) {
        try {
            getCurrentDoctorQuery.setString(1, username);

            var rs = getCurrentDoctorQuery.executeQuery();

            var splitString = rs.getString(5).split("-");
            int godina = Integer.parseInt(splitString[0]);
            int mjesec = Integer.parseInt(splitString[1]);
            int dan = Integer.parseInt(splitString[2]);

            if(rs.getString(7).equals("M"))
                return new Doctor(rs.getInt(9), rs.getString(4), rs.getString(5), LocalDate.of(godina, mjesec, dan), rs.getString(1), rs.getString(2), ProfileType.LJEKAR, SexOfAUser.MUSKI, rs.getString(8));
            else
                return new Doctor(rs.getInt(9), rs.getString(4), rs.getString(5), LocalDate.of(godina, mjesec, dan), rs.getString(1), rs.getString(2), ProfileType.LJEKAR, SexOfAUser.ZENSKI, rs.getString(8));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void addAServiceForADoctor(int idLjekara, String u) {
        try {
            getServiceIdQuery.setString(1, u);
            var idUsluge = getServiceIdQuery.executeQuery().getInt(1);
            addAServiceForADoctorQuery.setInt(1, idUsluge);
            addAServiceForADoctorQuery.setInt(2, idLjekara);
            addAServiceForADoctorQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteServiceForADoctor(int idLjekara, String u) {
        try {
            getServiceIdQuery.setString(1, u);
            var serviceId = getServiceIdQuery.executeQuery().getInt(1);

            deleteServiceForADoctorQuery.setInt(1, serviceId);
            deleteServiceForADoctorQuery.setInt(2, idLjekara);
            deleteServiceForADoctorQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addAnAppointment(Examination examination) {
        try {
            var id = getNextExaminationIdQuery.executeQuery().getInt(1);

            scheduleAnAppointmentQuery.setInt(1, id);
            scheduleAnAppointmentQuery.setInt(2, examination.getPatient().getId());
            scheduleAnAppointmentQuery.setInt(3, -1);
            scheduleAnAppointmentQuery.setString(4, examination.getTypeOfExamination());
            scheduleAnAppointmentQuery.setString(5,null);
            scheduleAnAppointmentQuery.setString(6, examination.getDateAndTimeOfAppointment().toString());
            scheduleAnAppointmentQuery.setString(7, examination.getDateAndTimeOfReservation().toString());
            scheduleAnAppointmentQuery.setBoolean(8, false);
            scheduleAnAppointmentQuery.setString(9, null);
            scheduleAnAppointmentQuery.setBoolean(10, false);
            scheduleAnAppointmentQuery.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAppoinment(int id) {

        try {
            deleteAppointmentQuery.setInt(1, id);
            deleteAppointmentQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addTherapy(int id, int doctorId, String therapy) {

        try {
            addTherapyQuery.setString(1, therapy);
            addTherapyQuery.setInt(2, doctorId);
            addTherapyQuery.setInt(3, id);
            addTherapyQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addDiagnosis(int id, int doctorId, String diagnosis) {

        try {
            addDiagnosisQuery.setString(1, diagnosis);
            addDiagnosisQuery.setInt(2, doctorId);
            addDiagnosisQuery.setInt(3, id);
            addDiagnosisQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Examination getAppointment(int id) {
        // neki belaj ima ovdje s rs-om
        try {
            getAppointmentQuery.setInt(1, id);

            var rs = getAppointmentQuery.executeQuery();

            if(rs.next()) {

                id = rs.getInt(1);

                getPatientQuery.setInt(1, rs.getInt(2));

                var set = getPatientQuery.executeQuery();

                Patient patient = getPatientFromResultSet(set);

                getDoctorQuery.setInt(1, rs.getInt(3));

                set = getDoctorQuery.executeQuery();

                Doctor doctor = getADoctorFromResultSet(set);

                return getAppointmentFromResultSet(rs, doctor, patient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Examination> getAppointmentsPatientDid(String username) {

        try {
            getPatienIdQuery.setString(1, username);
            var rs = getPatienIdQuery.executeQuery();

            getPatientQuery.setInt(1, rs.getInt(1));
            rs = getPatientQuery.executeQuery();

            Patient p = getPatientFromResultSet(rs);

            getAppointmentsThatPatientDidQuery.setInt(1, p.getId());
            rs = getAppointmentsThatPatientDidQuery.executeQuery();

            ArrayList<Examination> appointments = new ArrayList<>();

            while (rs.next()){
                getDoctorQuery.setInt(1, rs.getInt(3));
                var set = getDoctorQuery.executeQuery();
                Doctor d = getADoctorFromResultSet(set);
                appointments.add(getAppointmentFromResultSet(rs, d, p));
            }
            return appointments;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkAppointment(String typeOfExamination, String date) {

        try {
            getServiceIdQuery.setString(1, typeOfExamination);
            var idUsluge = getServiceIdQuery.executeQuery().getInt(1);

            getIdsOfDoctorsThatCanDoTheExaminationQuery.setInt(1, idUsluge);
            var rs = getIdsOfDoctorsThatCanDoTheExaminationQuery.executeQuery();

            while(rs.next()){
                checkTheAppointmentQuery.setInt(1, rs.getInt(1));
                checkTheAppointmentQuery.setString(2, date);
                var set = checkTheAppointmentQuery.executeQuery();
                if(!set.next()) return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Examination> getAppointmentsPatitentScheduled(int id) {

        try {
            getAppointmentsThePatientScheduled.setInt(1,id);

            var rs = getAppointmentsThePatientScheduled.executeQuery();
            ArrayList<Examination> appointments = new ArrayList<>();

            while(rs.next()){
                getPatientQuery.setInt(1, rs.getInt(2));
                var set = getPatientQuery.executeQuery();
                Patient patient = getPatientFromResultSet(set);
                Examination appointment = getAppointmentFromResultSet(rs, null, patient);
                appointments.add(appointment);
            }
            return appointments;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cancelAppointment(int id) {
        try {
            cancelTheAppointmentQuery.setInt(1, id);
            cancelTheAppointmentQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getNamesOfServices() {

        try {
            var rs = getAllServicesQuery.executeQuery();

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
