package ba.unsa.etf.rpr;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class TextAreaController {
    public Button okBtn;
    public TextArea dijagnozaArea;
    public TextArea terapijaArea;
    private SimpleStringProperty dijagnozaText;
    private SimpleStringProperty terapijaText;

    public TextAreaController() {
        dijagnozaText = new SimpleStringProperty("");
        terapijaText = new SimpleStringProperty("");

    }

    @FXML
    public void initialize(){
        dijagnozaArea.textProperty().bindBidirectional(dijagnozaText);
        terapijaArea.textProperty().bindBidirectional(terapijaText);
    }

    public void okClick(ActionEvent actionEvent){
        Stage stage = (Stage) okBtn.getScene().getWindow();
        stage.close();
    }

    public String getDijagnozaText() {
        return dijagnozaText.getValue();
    }

    public String getTerapijaText() {
        return terapijaText.getValue();
    }
}
