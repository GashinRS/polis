package polis;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * Deze klasse behandelt alle berekeningen voor het bepalen waar de muis zich bevindt op het rooster, en zorgt ervoor
 * dat de juiste cursorblokjes worden weergeven afhankelijk van welke knop is ingedrukt
 */

public class MouseMovementTracker extends Pane {

    private final Polygon tile = new Polygon(
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
        this.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        tile.setFill(Color.TRANSPARENT);
        tile.setStroke(Color.PURPLE);
        tile.setStrokeWidth(5);
        this.getChildren().add(tile);
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                int r = (int) Math.round((2*e.getY() - e.getX() + 750) / (2*64)) +10;
                int k = (int) Math.round((e.getX() + 2*e.getY() - 750) / (2*64)) -10;
                if (r>=0 && r<32 && k>=0 && k<32) {
                    tile.setTranslateX(64 * (32 - r + k));
                    tile.setTranslateY(64 * (r + k) / 2);
                }
            }
        };
        this.addEventHandler(MouseEvent.MOUSE_MOVED, eventHandler);
    }
}
