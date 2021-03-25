package polis;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * Dit is de eigenlijke stad met het groene veld en alle gebouwen en wegen.
 */

public class CityArea extends Pane {

    private static final Polygon area = new Polygon(
                0, 0,
                        64 * 32, 0.5 * 64 * 32,
                        0, 64 * 32,
                        -64 * 32, 0.5 * 64 * 32
    );

    public CityArea(MouseMovementTracker mouseMovementTracker){
        this.setPrefWidth(64 * 2 * 32);
        this.setPrefHeight(64 * 32);
        this.setFocusTraversable(true);

        area.setFill(Color.rgb(204,249,170));
        area.setTranslateX(64 * 32);
        area.setTranslateY(0);

        this.getChildren().addAll(area, mouseMovementTracker);
    }
}
