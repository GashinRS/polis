package polis;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import tiles.Area;
import tiles.NonRemovableRoadTile;

import java.util.Random;


public class CityArea extends Pane {

    //private MouseMovementTracker mouseMovementTracker;

    public CityArea(MouseMovementTracker mouseMovementTracker){
        this.setPrefWidth(64 * 2 * 32);
        this.setPrefHeight(64 * 32);
        this.setFocusTraversable(true);

        Area area = new Area();
        area.setTranslateX(64 * 32);
        area.setTranslateY(0);

        this.getChildren().add(area);

        //this.mouseMovementTracker=mouseMovementTracker;
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
