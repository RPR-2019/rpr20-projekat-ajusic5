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
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class PacijentController {

    public Button odjavaBtn = new Button();
    public ListView<String> uslugeView= new ListView();
    public TableView<Pregled> preglediTable;
    public TableColumn vrstaPregledaCol;
    public TableColumn datumIVrijemePregledaCol;

    private ArrayList<String> usluge;
    private Pacijent trenutnoPrijavljeniPacijent;
    private List<Integer> idLjekara;
    private ArrayList<Pregled> appointments = new ArrayList<>();
    private OrdinacijaDAO dao;


    public PacijentController(ArrayList<String> usluge, ArrayList<LocalDateTime> dajSveZakazanePreglede, Pacijent trenutnoPrijavljeniPacijent, List<Integer> idLjekara) {
        this.usluge=usluge;
        this.trenutnoPrijavljeniPacijent = trenutnoPrijavljeniPacijent;
        this.idLjekara = idLjekara;
        dao = OrdinacijaDAO.getInstanca();
    }
    @FXML
    public void initialize(){
        uslugeView.setItems(FXCollections.observableList(usluge));
        appointments = dao.dajPregledeKojeJePacijentZakazao(trenutnoPrijavljeniPacijent.getId());
        preglediTable.setItems(FXCollections.observableList(appointments));
        vrstaPregledaCol.setCellValueFactory(new PropertyValueFactory<>("vrstaPregleda"));
        datumIVrijemePregledaCol.setCellValueFactory(new PropertyValueFactory<>("datumIVrijemePregleda"));
    }

    public void setUslugeView(ObservableList<String> uslugeView) {
        this.uslugeView.setItems(uslugeView);
    }

    public void pregledClick(ActionEvent actionEvent) throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/zakazivanje_termina_forma.fxml"), bundle);
        PregledController controller = new PregledController(usluge, trenutnoPrijavljeniPacijent, idLjekara);
        loader.setController(controller);
        Parent root = null;
        root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Zakazivanje termina");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(true);
        stage.show();

        stage.setOnHiding(event -> {
            Pregled p = controller.getPregled();
            if(p != null){
                appointments.add(p);
                preglediTable.setItems(FXCollections.observableArrayList(appointments));
            }
        });

    }

    public void otkazivanjeClick(ActionEvent actionEvent){
        if(preglediTable.getSelectionModel().getSelectedItem() == null) return;

        var pregled = preglediTable.getSelectionModel().getSelectedItem();
        Duration duration = Duration.between(pregled.getVrijemeZakazivanjaTermina(), LocalDateTime.now());
        var stringSplit = duration.toString().split("PT|H");

        if(stringSplit[1].length()<3 && Integer.parseInt(stringSplit[1]) > 23){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška");
            alert.setHeaderText("Neispravni podaci");
            alert.setContentText("Nemoguće je otkazati pregled koji je zakazan prije više od 24 sata!");
            alert.setResizable(true);
            alert.show();
            return;
        }
        dao.otkaziPregled(pregled.getId());
        var p = preglediTable.getSelectionModel().getSelectedItem();
        appointments.remove(p);
        preglediTable.setItems(FXCollections.observableArrayList(appointments));
    }

    public void historijaClick(ActionEvent actionEvent) throws IOException{}

    public void odjavaClick(ActionEvent actionEvent){
        Stage stage = (Stage) odjavaBtn.getScene().getWindow();
        stage.close();
    }
}
