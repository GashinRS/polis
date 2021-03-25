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

import java.util.ArrayList;
import java.util.List;

/**
 * Deze klasse behandelt alle berekeningen voor het bepalen waar de muis zich bevindt op het rooster, en zorgt ervoor
 * dat de juiste cursorblokjes worden weergeven afhankelijk van welke knop is ingedrukt
 */

public class MouseMovementTracker extends Pane {

    private List<RoadTile> roadTileList;
    private List<EventHandler<MouseEvent>> eventHandlers;
    private final Tile tile = new Tile(); //cursorblokje

    EventHandler<MouseEvent> roadHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            int r = getR(mouseEvent);
            int k = getK(mouseEvent);
            RoadTile roadTile = new RoadTile(r, k, roadTileList);
            if (!roadTile.isDuplicate()) {
                roadTileList.add(roadTile);
                MouseMovementTracker.super.getChildren().add(roadTile);
                setTranslateXY(roadTile, r, k);
            }
        }
    };

    EventHandler<MouseEvent> cursorHandler = e -> {
        int r = getR(e);
        int k = getK(e);
        if (r>=0 && r<32 && k>=0 && k<32) {
            setTranslateXY(tile, r, k);
        }
    };

//https://www.tutorialspoint.com/javafx/javafx_event_handling.htm
//mouse events
    public MouseMovementTracker(){
        roadTileList = new ArrayList<>();
        eventHandlers = new ArrayList<>();
        this.setPrefWidth(64 * 2 * 32);
        this.setPrefHeight(64 * 32);
        this.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        setSelectionMode();
        this.getChildren().add(tile);
        this.addEventHandler(MouseEvent.MOUSE_MOVED, cursorHandler);
    }

    public void setTranslateXY(Polygon polygon, int r, int k){
        polygon.setTranslateX(64 * (32 - r + k));
        polygon.setTranslateY(64 * (r + k) / 2);
    }

    public void setSelectionMode(){
        removeEventHandlers();
        tile.setFill(Color.TRANSPARENT);
        tile.setStroke(Color.PURPLE);
        tile.setStrokeWidth(5);
    }

    public void setRoadMode(){
        tile.setFill(Color.rgb(90, 155,255, 0.5));
        tile.setStroke(Color.TRANSPARENT);
        tile.setStrokeWidth(0);
        removeEventHandlers();
        eventHandlers.add(roadHandler);
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, roadHandler);
    }

    /**
     * Hiermee worden EventHandlers verwijderd als er op een andere knop wordt geklikt.
     */
    public void removeEventHandlers(){
        for (EventHandler<MouseEvent> eventHandler: eventHandlers){
            this.removeEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
        }
    }

    /**
     * Getters om de coordinaten van de muis om te zetten naar rastercoordinaten
     */
    public int getR(MouseEvent e){
        return (int) Math.round((2*e.getY() - e.getX() + this.getWidth()/2) / (2*64));
    }

    public int getK(MouseEvent e){
        return (int) Math.round((e.getX() + 2*e.getY() - this.getWidth()/2) / (2*64));
    }
}
