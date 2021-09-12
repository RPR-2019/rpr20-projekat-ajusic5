package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public void setUslugeView(ObservableList<String> uslugeView){

    }

    public void pregledClick(ActionEvent actionEvent){

    }

    public void otkazivanjeClick(ActionEvent actionEvent){}

    public void historijaClick(ActionEvent actionEvent) throws IOException{}

    public void odjavaClick(ActionEvent actionEvent){}
}
