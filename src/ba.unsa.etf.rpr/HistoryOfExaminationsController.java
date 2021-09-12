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
    public TableColumn<Examination, String> surnameCol;
    public TableColumn<Examination,String> nameCol;
    public TableColumn dateCol;
    public TableColumn typeOfExaminationCol;
    private ObservableList<Examination> examinations;
    private User u;

    public HistoryOfExaminationsController(ArrayList<Examination> examinations, User u) {
        this.examinations = FXCollections.observableArrayList(examinations);
        this.u = u;
    }
    public void initialize(){

        historyTable.setItems(examinations);

        if(u.getClass().getName().equals("ba.unsa.etf.rpr.probniprojekat.Pacijent")){
            surnameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLjekar().getSurname()));
            nameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLjekar().getName()));
        }else {
            surnameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPacijent().getSurname()));
            nameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPacijent().getName()));
        }
        dateCol.setCellValueFactory(new PropertyValueFactory<>("datumIVrijemePregleda"));
        typeOfExaminationCol.setCellValueFactory(new PropertyValueFactory<>("vrstaPregleda"));

    }
}
