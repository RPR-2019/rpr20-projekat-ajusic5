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
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class MainController {
    @FXML
    private TextField usernameFld = new TextField();
    @FXML
    private PasswordField passwordFld = new PasswordField();
    @FXML
    private Button signInBtn;
    @FXML
    private TextArea txtAreaFld = new TextArea("");
    @FXML
    public TableView<Service> servicesTab = new TableView<>();
    @FXML
    public TableColumn servicesCol;
    private DoctorsOfficeDAO dao;

    public MainController() throws SQLException {
        dao = DoctorsOfficeDAO.getInstanca();

    }

    public void initialize(){
        txtAreaFld.appendText("Dobro došli na aplikaciju naše ljekarske ordinacije.\nZakažite pregled iz udobnosti svog doma" +
                "\nNaše usluge su dostupne svaki dan od 08:00 do 16:00");
        var lis = FXCollections.observableArrayList(dao.getAllServices());
        servicesTab.setItems(lis);
        servicesCol.setCellValueFactory(new PropertyValueFactory("name"));

    }

    public void signInClick(ActionEvent actionEvent) throws IOException {

        var username = usernameFld.getText();
        var password = passwordFld.getText();
        var mode = dao.checkSignIn(username, password);
        usernameFld.setText("");
        passwordFld.setText("");
        if(mode == 1){
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/doctor_homepage.fxml"), bundle);

            DoctorController controller = new DoctorController(dao.getDoctorsServices(username), dao.getAllExaminationsDoctorCanDo(username), dao.getAllExaminationsDoctorDid(username), dao.getCurrentDoctor(username), dao.getNamesOfServices());
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/patient_homepage.fxml"), bundle);
            PatientController controller = new PatientController(dao.getNamesOfServices(), dao.getAllAppointments(), dao.getCurrentPatient(username, password), dao.getAllDoctors());
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

    public void registerClick(ActionEvent actionEvent) throws IOException {

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/registration_form.fxml"), bundle);
        RegisterController controller = new RegisterController(dao.getSpecializations());
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

            int registred = 0;
            if(patient != null){
                registred = dao.checkSignIn(patient.getUsername(), patient.getPassword());
            }
            else if(doctor !=null ){
                registred = dao.checkSignIn(doctor.getUsername(), doctor.getPassword());
            }
            if(registred==-1){
                if(controller.getProfileTypeCB().equalsIgnoreCase("pacijent"))
                    dao.addAPatient(controller.getPacijent());
                else
                    dao.addADoctor(controller.getLjekar());
            }
        });

    }

    public void bosnianClick(ActionEvent actionEvent) throws IOException, SQLException {
        Stage primaryStage = (Stage) usernameFld.getScene().getWindow();
        primaryStage.close();

        MainController ctrl = new MainController();
        Locale.setDefault(new Locale("bs_BA", "BA"));
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/homepage.fxml"), bundle);

        loader.setController(ctrl);
        Parent root = loader.load();
        primaryStage.setTitle("Naslovna stranica");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.show();

    }

    public void englishClick(ActionEvent actionEvent) throws IOException, SQLException {

        Stage primaryStage = (Stage) usernameFld.getScene().getWindow();
        primaryStage.close();

        MainController ctrl = new MainController();
        Locale.setDefault(new Locale("en_US", "US"));
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/homepage.fxml"), bundle);

        loader.setController(ctrl);
        Parent root = loader.load();
        primaryStage.setTitle("Home page");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.show();

    }

    public void aboutClick(ActionEvent actionEvent) throws IOException {

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/about.fxml"), bundle);
        AboutController controller = new AboutController();
        loader.setController(controller);
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("About");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();

    }

    public void exitClick(ActionEvent actionEvent){
        Stage stage = (Stage) signInBtn.getScene().getWindow();
        stage.close();
    }

}
