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

    public Button signOutBtn;
    public ChoiceBox<String> servicesCB;
    public ListView<String> servicesView;
    public TableView<Examination> examinationsTable;
    public TableColumn<Examination, String> surnameCol;
    public TableColumn<Examination, String> nameCol;
    public TableColumn dateAndTimeCol;
    public TableColumn typeOfExaminationCol;

    private Doctor doctor;
    private ObservableList<String> doctorsServices;
    private ArrayList<Examination> examinationsThatDoctorCanDo;
    private ArrayList<Examination> examinationsThatDoctorDid;
    private ObservableList<String> allServices;
    private DoctorsOfficeDAO dao;
    private ObservableList<Examination> examinations;

    public DoctorController(ArrayList<String> doctorsServices, ArrayList<Examination> examinationsThatDoctorCanDo, ArrayList<Examination> examinationsThatDoctorDid, Doctor doctor, ArrayList<String> allServices) {
        this.doctorsServices = FXCollections.observableArrayList(doctorsServices);
        this.examinationsThatDoctorCanDo = examinationsThatDoctorCanDo;
        this.examinationsThatDoctorDid = examinationsThatDoctorDid;
        this.doctor = doctor;
        this.allServices = FXCollections.observableArrayList(allServices);
        dao = DoctorsOfficeDAO.getInstanca();
        examinations = FXCollections.observableArrayList(examinationsThatDoctorCanDo);
    }
    @FXML
    public void initialize(){
        examinationsTable.setItems(examinations);
        surnameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPacijent().getSurname()));
        nameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPacijent().getName()));
        dateAndTimeCol.setCellValueFactory(new PropertyValueFactory<>("datumIVrijemePregleda"));
        typeOfExaminationCol.setCellValueFactory(new PropertyValueFactory<>("vrstaPregleda"));
        servicesView.setItems(doctorsServices);
        servicesCB.setItems(allServices);
    }
    public Doctor getDoctor() {
        return doctor;
    }


    public void signOutClick(ActionEvent actionEvent){
        Stage stage = (Stage) signOutBtn.getScene().getWindow();
        stage.close();
    }
    public void addAServiceClick(ActionEvent actionEvent){
        if(doctorsServices.contains(servicesCB.getValue()) || servicesCB.getValue()==null) return;
        getDoctor().setServices(servicesView.getItems());
        doctorsServices.add(servicesCB.getValue());
        dao.dodajUsluguZaLjekara(getDoctor().getId() , servicesCB.getValue());
        examinations = FXCollections.observableArrayList(dao.dajSvePregledeKojeLjekarMozeObaviti(doctor.getUsername()));
        examinationsTable.setItems(examinations);
    }

    public void deleteTheServiceClick(ActionEvent actionEvent){
        var service = servicesView.getSelectionModel().getSelectedItem();
        if(!doctorsServices.contains(service) || service==null) return;
        servicesView.getItems().remove(service);
        dao.obrisiUsluguZaLjekara(getDoctor().getId(), service);
        examinations = FXCollections.observableArrayList(dao.dajSvePregledeKojeLjekarMozeObaviti(doctor.getUsername()));
        examinationsTable.setItems(examinations);
    }

    public void addDiagnosisAndTherapyClick(ActionEvent actionEvent) throws IOException {
        if(examinationsTable.getSelectionModel().getSelectedItem() == null) return;
        var id = examinationsTable.getSelectionModel().getSelectedItem().getId();

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
            String diagnosis = controller.getDijagnozaText();
            String therapy = controller.getTerapijaText();
            dao.dodajDijagnozu(id, doctor.getId(), diagnosis);
            dao.dodajTerapiju(id, doctor.getId(), therapy);
            examinations = FXCollections.observableArrayList(dao.dajSvePregledeKojeLjekarMozeObaviti(doctor.getUsername()));
        });
    }

    public void deleteTheExaminationClick(ActionEvent actionEvent){
        if(examinationsTable.getSelectionModel().getSelectedItem() == null) return;
        dao.obrisiPregled(examinationsTable.getSelectionModel().getSelectedItem().getId());
        int index = examinationsTable.getSelectionModel().getSelectedIndex();
        examinationsTable.getItems().remove(index);
    }

    public void historyClick(ActionEvent actionEvent) throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/historija_pregleda.fxml"), bundle);
        HistoryOfExaminationsController controller = new HistoryOfExaminationsController(dao.dajSvePregledeKojeJeLjekarObavio(doctor.getUsername()), doctor);
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
