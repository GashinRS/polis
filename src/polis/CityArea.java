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

    private MouseMovementTracker mouseMovementTracker;

    private static final Polygon area = new Polygon(
                0, 0,
                        64 * 32, 0.5 * 64 * 32,
                        0, 64 * 32,
                        -64 * 32, 0.5 * 64 * 32
    );

    public CityArea(){
        this.setPrefWidth(64 * 2 * 32);
        this.setPrefHeight(64 * 32);
        this.setFocusTraversable(true);

        area.setFill(Color.rgb(204,249,170));
        area.setTranslateX(64 * 32);
        area.setTranslateY(0);

        this.getChildren().add(area);
    }

    public void setTranslateXY(Polygon polygon, int r, int k){
        polygon.setTranslateX(64 * (32 - r + k));
        polygon.setTranslateY(64 * (r + k) / 2);
    }

    public void setMouseMovementTracker(MouseMovementTracker mouseMovementTracker) {
        this.mouseMovementTracker = mouseMovementTracker;
        this.getChildren().add(mouseMovementTracker);
    }

    public MouseMovementTracker getMouseMovementTracker() {
        return mouseMovementTracker;
    }
}
