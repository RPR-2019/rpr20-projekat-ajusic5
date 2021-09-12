package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainController {
    @FXML
    private TextField kImeFld = new TextField();
    @FXML
    private PasswordField lozinkaFld = new PasswordField();
    @FXML
    private Button prijavaBtn;
    @FXML
    private TextArea txtAreaFld = new TextArea("");
    @FXML
    public TableView<Usluga> uslugeTab = new TableView<>();
    @FXML
    public TableColumn uslugeCol;
    private OrdinacijaDAO dao;

    public MainController() {
        dao = OrdinacijaDAO.getInstanca();

    }

    public void initialize(){
        txtAreaFld.appendText("Dobro došli na aplikaciju naše ljekarske ordinacije.\nZakažite pregled iz udobnosti svog doma" +
                "\nNaše usluge su dostupne svaki dan od 08:00 do 16:00");
        var lis = FXCollections.observableArrayList(dao.dajSveUsluge());
        uslugeTab.setItems(lis);
        uslugeCol.setCellValueFactory(new PropertyValueFactory("name"));

    }

    public void prijavaClick(ActionEvent actionEvent){

    }

    public void registracijaClick(ActionEvent actionEvent){

    }

    public void bosanskiClick(ActionEvent actionEvent){

    }

    public void engleskiClick(ActionEvent actionEvent){

    }

    public void aboutClick(ActionEvent actionEvent){

    }

    public void izlazClick(ActionEvent actionEvent){

    }



}
