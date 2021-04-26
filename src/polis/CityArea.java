package polis;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.util.Pair;
import tiles.Area;
import tiles.NonRemovableRoadTile;

import java.util.Random;


public class CityArea extends Pane {

    private static final Random RG = new Random();

    private final int[][] directions = new int[][] {
        { 0,1,3,2 }, {0,3,1,2}, {1,3,0,2}, {1,0,3,2}, {3,0,1,2}, {3,1,0,2}
    };

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
