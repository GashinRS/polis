package polis;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import tiles.Area;

/**
 * Dit is de eigenlijke stad met het groene veld en alle gebouwen en wegen.
 */

public class CityArea extends Pane {

    private MouseMovementTracker mouseMovementTracker;

    public CityArea(){
        this.setPrefWidth(64 * 2 * 32);
        this.setPrefHeight(64 * 32);
        this.setFocusTraversable(true);

        Area area = new Area();
        area.setTranslateX(64 * 32);
        area.setTranslateY(0);

        this.getChildren().add(area);
    }

    public void setMouseMovementTracker(MouseMovementTracker mouseMovementTracker) {
        this.mouseMovementTracker = mouseMovementTracker;
        this.getChildren().add(mouseMovementTracker);
    }

    public MouseMovementTracker getMouseMovementTracker() {
        return mouseMovementTracker;
    }
}
