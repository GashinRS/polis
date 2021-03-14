package polis;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import prog2.util.Viewport;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        Scene scene = new Scene (new CityMap());
        stage.setScene(scene);
        stage.setTitle("Polis - 2021 © Universiteit Gent");
        stage.show();
    }

}
