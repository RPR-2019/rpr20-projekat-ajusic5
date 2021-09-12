package ba.unsa.etf.rpr;

import javafx.beans.property.SimpleStringProperty;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

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
    public void dodajUsluguClick(ActionEvent actionEvent){
        if(uslugeLjekara.contains(uslugeCB.getValue()) || uslugeCB.getValue()==null) return;
        getTrenutnoPrijavljeniLjekar().setUsluge(uslugeView.getItems());
        uslugeLjekara.add(uslugeCB.getValue());
        dao.dodajUsluguZaLjekara(getTrenutnoPrijavljeniLjekar().getId() , uslugeCB.getValue());
        pregledi = FXCollections.observableArrayList(dao.dajSvePregledeKojeLjekarMozeObaviti(trenutnoPrijavljeniLjekar.getUsername()));
        preglediTable.setItems(pregledi);
    }

    public void izbrisiUsluguClick(ActionEvent actionEvent){
        var usluga = uslugeView.getSelectionModel().getSelectedItem();
        if(!uslugeLjekara.contains(usluga) || usluga==null) return;
        uslugeView.getItems().remove(usluga);
        dao.obrisiUsluguZaLjekara(getTrenutnoPrijavljeniLjekar().getId(), usluga);
        pregledi = FXCollections.observableArrayList(dao.dajSvePregledeKojeLjekarMozeObaviti(trenutnoPrijavljeniLjekar.getUsername()));
        preglediTable.setItems(pregledi);
    }

    public void unesiDijagnozuITerapijuClick(ActionEvent actionEvent) throws IOException {
        if(preglediTable.getSelectionModel().getSelectedItem() == null) return;
        var id = preglediTable.getSelectionModel().getSelectedItem().getId();

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tekstualno_polje.fxml"), bundle);
        TekstualnoPoljeController controller = new TekstualnoPoljeController();
        loader.setController(controller);
        Parent root = null;
        root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Unos dijagnoze");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(true);
        stage.show();

        stage.setOnHiding(event -> {
            String dijagnoza = controller.getDijagnozaText();
            String terapija = controller.getTerapijaText();
            dao.dodajDijagnozu(id, trenutnoPrijavljeniLjekar.getId(), dijagnoza);
            dao.dodajTerapiju(id, trenutnoPrijavljeniLjekar.getId(), terapija);
            pregledi = FXCollections.observableArrayList(dao.dajSvePregledeKojeLjekarMozeObaviti(trenutnoPrijavljeniLjekar.getUsername()));
        });
    }

    public void obrisiPregledClick(ActionEvent actionEvent){
        if(preglediTable.getSelectionModel().getSelectedItem() == null) return;
        dao.obrisiPregled(preglediTable.getSelectionModel().getSelectedItem().getId());
        int index = preglediTable.getSelectionModel().getSelectedIndex();
        preglediTable.getItems().remove(index);
    }

    public void historijaPregledaClick(ActionEvent actionEvent) throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/historija_pregleda.fxml"), bundle);
        HistorijaPregledaController controller = new HistorijaPregledaController(dao.dajSvePregledeKojeJeLjekarObavio(trenutnoPrijavljeniLjekar.getUsername()), trenutnoPrijavljeniLjekar);
        loader.setController(controller);
        Parent root = null;
        root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Historija pregleda");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(true);
        stage.show();
    }
}
