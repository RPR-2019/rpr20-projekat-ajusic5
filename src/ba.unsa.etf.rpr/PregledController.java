package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PregledController {
    @FXML
    public Button okBtn = new Button();
    @FXML
    public ChoiceBox<String> vrstaPregledaCB = new ChoiceBox<>();
    @FXML
    public DatePicker datumPregledaDP = new DatePicker();
    @FXML
    public ChoiceBox<String> vrijemePregledaCB = new ChoiceBox<>();

    private ArrayList<String> usluge;
    private Pregled pregled = new Pregled();
    private ArrayList<LocalDateTime> zakazaniPregledi = new ArrayList<>();
    private Pacijent pacijent = new Pacijent();
    private List<Integer> idLjekara = new ArrayList<>();
    private OrdinacijaDAO dao;

    public PregledController(ArrayList<String> usluge, Pacijent pacijent, List<Integer> idLjekara) {
        this.usluge = usluge;
        this.pacijent = pacijent;
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

        dao = OrdinacijaDAO.getInstanca();

    }

    public Pregled getPregled() {
        return pregled;
    }

    public Pregled okClick(ActionEvent actionEvent){
        return null;
    }
}
