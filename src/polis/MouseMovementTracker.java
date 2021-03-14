package polis;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;

public class MouseMovementTracker extends Pane {

    private Polygon tile = new Polygon(
            0, 0,
            64 * 1, 0.5 * 64 * 1,
            0, 64 * 1,
            -64 * 1, 0.5 * 64 * 1
    );

//https://www.tutorialspoint.com/javafx/javafx_event_handling.htm
//mouse events
    public MouseMovementTracker(){
        this.setPrefWidth(64 * 2 * 32);
        this.setPrefHeight(64 * 32);
        tile.setFill(Color.TRANSPARENT);
        tile.setStroke(Color.BLACK);
        tile.setStrokeWidth(5);
        tile.setTranslateX(64 * (32 - 2 + 1));
        tile.setTranslateY(64 * (2 + 1) / 2);
        tile.setVisible(false);
        this.getChildren().add(tile);
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                int x = (int) Math.round(e.getX());
                int y = (int) Math.round(e.getY());
                tile.setTranslateX(x);
                tile.setTranslateY(y);
                tile.setVisible(true);
            }
        };
        this.addEventHandler(MouseEvent.MOUSE_MOVED, eventHandler);
    }
}
