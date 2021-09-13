package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;

public class RegisterController {

    private ObservableList<String> list;
    private ObservableList<String> l1;
    private ObservableList<String> l2;
    private ObservableList<String> l3;
    private ObservableList<String> l4;
    private ObservableList<String> l5;
    private Patient patient;
    private Doctor doctor;
    @FXML
    public Button registerBtn = new Button();
    @FXML
    public ChoiceBox dayCB = new ChoiceBox();
    @FXML
    public ChoiceBox monthCB = new ChoiceBox();
    @FXML
    public ChoiceBox yearCB = new ChoiceBox();
    @FXML
    public ChoiceBox<String> profileTypeCB = new ChoiceBox();
    @FXML
    public ChoiceBox<String> specializationCB = new ChoiceBox();
    @FXML
    public ChoiceBox<String> sexCB = new ChoiceBox();
    @FXML
    public TextField nameFld = new TextField();
    @FXML
    public TextField surnameFld = new TextField();
    @FXML
    public TextField usernameFld = new TextField();
    @FXML
    public PasswordField passwordFld = new PasswordField();

    @FXML
    public void initialize(){
        dayCB.setItems(list);
        monthCB.setItems(l1);
        yearCB.setItems(l2);
        profileTypeCB.setItems(l3);
        sexCB.setItems(l4);
        specializationCB.setItems(l5);
        doctor = null;
        patient = null;
    }

    public RegisterController(ArrayList<String> namesOfServices) {

        ArrayList<String> l = new ArrayList<>();
        for(int i=1; i<32; i++)
            l.add(String.valueOf(i));

        list = FXCollections.observableArrayList(l);
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

        l5=FXCollections.observableArrayList(namesOfServices);

    }

    public String getProfileTypeCB() {
        return profileTypeCB.getValue();
    }

    public Patient getPacijent() {
        return patient;
    }

    public Doctor getLjekar() {
        return doctor;
    }

    public void registracijaAction(ActionEvent actionEvent) {

        if(nameFld.getText().equals("") || surnameFld.getText().equals("") || dayCB.getValue()==null || monthCB.getValue()==null || yearCB.getValue()==null
            || usernameFld.getText().equals("") || passwordFld.getText().equals("") || profileTypeCB.getValue()==null || sexCB.getValue()==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška");
            alert.setHeaderText("Neispravni podaci");
            alert.setContentText("Prazno polje!");
            alert.setResizable(true);
            alert.show();
            return;
        }

        int day = Integer.parseInt((String) dayCB.getValue());
        int month =Integer.parseInt((String) monthCB.getValue());
        int year = Integer.parseInt((String) yearCB.getValue());
        LocalDate datum = LocalDate.of(year, month, day);
        String sex = sexCB.getValue();

        if(profileTypeCB.getValue().equals("Pacijent")) {
            if(sex=="M")
                patient = new Patient(0, surnameFld.getText(), nameFld.getText(), datum, usernameFld.getText(), passwordFld.getText(), ProfileType.PACIJENT, SexOfAUser.MUSKI, 1);
            else
                patient = new Patient(0, surnameFld.getText(), nameFld.getText(), datum, usernameFld.getText(), passwordFld.getText(), ProfileType.PACIJENT, SexOfAUser.ZENSKI, 1);

        }
        else if(specializationCB.getValue()!=null){
            if(sex=="M")
                doctor = new Doctor(0, surnameFld.getText(), nameFld.getText(), datum, usernameFld.getText(), passwordFld.getText(), ProfileType.LJEKAR, SexOfAUser.MUSKI, specializationCB.getValue(), null);
            else
                doctor = new Doctor(0, surnameFld.getText(), nameFld.getText(), datum, usernameFld.getText(), passwordFld.getText(), ProfileType.LJEKAR, SexOfAUser.ZENSKI, specializationCB.getValue(), null);

        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška");
            alert.setHeaderText("Neispravni podaci");
            alert.setContentText("Ljekar mora imati specijalizaciju!");
            alert.setResizable(true);
            alert.show();
            return;
        }
        Stage stage = (Stage) registerBtn.getScene().getWindow();
        stage.close();
    }

}
