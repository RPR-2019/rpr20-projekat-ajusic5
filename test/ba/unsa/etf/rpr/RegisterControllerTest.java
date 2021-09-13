package ba.unsa.etf.rpr;

import javafx.fxml.FXMLLoader;
import javafx.geometry.VerticalDirection;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.awt.geom.RectangularShape;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class RegisterControllerTest {

    Stage theStage;
    RegisterController controller;
    @Start
    public void start(Stage stage) throws Exception {
        DoctorsOfficeDAO dao = DoctorsOfficeDAO.getInstanca();
        dao.vratiBazuNaDefault();
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

    @Test
    public void emptyFieldTest(FxRobot robot){
        robot.clickOn("#registerBtn");

        robot.lookup(".dialog-pane").tryQuery().isPresent();
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);

        assertNotNull(dialogPane.lookupAll("Prazno polje"));
    }

    @Test
    public void successfulRegistrationTest(FxRobot robot){
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

}