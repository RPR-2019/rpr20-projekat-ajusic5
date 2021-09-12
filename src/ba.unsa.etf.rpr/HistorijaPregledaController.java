package ba.unsa.etf.rpr;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class HistorijaPregledaController {
    public TableView<Pregled> historyTable;
    public TableColumn<Pregled, String> prezimeCol;
    public TableColumn<Pregled,String> imeCol;
    public TableColumn datumCol;
    public TableColumn vrstaPregledaCol;
    private ObservableList<Pregled> pregledi;
    Korisnik k;

    public HistorijaPregledaController(ArrayList<Pregled> pregledi, Korisnik k) {
        this.pregledi= FXCollections.observableArrayList(pregledi);
        this.k = k;
    }
    public void initialize(){

        historyTable.setItems(pregledi);

        if(k.getClass().getName().equals("ba.unsa.etf.rpr.probniprojekat.Pacijent")){
            prezimeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLjekar().getPrezime()));
            imeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLjekar().getIme()));
        }else {
            prezimeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPacijent().getPrezime()));
            imeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPacijent().getIme()));
        }
        datumCol.setCellValueFactory(new PropertyValueFactory<>("datumIVrijemePregleda"));
        vrstaPregledaCol.setCellValueFactory(new PropertyValueFactory<>("vrstaPregleda"));

    }
}
