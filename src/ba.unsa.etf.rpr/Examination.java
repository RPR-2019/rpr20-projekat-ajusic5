package ba.unsa.etf.rpr;

import java.time.LocalDateTime;

public class Examination {

    private int id;
    private Patient patient;
    private Doctor doctor;
    private String typeOfExamination;
    private String diagnosis;
    private String therapy;
    private LocalDateTime dateAndTimeOfAppointment;
    private LocalDateTime dateAndTimeOfReservation; // treba za mogućnost otkazivanja u roku od 24h
    private boolean successful;
    private boolean archived;

    public Examination() {
    }

    public Examination(int id, Patient patient, String typeOfExamination, LocalDateTime dateAndTimeOfAppointment, LocalDateTime dateAndTimeOfReservation) {
        this.id = id;
        this.patient = patient;
        this.typeOfExamination = typeOfExamination;
        this.dateAndTimeOfAppointment = dateAndTimeOfAppointment;
        this.dateAndTimeOfReservation = dateAndTimeOfReservation;
    }

    public Examination(int id, Patient patient, Doctor doctor, String typeOfExamination, String diagnosis, String therapy, LocalDateTime dateAndTimeOfAppointment, LocalDateTime dateAndTimeOfReservation, boolean successful, boolean archived) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.typeOfExamination = typeOfExamination;
        this.diagnosis = diagnosis;
        this.therapy = therapy;
        this.dateAndTimeOfAppointment = dateAndTimeOfAppointment;
        this.dateAndTimeOfReservation = dateAndTimeOfReservation;
        this.successful = successful;
        this.archived = archived;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getTypeOfExamination() {
        return typeOfExamination;
    }

    public void setTypeOfExamination(String typeOfExamination) {
        this.typeOfExamination = typeOfExamination;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTherapy() {
        return therapy;
    }

    public void setTherapy(String therapy) {
        this.therapy = therapy;
    }

    public LocalDateTime getDateAndTimeOfAppointment() {
        return dateAndTimeOfAppointment;
    }

    public void setDateAndTimeOfAppointment(LocalDateTime dateAndTimeOfAppointment) {
        this.dateAndTimeOfAppointment = dateAndTimeOfAppointment;
    }

    public LocalDateTime getDateAndTimeOfReservation() {
        return dateAndTimeOfReservation;
    }

    public void setDateAndTimeOfReservation(LocalDateTime dateAndTimeOfReservation) {
        this.dateAndTimeOfReservation = dateAndTimeOfReservation;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    @Override
    public String toString(){
        return patient.getSurname()+" "+ patient.getName()+" "+ dateAndTimeOfAppointment.toString()+" "+ typeOfExamination;
    }
}
