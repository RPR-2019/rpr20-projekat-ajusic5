package ba.unsa.etf.rpr;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class LjekarController {

    public Button odjavaBtn;
    public ChoiceBox<String> uslugeCB;
    public ListView<String> uslugeView;
    public TableView<Pregled> preglediTable;
    public TableColumn<Pregled, String> prezimeCol;
    public TableColumn<Pregled, String> imeCol;
    public TableColumn datumIVrijemeCol;
    public TableColumn vrstaPregledaCol;

    private Ljekar trenutnoPrijavljeniLjekar;
    private ObservableList<String> uslugeLjekara;
    private ArrayList<Pregled> preglediKojeLjekarMozeObaviti;
    private ArrayList<Pregled> preglediKojeJeLjekarObavio;
    private ObservableList<String> sveUsluge;
    private ArrayList<String> uslugeKojeTrebaIzbrisati;
    private OrdinacijaDAO dao;
    private ObservableList<Pregled> pregledi;

    public LjekarController(ArrayList<String> uslugeLjekara, ArrayList<Pregled> preglediKojeLjekarMozeObaviti, ArrayList<Pregled> preglediKojeJeLjekarObavio, Ljekar trenutnoPrijavljeniLjekar, ArrayList<String> sveUsluge) {
        this.uslugeLjekara = FXCollections.observableArrayList(uslugeLjekara);
        this.preglediKojeLjekarMozeObaviti = preglediKojeLjekarMozeObaviti;
        this.preglediKojeJeLjekarObavio = preglediKojeJeLjekarObavio;
        this.trenutnoPrijavljeniLjekar = trenutnoPrijavljeniLjekar;
        this.sveUsluge = FXCollections.observableArrayList(sveUsluge);
        dao = OrdinacijaDAO.getInstanca();
        pregledi = FXCollections.observableArrayList(preglediKojeLjekarMozeObaviti);
    }
    @FXML
    public void initialize(){
        preglediTable.setItems(pregledi);
        prezimeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPacijent().getPrezime()));
        imeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPacijent().getIme()));
        datumIVrijemeCol.setCellValueFactory(new PropertyValueFactory<>("datumIVrijemePregleda"));
        vrstaPregledaCol.setCellValueFactory(new PropertyValueFactory<>("vrstaPregleda"));
        uslugeView.setItems(uslugeLjekara);
        uslugeCB.setItems(sveUsluge);
    }
    public Ljekar getTrenutnoPrijavljeniLjekar() {
        return trenutnoPrijavljeniLjekar;
    }


    public void odjavaClick(ActionEvent actionEvent){
        Stage stage = (Stage) odjavaBtn.getScene().getWindow();
        stage.close();
    }
    public void dodajUsluguClick(ActionEvent actionEvent){}

    public void izbrisiUsluguClick(ActionEvent actionEvent){

    }

    public void unesiDijagnozuITerapijuClick(ActionEvent actionEvent) {}

    public void obrisiPregledClick(ActionEvent actionEvent){}

    public void historijaPregledaClick(ActionEvent actionEvent) {}
}
