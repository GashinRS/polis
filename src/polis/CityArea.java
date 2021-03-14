package polis;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class CityArea extends Pane {

    private static Polygon area = new Polygon(
                0, 0,
                        64 * 32, 0.5 * 64 * 32,
                        0, 64 * 32,
                        -64 * 32, 0.5 * 64 * 32
    );
    private static Polygon poly = new Polygon(
            0, 0,
            64 * 2, 0.5 * 64 * 2,
            0, 64 * 2,
            -64 * 2, 0.5 * 64 * 2
    );
    public CityArea(){
        this.setPrefWidth(64 * 2 * 32);
        this.setPrefHeight(64 * 32);
        this.setFocusTraversable(true);

        area.setFill(Color.rgb(204,249,170));
        area.setTranslateX(64 * 32);
        area.setTranslateY(2);

        MouseMovementTracker mouseMovementTracker = new MouseMovementTracker();
        this.getChildren().addAll(area, mouseMovementTracker);
        //poly.setTranslateX(64 * (32));
        //poly.setTranslateY(64 * (0 + 0) / 2);
    }
}
