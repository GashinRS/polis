package polis;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import tiles.Area;
import tiles.NonRemovableRoadTile;

/**
 * Deze klasse bevat alle visuele aspecten van de stad (afbeeldingen en de achtergrond)
 */

public class CityArea extends Pane {

    public CityArea(MouseMovementTracker mouseMovementTracker){
        this.setPrefWidth(64 * 2 * 32);
        this.setPrefHeight(64 * 32);
        this.setFocusTraversable(true);

        Area area = new Area();
        area.setTranslateX(64 * 32);
        area.setTranslateY(0);

        this.getChildren().add(area);

        getChildren().add(mouseMovementTracker);
        mouseMovementTracker.setCityArea(this);
        for (int i = 0; i < 16; i++) {
            getChildren().add(new NonRemovableRoadTile(i, 14, mouseMovementTracker));
        }

    }

    public void setTranslateXY(Node node, int r, int k) {
        node.setTranslateX(64 * (32 - r + k));
        node.setTranslateY(64 * (r + k) / 2);
    }

}
