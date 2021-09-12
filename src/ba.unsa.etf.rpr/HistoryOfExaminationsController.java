package ba.unsa.etf.rpr;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class HistoryOfExaminationsController {
    public TableView<Examination> historyTable;
    public TableColumn<Examination, String> prezimeCol;
    public TableColumn<Examination,String> imeCol;
    public TableColumn datumCol;
    public TableColumn vrstaPregledaCol;
    private ObservableList<Examination> pregledi;
    User k;

    public HistoryOfExaminationsController(ArrayList<Examination> pregledi, User k) {
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
