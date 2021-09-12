package ba.unsa.etf.rpr;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AboutController {
    public Button okBtn;

    public void okClick(ActionEvent actionEvent){
        Stage stage = (Stage) okBtn.getScene().getWindow();
        stage.close();
    }
}