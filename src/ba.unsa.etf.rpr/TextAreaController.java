package ba.unsa.etf.rpr;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class TextAreaController {
    public Button okBtn;
    public TextArea dijagnosisArea;
    public TextArea therapyArea;
    private SimpleStringProperty diagnosisText;
    private SimpleStringProperty therapyText;

    public TextAreaController() {
        diagnosisText = new SimpleStringProperty("");
        therapyText = new SimpleStringProperty("");

    }

    @FXML
    public void initialize(){
        dijagnosisArea.textProperty().bindBidirectional(diagnosisText);
        therapyArea.textProperty().bindBidirectional(therapyText);
    }

    public void okClick(ActionEvent actionEvent){
        Stage stage = (Stage) okBtn.getScene().getWindow();
        stage.close();
    }

    public String getDiagnosisText() {
        return diagnosisText.getValue();
    }

    public String getTherapyText() {
        return therapyText.getValue();
    }
}
