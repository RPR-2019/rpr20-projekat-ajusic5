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
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class DoctorController {

    public Button odjavaBtn;
    public ChoiceBox<String> uslugeCB;
    public ListView<String> uslugeView;
    public TableView<Examination> preglediTable;
    public TableColumn<Examination, String> prezimeCol;
    public TableColumn<Examination, String> imeCol;
    public TableColumn datumIVrijemeCol;
    public TableColumn vrstaPregledaCol;

    private Doctor trenutnoPrijavljeniDoctor;
    private ObservableList<String> uslugeLjekara;
    private ArrayList<Examination> preglediKojeLjekarMozeObaviti;
    private ArrayList<Examination> preglediKojeJeLjekarObavio;
    private ObservableList<String> sveUsluge;
    private ArrayList<String> uslugeKojeTrebaIzbrisati;
    private DoctorsOfficeDAO dao;
    private ObservableList<Examination> pregledi;

    public DoctorController(ArrayList<String> uslugeLjekara, ArrayList<Examination> preglediKojeLjekarMozeObaviti, ArrayList<Examination> preglediKojeJeLjekarObavio, Doctor trenutnoPrijavljeniDoctor, ArrayList<String> sveUsluge) {
        this.uslugeLjekara = FXCollections.observableArrayList(uslugeLjekara);
        this.preglediKojeLjekarMozeObaviti = preglediKojeLjekarMozeObaviti;
        this.preglediKojeJeLjekarObavio = preglediKojeJeLjekarObavio;
        this.trenutnoPrijavljeniDoctor = trenutnoPrijavljeniDoctor;
        this.sveUsluge = FXCollections.observableArrayList(sveUsluge);
        dao = DoctorsOfficeDAO.getInstanca();
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
    public Doctor getTrenutnoPrijavljeniLjekar() {
        return trenutnoPrijavljeniDoctor;
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
        pregledi = FXCollections.observableArrayList(dao.dajSvePregledeKojeLjekarMozeObaviti(trenutnoPrijavljeniDoctor.getUsername()));
        preglediTable.setItems(pregledi);
    }

    public void izbrisiUsluguClick(ActionEvent actionEvent){
        var usluga = uslugeView.getSelectionModel().getSelectedItem();
        if(!uslugeLjekara.contains(usluga) || usluga==null) return;
        uslugeView.getItems().remove(usluga);
        dao.obrisiUsluguZaLjekara(getTrenutnoPrijavljeniLjekar().getId(), usluga);
        pregledi = FXCollections.observableArrayList(dao.dajSvePregledeKojeLjekarMozeObaviti(trenutnoPrijavljeniDoctor.getUsername()));
        preglediTable.setItems(pregledi);
    }

    public void unesiDijagnozuITerapijuClick(ActionEvent actionEvent) throws IOException {
        if(preglediTable.getSelectionModel().getSelectedItem() == null) return;
        var id = preglediTable.getSelectionModel().getSelectedItem().getId();

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tekstualno_polje.fxml"), bundle);
        TextAreaController controller = new TextAreaController();
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
            dao.dodajDijagnozu(id, trenutnoPrijavljeniDoctor.getId(), dijagnoza);
            dao.dodajTerapiju(id, trenutnoPrijavljeniDoctor.getId(), terapija);
            pregledi = FXCollections.observableArrayList(dao.dajSvePregledeKojeLjekarMozeObaviti(trenutnoPrijavljeniDoctor.getUsername()));
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
        HistoryOfExaminationsController controller = new HistoryOfExaminationsController(dao.dajSvePregledeKojeJeLjekarObavio(trenutnoPrijavljeniDoctor.getUsername()), trenutnoPrijavljeniDoctor);
        loader.setController(controller);
        Parent root = null;
        root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Historija pregleda");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(true);
        stage.show();
    }

    public void printClick(){
        try {
            new Report().showReport(dao.getConn());
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
