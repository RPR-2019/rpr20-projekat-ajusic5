package ba.unsa.etf.rpr;

import javafx.fxml.FXMLLoader;
import javafx.geometry.VerticalDirection;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;


import java.sql.SQLException;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class RegisterControllerTest {

    Stage theStage;
    RegisterController controller;
    DoctorsOffice dao = DoctorsOffice.getInstanca();

    @Start
    public void start(Stage stage) throws Exception {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/registration_form.fxml"), bundle);
        controller = new RegisterController(dao.getSpecializations());
        loader.setController(controller);
        Parent root = loader.load();
        stage.setTitle("Registracija");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
        theStage = stage;

    }

    @BeforeEach
    public void resetujBazu() throws SQLException {
        dao.vratiBazuNaDefault();
    }


    @AfterEach
    public void zatvoriFormu(FxRobot robot) {
        if (robot.lookup("#cancelBtn").tryQuery().isPresent())
            robot.clickOn("#cancelBtn");
    }


    @Test
    public void emptyFieldTest(FxRobot robot){
        robot.clickOn("#registerBtn");

        robot.lookup(".dialog-pane").tryQuery().isPresent();
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);

        assertNotNull(dialogPane.lookupAll("Prazno polje"));

        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
    }

    @Test
    public void successfulRegistrationTest(FxRobot robot){

        robot.lookup("#nameFld").tryQuery().isPresent();
        robot.clickOn("#nameFld");
        robot.write("Armena");

        robot.clickOn("#surnameFld");
        robot.write("Tarasyants");

        robot.clickOn("#dayCB");
        robot.lookup("31");
        robot.clickOn("31");

        robot.clickOn("#monthCB");
        robot.lookup("10");
        robot.clickOn("10");

        robot.clickOn("#yearCB");
        robot.scroll(54, VerticalDirection.DOWN);
        robot.lookup("1999");
        robot.clickOn("1999");

        robot.clickOn("#usernameFld");
        robot.write("atarasyants1");

        robot.clickOn("#passwordFld");
        robot.write("armena");

        robot.clickOn("#profileTypeCB");
        robot.lookup("Pacijent");
        robot.clickOn("Pacijent");

        robot.clickOn("#sexCB");
        robot.lookup("Ž");
        robot.clickOn("Ž");


        robot.clickOn("#registerBtn");
        assertFalse(theStage.isShowing());

    }

    @Test
    public void doctorRegistrationTest(FxRobot robot){

        robot.lookup("#nameFld").tryQuery().isPresent();
        robot.clickOn("#nameFld");
        robot.write("Armena");

        robot.clickOn("#surnameFld");
        robot.write("Tarasyants");

        robot.clickOn("#dayCB");
        robot.lookup("31");
        robot.clickOn("31");

        robot.clickOn("#monthCB");
        robot.lookup("10");
        robot.clickOn("10");

        robot.clickOn("#yearCB");
        robot.scroll(54, VerticalDirection.DOWN);
        robot.lookup("1999");
        robot.clickOn("1999");

        robot.clickOn("#usernameFld");
        robot.write("atarasyants1");

        robot.clickOn("#passwordFld");
        robot.write("armena");

        robot.clickOn("#profileTypeCB");
        robot.lookup("Ljekar");
        robot.clickOn("Ljekar");

        robot.clickOn("#sexCB");
        robot.lookup("Ž");
        robot.clickOn("Ž");


        robot.clickOn("#registerBtn");

        robot.lookup(".dialog-pane").tryQuery().isPresent();
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);

        assertNotNull(dialogPane.lookupAll("Ljekar mora imati specijalizaciju"));

        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);

    }

}