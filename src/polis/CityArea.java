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

    private MouseMovementTracker mouseMovementTracker;

    public CityArea(MouseMovementTracker mouseMovementTracker){
        this.setPrefWidth(64 * 2 * 32);
        this.setPrefHeight(64 * 32);
        this.setFocusTraversable(true);

        Area area = new Area();
        area.setTranslateX(64 * 32);
        area.setTranslateY(0);

        this.getChildren().add(area);

        this.mouseMovementTracker=mouseMovementTracker;
        getChildren().add(mouseMovementTracker);
        mouseMovementTracker.setCityArea(this);
        for (int i = 0; i < 16; i++) {
            getChildren().add(new NonRemovableRoadTile(i, 14, mouseMovementTracker));
        }

        int r = 0;
        int k = 14;
        int [] rco = new int[] {1, 0, -1, 0};
        int [] kco = new int[] {0, -1, 0, 1};

//        Circle circle = new Circle(0, 64/2, 64/6, Color.GREY);
//        circle.setViewOrder(-r -k -1.5);
//        getChildren().add(circle);
//        setTranslateXY(circle, r, k);
////        int direction = 0;
////        for (int i=0; i<500; i++){
////            int option = RG.nextInt(6);
////            int index = 0;
////            int newDirection = (direction + directions[option][index]) % 4;
////            int j = 0;
////            while (mouseMovementTracker.getRoadTiles().get(new Pair<>(r+rco[j], k+kco[j])) == null) {
////                index ++;
////                newDirection = (direction + directions[option][index]) % 4;
////                j++;
////            }
////            direction = newDirection;
////            setTranslateXY(circle, r+rco[j], k+kco[j]);
////        }
//        for (int i=1; i<14; i++){
//            setTranslateXY(circle, i, 14);
//        }
    }


    public void setTranslateXY(Node node, int r, int k) {
        node.setTranslateX(64 * (32 - r + k));
        node.setTranslateY(64 * (r + k) / 2);
    }

}
