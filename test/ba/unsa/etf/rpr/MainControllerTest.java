package ba.unsa.etf.rpr;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
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
class MainControllerTest {

    Stage theStage;
    MainController controller;
    DoctorsOfficeDAO dao = DoctorsOfficeDAO.getInstanca();


    @Start
    public void start(Stage stage) throws Exception {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/homepage.fxml"), bundle);
        controller = new MainController();
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

    @Test
    public void emptyFieldsTest(FxRobot robot){
        robot.lookup("#signInBtn");
        robot.clickOn("#signInBtn");
        robot.lookup(".dialog-pane").tryQuery().isPresent();
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);

        assertNotNull(dialogPane.lookupAll("Prazno polje!"));

        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
    }

    @Test
    public void invalidUsernameOrPasswordTest(FxRobot robot){
        robot.lookup("#usernameFld").tryQuery().isPresent();
        robot.clickOn("#usernameFld");
        robot.write("Amna");

        robot.clickOn("#passwordFld");
        robot.write("amna");

        robot.clickOn("#signInBtn");
        robot.lookup(".dialog-pane").tryQuery().isPresent();
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);

        assertNotNull(dialogPane.lookupAll("Ne postoji korisnički račun s ovim podacima!"));

        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);

    }
}