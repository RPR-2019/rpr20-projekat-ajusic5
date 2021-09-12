package ba.unsa.etf.rpr;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AboutController {
    public Button ureduBtn;

    public void ureduClick(ActionEvent actionEvent){
        Stage stage = (Stage)ureduBtn.getScene().getWindow();
        stage.close();
    }
}