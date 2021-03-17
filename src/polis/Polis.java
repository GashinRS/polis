package polis;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Polis extends Application {

    @Override
    public void start(Stage stage) {
        stage.setScene(new Scene (new CityContainer()));
        stage.setTitle("Polis - 2021 © Universiteit Gent");
        stage.show();
    }
}
