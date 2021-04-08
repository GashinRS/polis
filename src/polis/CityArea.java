package polis;

import javafx.scene.layout.Pane;
import tiles.Area;


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
