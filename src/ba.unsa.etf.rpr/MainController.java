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
import java.util.Locale;
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
    private DoctorsOfficeDAO dao;

    public MainController() {
        dao = DoctorsOfficeDAO.getInstanca();

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ljekar_naslovna.fxml"), bundle);

            DoctorController controller = new DoctorController(dao.dajUslugeZaLjekara(username), dao.dajSvePregledeKojeLjekarMozeObaviti(username), dao.dajSvePregledeKojeJeLjekarObavio(username), dao.dajTrenutnoPrijavljenogLjekara(username), dao.dajNaziveUsluga());
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/pacijent_naslovna.fxml"), bundle);
            PatientController controller = new PatientController(dao.dajNaziveUsluga(), dao.dajSveZakazanePreglede(), dao.dajTrenutnoPrijavljenogPacijenta(username, password), dao.dajSveLjekare());
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

    public void registracijaClick(ActionEvent actionEvent) throws IOException {

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/registracija_forma.fxml"), bundle);
        RegisterController controller = new RegisterController();
        loader.setController(controller);
        Parent root = null;
        root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(true);
        stage.show();

        stage.setOnHiding(event -> {

            Patient patient = controller.getPacijent();
            Doctor doctor = controller.getLjekar();

            int imaRegistrovan = 0;
            if(patient != null){
                imaRegistrovan = dao.provjeriPrijavu(patient.getUsername(), patient.getPassword());
            }
            else if(doctor !=null ){
                imaRegistrovan = dao.provjeriPrijavu(doctor.getUsername(), doctor.getPassword());
            }
            if(imaRegistrovan==-1){
                if(controller.getVrstaKRBC().equalsIgnoreCase("pacijent")) dao.dodajPacijenta(controller.getPacijent());
                else dao.dodajLjekara(controller.getLjekar());
            }
        });

    }

    public void bosanskiClick(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = (Stage) kImeFld.getScene().getWindow();
        primaryStage.close();

        MainController ctrl = new MainController();
        Locale.setDefault(new Locale("bs_BA", "BA"));
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/naslovna_stranica.fxml" ), bundle);

        loader.setController(ctrl);
        Parent root = loader.load();
        primaryStage.setTitle("Naslovna stranica");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.show();

    }

    public void engleskiClick(ActionEvent actionEvent) throws IOException {

        Stage primaryStage = (Stage) kImeFld.getScene().getWindow();
        primaryStage.close();

        MainController ctrl = new MainController();
        Locale.setDefault(new Locale("en_US", "US"));
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/naslovna_stranica.fxml" ), bundle);

        loader.setController(ctrl);
        Parent root = loader.load();
        primaryStage.setTitle("Home page");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.show();

    }

    public void aboutClick(ActionEvent actionEvent) throws IOException {

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/o_nama.fxml"), bundle);
        AboutController controller = new AboutController();
        loader.setController(controller);
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("About");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();

    }

    public void izlazClick(ActionEvent actionEvent){
        Stage stage = (Stage) prijavaBtn.getScene().getWindow();
        stage.close();
    }

}
