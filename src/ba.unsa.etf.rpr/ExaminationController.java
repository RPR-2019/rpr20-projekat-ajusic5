package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ExaminationController {
    @FXML
    public Button okBtn = new Button();
    @FXML
    public ChoiceBox<String> typeOfExaminationCB = new ChoiceBox<>();
    @FXML
    public DatePicker dateDP = new DatePicker();
    @FXML
    public ChoiceBox<String> timeCB = new ChoiceBox<>();

    private ArrayList<String> services;
    private Examination examination = new Examination();
    private ArrayList<LocalDateTime> appointments = new ArrayList<>();
    private Patient patient = new Patient();
    private List<Integer> doctorId = new ArrayList<>();
    private DoctorsOfficeDAO dao;

    public ExaminationController(ArrayList<String> services, Patient patient, List<Integer> doctorId) {
        this.services = services;
        this.patient = patient;
        this.doctorId = doctorId;
    }

    public void initialize(){
        dateDP.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0 );
            }
        });

        typeOfExaminationCB.setItems(FXCollections.observableList(services));

        timeCB.getItems().addAll("08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30");

        dao = DoctorsOfficeDAO.getInstanca();

    }

    public Examination getPregled() {
        return examination;
    }

    public Examination okClick(ActionEvent actionEvent){
        if(typeOfExaminationCB.getValue() == null || timeCB.getValue() == null || dateDP.getValue() == null) return null;

        var splitString = timeCB.getSelectionModel().getSelectedItem().split(":");
        var vrstaPregleda = typeOfExaminationCB.getValue();
        var datumPregleda = dateDP.getValue().toString() + "T" + timeCB.getValue();

        var available = dao.provjeriPregled(vrstaPregleda, datumPregleda);

        if(!available){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška");
            alert.setHeaderText("Neispravni podaci");
            alert.setContentText("Već je zauzet ovaj termin za sve ljekare koji nude ovu uslugu!");
            alert.setResizable(true);
            alert.show();
            return null;
        }

        examination.setTypeOfExamination(typeOfExaminationCB.getValue());
        examination.setDateAndTimeOfReservation(LocalDateTime.now());
        examination.setDateAndTimeOfAppointment(LocalDateTime.of(dateDP.getValue(), LocalTime.of(Integer.parseInt(splitString[0]),Integer.parseInt(splitString[1]))));
        examination.setDateAndTimeOfReservation(LocalDateTime.now());
        examination.setPacijent(patient);
        dao.dodajPregled(examination);
        Stage stage = (Stage) okBtn.getScene().getWindow();
        stage.close();
        return examination;
    }
}
