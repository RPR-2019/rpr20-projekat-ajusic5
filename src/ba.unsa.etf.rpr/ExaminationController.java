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
    public ChoiceBox<String> vrstaPregledaCB = new ChoiceBox<>();
    @FXML
    public DatePicker datumPregledaDP = new DatePicker();
    @FXML
    public ChoiceBox<String> vrijemePregledaCB = new ChoiceBox<>();

    private ArrayList<String> usluge;
    private Examination examination = new Examination();
    private ArrayList<LocalDateTime> zakazaniPregledi = new ArrayList<>();
    private Patient patient = new Patient();
    private List<Integer> idLjekara = new ArrayList<>();
    private DoctorsOfficeDAO dao;

    public ExaminationController(ArrayList<String> usluge, Patient patient, List<Integer> idLjekara) {
        this.usluge = usluge;
        this.patient = patient;
        this.idLjekara = idLjekara;
    }

    public void initialize(){
        datumPregledaDP.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0 );
            }
        });

        vrstaPregledaCB.setItems(FXCollections.observableList(usluge));

        vrijemePregledaCB.getItems().addAll("08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30");

        dao = DoctorsOfficeDAO.getInstanca();

    }

    public Examination getPregled() {
        return examination;
    }

    public Examination okClick(ActionEvent actionEvent){
        if(vrstaPregledaCB.getValue() == null || vrijemePregledaCB.getValue() == null || datumPregledaDP.getValue() == null) return null;

        var splitString = vrijemePregledaCB.getSelectionModel().getSelectedItem().split(":");
        var vrstaPregleda = vrstaPregledaCB.getValue();
        var datumPregleda = datumPregledaDP.getValue().toString() + "T" + vrijemePregledaCB.getValue();

        var nijeZauzet = dao.provjeriPregled(vrstaPregleda, datumPregleda);

        if(!nijeZauzet){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška");
            alert.setHeaderText("Neispravni podaci");
            alert.setContentText("Već je zauzet ovaj termin za sve ljekare koji nude ovu uslugu!");
            alert.setResizable(true);
            alert.show();
            return null;
        }

        examination.setVrstaPregleda(vrstaPregledaCB.getValue());
        examination.setVrijemeZakazivanjaTermina(LocalDateTime.now());
        examination.setDatumIVrijemePregleda(LocalDateTime.of(datumPregledaDP.getValue(), LocalTime.of(Integer.parseInt(splitString[0]),Integer.parseInt(splitString[1]))));
        examination.setVrijemeZakazivanjaTermina(LocalDateTime.now());
        examination.setPacijent(patient);
        dao.dodajPregled(examination);
        Stage stage = (Stage) okBtn.getScene().getWindow();
        stage.close();
        return examination;
    }
}
