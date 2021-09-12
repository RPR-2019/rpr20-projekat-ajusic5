package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

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

    public void prijavaClick(ActionEvent actionEvent) throws IOException {

        var username = kImeFld.getText();
        var password = lozinkaFld.getText();
        var mode = dao.provjeriPrijavu(username, password);
        kImeFld.setText("");
        lozinkaFld.setText("");
        if(mode == 1){
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ljekar_naslovna.fxml"), bundle);

            LjekarController controller = new LjekarController(dao.dajUslugeZaLjekara(username), dao.dajSvePregledeKojeLjekarMozeObaviti(username), dao.dajSvePregledeKojeJeLjekarObavio(username), dao.dajTrenutnoPrijavljenogLjekara(username), dao.dajNaziveUsluga());
            loader.setController(controller);
            Parent root = null;
            root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Ljekar naslovna");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.show();


        }
        else if(mode == 2){
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("pacijent_naslovna.fxml"), bundle);
            PacijentController controller = new PacijentController(dao.dajNaziveUsluga(), dao.dajSveZakazanePreglede(), dao.dajTrenutnoPrijavljenogPacijenta(username, password), dao.dajSveLjekare());
            loader.setController(controller);
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Pacijent naslovna");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška");
            alert.setHeaderText("Neispravni podaci");
            alert.setContentText("Ne postoji korisnički račun s ovim podacima!");
            alert.setResizable(true);
            alert.show();
        }

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
