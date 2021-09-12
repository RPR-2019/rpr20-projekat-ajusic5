package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;

public class RegisterController {

    private ObservableList<String> lista;
    private ObservableList<String> l1;
    private ObservableList<String> l2;
    private ObservableList<String> l3;
    private ObservableList<String> l4;
    private ObservableList<String> l5;
    private Patient patient;
    private Doctor doctor;
    @FXML
    public Button registracijaBtn = new Button();
    @FXML
    public ChoiceBox danCB = new ChoiceBox();
    @FXML
    public ChoiceBox mjesecCB = new ChoiceBox();
    @FXML
    public ChoiceBox godinaCB = new ChoiceBox();
    @FXML
    public ChoiceBox<String> vrstaKRBC = new ChoiceBox();
    @FXML
    public ChoiceBox<String> specCB = new ChoiceBox();
    @FXML
    public ChoiceBox<String> spolCB = new ChoiceBox();
    @FXML
    public TextField imeFld = new TextField();
    @FXML
    public TextField prezimeFld = new TextField();
    @FXML
    public TextField kImeFld = new TextField();
    @FXML
    public PasswordField lozinkaFld = new PasswordField();

    @FXML
    public void initialize(){
        danCB.setItems(lista);
        mjesecCB.setItems(l1);
        godinaCB.setItems(l2);
        vrstaKRBC.setItems(l3);
        spolCB.setItems(l4);
        specCB.setItems(l5);
        doctor = null;
        patient = null;
    }

    public RegisterController() {

        ArrayList<String> l = new ArrayList<>();
        for(int i=1; i<32; i++)
            l.add(String.valueOf(i));

        lista= FXCollections.observableArrayList(l);
        l.clear();

        for(int i=1; i<13; i++)
            l.add(String.valueOf(i));
        l1=FXCollections.observableArrayList(l);

        l.clear();
        for(int i=1900; i<2022; i++){
            l.add(String.valueOf(i));
        }
        l2=FXCollections.observableArrayList(l);

        l.clear();
        l.add("Ljekar"); l.add("Pacijent");
        l3 = FXCollections.observableArrayList(l);

        l.clear();
        l.add("M"); l.add("Ž");
        l4=FXCollections.observableArrayList(l);

        l.clear();
        l.add("Oftamolog");
        l5=FXCollections.observableArrayList(l);
    }

    public String getVrstaKRBC() {
        return vrstaKRBC.getValue();
    }

    public Patient getPacijent() {
        return patient;
    }

    public Doctor getLjekar() {
        return doctor;
    }

    public void registracijaAction(ActionEvent actionEvent) {

        int dan = Integer.parseInt((String) danCB.getValue());
        int mjesec =Integer.parseInt((String) mjesecCB.getValue());
        int godina = Integer.parseInt((String) godinaCB.getValue());
        LocalDate datum = LocalDate.of(godina, mjesec, dan);
        String spol = (String) spolCB.getValue();

        if(vrstaKRBC.getValue().equals("Pacijent")) {
            if(spol=="M")
            patient = new Patient(0, prezimeFld.getText(), imeFld.getText(), datum, kImeFld.getText(), lozinkaFld.getText(), ProfileType.PACIJENT, SexOfAUser.MUSKI, 1);
            else
                patient = new Patient(0, prezimeFld.getText(), imeFld.getText(), datum, kImeFld.getText(), lozinkaFld.getText(), ProfileType.PACIJENT, SexOfAUser.ZENSKI, 1);

        }
        else {
            if(spol=="M")
            doctor = new Doctor(0, prezimeFld.getText(), imeFld.getText(), datum, kImeFld.getText(), lozinkaFld.getText(), ProfileType.LJEKAR, SexOfAUser.MUSKI, specCB.getValue(), null);
            else
                doctor = new Doctor(0, prezimeFld.getText(), imeFld.getText(), datum, kImeFld.getText(), lozinkaFld.getText(), ProfileType.LJEKAR, SexOfAUser.ZENSKI, specCB.getValue(), null);

        }
        Stage stage = (Stage) registracijaBtn.getScene().getWindow();
        stage.close();
    }

}
