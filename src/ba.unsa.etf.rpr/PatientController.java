package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class PatientController {

    public Button signOutBtn = new Button();
    public ListView<String> servicesView = new ListView();
    public TableView<Examination> examinationsTable;
    public TableColumn typeOfExaminationCol;
    public TableColumn dateAndTimeOfExaminationCol;

    private ArrayList<String> services;
    private Patient patient;
    private List<Integer> doctorId;
    private ArrayList<Examination> appointments = new ArrayList<>();
    private DoctorsOfficeDAO dao;


    public PatientController(ArrayList<String> services, ArrayList<LocalDateTime> dajSveZakazanePreglede, Patient patient, List<Integer> doctorId) {
        this.services = services;
        this.patient = patient;
        this.doctorId = doctorId;
        dao = DoctorsOfficeDAO.getInstanca();
    }
    @FXML
    public void initialize(){
        servicesView.setItems(FXCollections.observableList(services));
        appointments = dao.getAppointmentsPatitentScheduled(patient.getId());
        examinationsTable.setItems(FXCollections.observableList(appointments));
        typeOfExaminationCol.setCellValueFactory(new PropertyValueFactory<>("typeOfExamination"));
        dateAndTimeOfExaminationCol.setCellValueFactory(new PropertyValueFactory<>("dateAndTimeOfAppointment"));
    }

    public void setServicesView(ObservableList<String> servicesView) {
        this.servicesView.setItems(servicesView);
    }

    public void examinationClick(ActionEvent actionEvent) throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/appointment_form.fxml"), bundle);
        ExaminationController controller = new ExaminationController(services, patient, doctorId);
        loader.setController(controller);
        Parent root = null;
        root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Zakazivanje termina");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(true);
        stage.show();

        stage.setOnHiding(event -> {
            Examination p = controller.getExamination();
            if(p != null){
                appointments.add(p);
                examinationsTable.setItems(FXCollections.observableArrayList(appointments));
            }
        });

    }

    public void cancelClick(ActionEvent actionEvent){
        if(examinationsTable.getSelectionModel().getSelectedItem() == null) return;

        var examination = examinationsTable.getSelectionModel().getSelectedItem();
        Duration duration = Duration.between(examination.getDateAndTimeOfReservation(), LocalDateTime.now());
        var stringSplit = duration.toString().split("PT|H");

        if(stringSplit[1].length()<3 && Integer.parseInt(stringSplit[1]) > 23){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška");
            alert.setHeaderText("Neispravni podaci");
            alert.setContentText("Nemoguće je otkazati examination koji je zakazan prije više od 24 sata!");
            alert.setResizable(true);
            alert.show();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potvrda brisanja");
        alert.setHeaderText("Brisanje pregleda");
        alert.setContentText("Da li ste sigurni da želite obrisati pregled?");
        alert.setResizable(true);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            dao.cancelAppointment(examination.getId());
            var e = examinationsTable.getSelectionModel().getSelectedItem();
            appointments.remove(e);
            examinationsTable.setItems(FXCollections.observableArrayList(appointments));
        }
    }

    public void historyClick(ActionEvent actionEvent) throws IOException{
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/appointments_history.fxml"), bundle);
        HistoryOfExaminationsController controller = new HistoryOfExaminationsController(dao.getAppointmentsPatientDid(patient.getUsername()), patient);
        loader.setController(controller);
        Parent root = null;
        root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Historija pregleda");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(true);
        stage.show();
    }

    public void signOutClick(ActionEvent actionEvent){
        Stage stage = (Stage) signOutBtn.getScene().getWindow();
        stage.close();
    }
}
