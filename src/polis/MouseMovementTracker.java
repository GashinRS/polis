package polis;

import eventHandlers.BigPictureTileHandler;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Pair;
import tiles.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Deze klasse behandelt alle berekeningen voor het bepalen waar de muis zich bevindt op het rooster, en zorgt ervoor
 * dat de juiste cursorblokjes worden weergeven afhankelijk van welke knop is ingedrukt
 */

public class MouseMovementTracker extends Pane {

    private List<RoadTile> roadTileList;
    private List<RoadSelectionDragTile> roadSelectionDragTiles;
    private List<EventHandler<MouseEvent>> eventHandlers;

    private Map<Pair<Integer, Integer>, Tile> tiles = new HashMap<>();

    private final SmallTile selectionTile = new SelectionTile(); //cursorblokje
    private final RoadSelectionTile roadSelectionTile = new RoadSelectionTile(tiles);
    private final BigSelectionTile bigSelectionTile = new BigSelectionTile(tiles);
    private final List<Tile> cursorTiles = List.of(selectionTile, roadSelectionTile, bigSelectionTile);
    private Tile cursorTile;

    private final Map<String, BigPictureTileHandler> bigPictureTileHandlerMap = Map.of(
            "residence", new BigPictureTileHandler("residence", this, tiles, bigSelectionTile),
            "commerce", new BigPictureTileHandler("commerce", this, tiles, bigSelectionTile),
            "industry", new BigPictureTileHandler("industry", this, tiles, bigSelectionTile));

    /**
     * Startcoordinaten van het slepen bij het plaatsen van wegen
     */
    private int rDrag;
    private int kDrag;
    private final CityArea cityArea;

    EventHandler<MouseEvent> roadHandler = mouseEvent -> {
        if (roadSelectionTile.isValid()) {
            placeRoad(rDrag, kDrag, new RoadTile(rDrag, kDrag, roadTileList, tiles));
        }
    };

    EventHandler<MouseEvent> cursorHandler = e -> {
        int r = getR(e);
        int k = getK(e);
        if (r>=0 && r<=32-cursorTile.getCellSize() && k>=0 && k<=32-cursorTile.getCellSize()) {
            setTranslateXY(cursorTile, r, k);
            cursorTile.checkValidity(r, k);
        }
    };

//https://www.tutorialspoint.com/javafx/javafx_event_handling.htm
//mouse events
    public MouseMovementTracker(CityArea cityArea){
        this.cityArea = cityArea;
        roadTileList = new ArrayList<>();
        eventHandlers = new ArrayList<>();
        roadSelectionDragTiles = new ArrayList<>();

        setPrefWidth(64 * 2 * 32);
        setPrefHeight(64 * 32);
        setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        setSelectionMode();
        getChildren().addAll(selectionTile, roadSelectionTile, bigSelectionTile);
        addEventHandler(MouseEvent.MOUSE_MOVED, cursorHandler);

        for (int i=0; i<16; i++){
            placeRoad(i, 14, new NonRemovableRoadTile(i, 14, roadTileList, tiles));
        }
    }

    public void setTranslateXY(Polygon polygon, int r, int k){
        polygon.setTranslateX(64 * (32 - r + k));
        polygon.setTranslateY(64 * (r + k) / 2);
    }

    //voor commerce, industry en residence
    public void setBigPictureTileEventHandler(String bigPictureTileType){
        switchMode(bigSelectionTile, bigPictureTileHandlerMap.get(bigPictureTileType));
        addEventHandler(MouseEvent.MOUSE_CLICKED, bigPictureTileHandlerMap.get(bigPictureTileType));
    }

    public void setBulldozerMode() {
        selectionTile.setStroke(Color.RED);
        switchMode(selectionTile, bulldozerHandler);
        addEventHandler(MouseEvent.MOUSE_CLICKED, bulldozerHandler);
    }

    EventHandler<MouseEvent> bulldozerHandler = mouseEvent -> {
        Tile tile = tiles.get(new Pair<>(getR(mouseEvent), getK(mouseEvent)));
        if (tile != null) {
            tile.removeThis(this);
        }
    };

    public void setSelectionMode(){
        selectionTile.setStroke(Color.PURPLE);
        switchMode(selectionTile, mouseEvent -> {});
    }

    public void setRoadMode(){
        switchMode(roadSelectionTile, e -> {});
        setOnMousePressed(pressHandler);
        setOnMouseReleased(roadHandler);
        setOnDragDetected(e -> startFullDrag());
        setOnMouseDragged(roadSelectionHandler);
        setOnMouseDragReleased(roadSelectionDragTileRemover);
    }

    //om het startcoordinaat van de drag te krijgen
    EventHandler<MouseEvent> pressHandler = e -> {
            rDrag = Math.max(Math.min(getR(e), 31), 0);
            kDrag = Math.max(Math.min(getK(e), 31), 0);

    };

    EventHandler<MouseEvent> roadSelectionDragTileRemover = new EventHandler<>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            for (RoadSelectionDragTile roadSelectionDragTile : roadSelectionDragTiles) {
                getChildren().remove(roadSelectionDragTile);
                if (roadSelectionDragTile.getColor().equals(Color.rgb(90, 155,255, 0.5))) {
                    placeRoad(roadSelectionDragTile.getR(), roadSelectionDragTile.getK(),
                            new RoadTile(roadSelectionDragTile.getR(), roadSelectionDragTile.getK(), roadTileList, tiles));
                }
            }
            roadSelectionDragTiles.clear();
        }
    };

    EventHandler<MouseEvent> roadSelectionHandler = new EventHandler<>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            int r = getR(mouseEvent);
            int k = getK(mouseEvent);
            for (RoadSelectionDragTile roadSelectionDragTile : roadSelectionDragTiles) {
                getChildren().remove(roadSelectionDragTile);
            }
            roadSelectionDragTiles.clear();

            int min = Math.max(Math.min(r, rDrag), 0);
            int max = Math.min(Math.max(r, rDrag), 31);
            for (int i = min + 1; i < max; i++) {
                placeRoadSelectionDragTile(i, kDrag);
            }

            min = Math.max(Math.min(k, kDrag), 0);
            max = Math.min(Math.max(k, kDrag), 31);
            r = Math.max(Math.min(r, 31), 0);
            int factor = 0;
            if (r == rDrag) {
                factor = 1;
            }

            for (int i = min + factor; i <= max - factor; i++) {
                placeRoadSelectionDragTile(r, i);
            }
            for (RoadSelectionDragTile roadSelectionDragTile : roadSelectionDragTiles) {
                roadSelectionDragTile.checkValidity(roadSelectionDragTile.getR(), roadSelectionDragTile.getK());
            }
        }
    };

    public void placeRoad(int r, int k, RoadTile roadTile){
        if (r>=0 && r<32 && k>=0 && k<32) {
            roadTileList.add(roadTile);

            //fix later
            tiles.put(new Pair<>(r, k), roadTile);

            getChildren().add(roadTile);
            setTranslateXY(roadTile, r, k);
        }
    }

    public void placeRoadSelectionDragTile(int r, int k){
        RoadSelectionDragTile roadSelectionDragTile = new RoadSelectionDragTile(tiles, r, k);
        roadSelectionDragTiles.add(roadSelectionDragTile);
        getChildren().add(roadSelectionDragTile);
        setTranslateXY(roadSelectionDragTile, r, k);
    }

    /**
     * Wordt opgeroepen om te wisselen tussen de verschillende modi wanneer er op een andere knop wordt geklikt.
     */
    public void switchMode(Tile tile, EventHandler<MouseEvent> eventHandler){
        for (Tile cursorTile: cursorTiles){
            cursorTile.setVisible(false);
        }
        cursorTile = tile;
        tile.setVisible(true);
        for (EventHandler<MouseEvent> handler: eventHandlers){
            removeEventHandler(MouseEvent.MOUSE_CLICKED, handler);
        }
        eventHandlers.add(eventHandler);
        setOnMousePressed(e -> {});
        setOnMouseReleased(e -> {});
        setOnDragDetected(e -> {});
        setOnMouseDragged(e -> {});
        setOnMouseDragReleased(e -> {});
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
