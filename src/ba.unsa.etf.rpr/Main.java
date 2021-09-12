package ba.unsa.etf.rpr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;



public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/naslovna_stranica.fxml"));
        MainController controller = new MainController();
        fxmlLoader.setController(controller);
        Scene scene = new Scene(fxmlLoader.load(), Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        stage.setTitle("Naslovna stranica");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}