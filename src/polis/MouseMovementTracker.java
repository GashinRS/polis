package polis;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import simulation.Region;
import simulation.SimulationEngine;
import tiles.*;
import tiles.bigPictureTile.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MouseMovementTracker extends Pane implements Observable {

    private Map<Pair<Integer, Integer>, RoadTile> roadTiles = new HashMap<>();
    private List<InvalidationListener> roadSelectionDragTiles = new ArrayList<>();
    private Map<Pair<Integer, Integer>, RemovableTile> tiles = new HashMap<>();

    private final SelectionTile selectionTile = new SelectionTile(this);
    private final RoadSelectionTile roadSelectionTile = new RoadSelectionTile(this);
    private final BigSelectionTile bigSelectionTile = new BigSelectionTile(this);
    private final List<CursorTile> cursorTiles = List.of(selectionTile, roadSelectionTile, bigSelectionTile);
    private CursorTile cursorTile;
    private String bigPictureTileType;
    //Startcoordinaten van het slepen bij het plaatsen van wegen
    private int rDrag;
    private int kDrag;
    private CityArea cityArea;
//    private Region region;
    private static final Map<String, BigPictureTileFactory> bigPictureTileFactories = Map.of(
            "residence", ResidenceTile::new,
            "commerce", CommerceTile::new,
            "industry", IndustryTile::new
    );

    public MouseMovementTracker() {
        setPrefSize(64 * 2 * 32, 64 * 32);
        setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        setSelectionMode();
        setOnMouseMoved(e -> {
            int r = Math.max(Math.min(getR(e), 32 - cursorTile.getCellSize()), 0);
            int k = Math.max(Math.min(getK(e), 32 - cursorTile.getCellSize()), 0);
            cursorTile.checkValidity(r, k);
        });
    }

    public void setCityArea(CityArea cityArea){
        this.cityArea=cityArea;
        cityArea.getChildren().addAll(selectionTile, roadSelectionTile, bigSelectionTile);
    }

    public CityArea getCityArea(){
        return cityArea;
    }

    /**
     * Stelt de modus in voor commerce, industry en residence
     */
    public void setBigPictureTileEventHandler(String bigPictureTileType) {
        this.bigPictureTileType = bigPictureTileType;
        switchMode(bigSelectionTile, bigPictureTileHandler);
    }

    /**
     * new hier wegwerken
     */
    EventHandler<MouseEvent> bigPictureTileHandler = e -> {
        if (bigSelectionTile.isValid()) {
            BigPictureTile bigPictureTile = bigPictureTileFactories.get(bigPictureTileType).createBigPictureTile();
            bigPictureTile.setMouseMovementTracker(this);
            bigPictureTile.setR(bigSelectionTile.getR());
            bigPictureTile.setK(bigSelectionTile.getK());
            bigPictureTile.initialize();
        }
    };

    public void setBulldozerMode() {
        selectionTile.setStroke(Color.RED);
        switchMode(selectionTile, bulldozerHandler);
    }

    EventHandler<MouseEvent> bulldozerHandler = e -> {
        RemovableTile tile = tiles.get(new Pair<>(getR(e), getK(e)));
        if (tile != null) {
            tile.removeThis();
        }
    };

    public void setSelectionMode() {
        selectionTile.setStroke(Color.WHITE);
        switchMode(selectionTile, e -> {
            RemovableTile tile = tiles.get(new Pair<>(getR(e), getK(e)));
            if (tile != null) {
                tile.upgrade();
            }
        });
    }

    public void setRoadMode() {
        switchMode(roadSelectionTile, null);
        setOnMousePressed(e -> {
            rDrag = Math.max(Math.min(getR(e), 31), 0);
            kDrag = Math.max(Math.min(getK(e), 31), 0);
        });
        setOnDragDetected(e -> startFullDrag());
        setOnMouseDragged(roadDragHandler);
        setOnMouseReleased(roadPlacer);
    }

    EventHandler<MouseEvent> roadPlacer = e -> {
        roadSelectionTile.invalidated(this);
        roadSelectionTile.setFill(roadSelectionTile.getRed());
        cityArea.setTranslateXY(roadSelectionTile, Math.max(Math.min(getR(e), 31), 0), Math.max(Math.min(getK(e), 31), 0));

        for (InvalidationListener roadSelectionDragTile : roadSelectionDragTiles) {
            roadSelectionDragTile.invalidated(this);
        }
        roadSelectionDragTiles.clear();
    };

    EventHandler<MouseEvent> roadDragHandler = e -> {
        int r = getR(e);
        int k = getK(e);
        for (InvalidationListener roadSelectionDragTile : roadSelectionDragTiles) {
            cityArea.getChildren().remove(roadSelectionDragTile);
        }
        roadSelectionDragTiles.clear();

        int min = Math.max(Math.min(r, rDrag), 0);
        int max = Math.min(Math.max(r, rDrag), 31);
        for (int i = min + 1; i < max; i++) {
//            roadSelectionDragTiles.add(new RoadSelectionDragTile(i, kDrag, this));
            addListener(new RoadSelectionDragTile(i, kDrag, this));
        }

        min = Math.max(Math.min(k, kDrag), 0);
        max = Math.min(Math.max(k, kDrag), 31);
        r = Math.max(Math.min(r, 31), 0);
        //factor == 0 wanneer r != rDrag, anders factor == 1
        int factor = Math.abs(Math.abs(Integer.signum(r - rDrag)) - 1);

        for (int i = min + factor; i <= max - factor; i++) {
//            roadSelectionDragTiles.add(new RoadSelectionDragTile(r, i, this));
            addListener(new RoadSelectionDragTile(r, i, this));
        }
    };

    /**
     * Wordt opgeroepen om te wisselen tussen de verschillende modi wanneer er op een andere knop wordt geklikt.
     */
    public void switchMode(CursorTile tile, EventHandler<MouseEvent> eventHandler) {
        for (CursorTile cursorTile : cursorTiles) {
            cursorTile.setVisible(false);
        }
        cursorTile = tile;
        cursorTile.setVisible(true);
        setOnMouseClicked(eventHandler);
        setOnMousePressed(null);
        setOnMouseReleased(null);
        setOnDragDetected(null);
        setOnMouseDragged(null);
        setOnMouseDragReleased(null);
    }

    /**
     * Getters om de coordinaten van de muis om te zetten naar rastercoordinaten
     */
    public int getR(MouseEvent e) {
        return (int) Math.round((2 * e.getY() - e.getX() + this.getWidth() / 2) / (2 * 64));
    }

    public int getK(MouseEvent e) {
        return (int) Math.round((e.getX() + 2 * e.getY() - this.getWidth() / 2) / (2 * 64));
    }

    public Map<Pair<Integer, Integer>, RemovableTile> getTiles() {
        return tiles;
    }

    public Map<Pair<Integer, Integer>, RoadTile> getRoadTiles() {
        return roadTiles;
    }


    //is listener patroon hier wel het beste en zo ja verbeteren
    @Override
    public void addListener(InvalidationListener invalidationListener) {
        roadSelectionDragTiles.add(invalidationListener);
    }

    @Override
    public void removeListener(InvalidationListener invalidationListener) {
        roadSelectionDragTiles.remove(invalidationListener);
    }
}
