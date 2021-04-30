package polis;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import simulation.GeneralStatistics;
import simulation.InfoPanel;
import simulation.SimulationEngine;
import tiles.*;
import tiles.buildtingTiles.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;


public class MouseMovementTracker extends Pane {

    private final Map<Pair<Integer, Integer>, RoadTile> roadTiles = new HashMap<>();
    private final List<RoadSelectionTile> roadSelectionDragTiles = new ArrayList<>();
    private final Map<Pair<Integer, Integer>, BuildingTile> buildingTiles = new HashMap<>();

    private final SelectionTile selectionTile = new SelectionTile(this);
    private final RoadSelectionTile roadSelectionTile = new RoadSelectionTile(this);
    private final BigSelectionTile bigSelectionTile = new BigSelectionTile(this);
    private final List<CursorTile> cursorTiles = List.of(selectionTile, roadSelectionTile, bigSelectionTile);
    private CursorTile cursorTile;
    private String buildingTileType;
    //Startcoordinaten van het slepen bij het plaatsen van wegen
    private int rDrag;
    private int kDrag;
    private CityArea cityArea;
    private static final Map<String, BuildingTileFactory> BUILDING_TILE_FACTORY_MAP = Map.of(
            "residence", ResidenceTile::new,
            "commerce", CommerceTile::new,
            "industry", IndustryTile::new
    );
    private Properties engineProperties;
    private final Properties levelsProperties = new Properties();
    private final InfoPanel infoPanel;
    private final GeneralStatistics generalStatistics;

    public MouseMovementTracker(InfoPanel infoPanel, GeneralStatistics generalStatistics) {
        this.infoPanel=infoPanel;
        this.generalStatistics=generalStatistics;
        setPrefSize(64 * 2 * 32, 64 * 32);
        setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        setSelectionMode();
        setOnMouseMoved(e -> {
            int r = Math.max(Math.min(getR(e), 32 - cursorTile.getCellSize()), 0);
            int k = Math.max(Math.min(getK(e), 32 - cursorTile.getCellSize()), 0);
            cursorTile.checkValidity(r, k);
        });
        try (InputStream in = SimulationEngine.class.getResourceAsStream("/polis/levels.properties")){
            levelsProperties.load(in);
        } catch (IOException ie){
            System.err.println("levels properties bestand kon niet gevonden of gelezen worden");
        }
        buildingTileHandler = e -> {
            if (bigSelectionTile.isValid()) {
                BUILDING_TILE_FACTORY_MAP.get(buildingTileType).creatBuildingTile().initialize(this, bigSelectionTile.getR(), bigSelectionTile.getK(), generalStatistics);
            }
        };
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

    public void setbuildingTileEventHandler(String buildingTileType) {
        this.buildingTileType = buildingTileType;
        switchMode(bigSelectionTile, buildingTileHandler);
    }
    private final EventHandler<MouseEvent> buildingTileHandler;


    public void setBulldozerMode() {
        selectionTile.setStroke(Color.RED);
        switchMode(selectionTile, bulldozerHandler);
    }

    private final EventHandler<MouseEvent> bulldozerHandler = e -> {
        Map<Pair<Integer, Integer>, RemovableTile> removableTiles = new HashMap<>(buildingTiles);
        removableTiles.putAll(roadTiles);
        RemovableTile removableTile = removableTiles.get(new Pair<>(getR(e), getK(e)));
        if (removableTile != null){
            removableTile.removeThis();
        }
    };

    /**
     * In SelectionMode wordt het model van het infopaneel ingesteld afhankelijk van waarop de gebruiker klikt.
     * Standaard staat het model ingesteld op de algemene informatie van heel de simulatie, namelijk de klasse
     * GeneralStatistics.
     */

    public void setSelectionMode() {
        selectionTile.setStroke(Color.WHITE);
        switchMode(selectionTile, e -> {
            BuildingTile tile = buildingTiles.get(new Pair<>(getR(e), getK(e)));
            if (tile != null) {
                infoPanel.setModel(tile);
            } else {
                infoPanel.setModel(generalStatistics);
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

    /**
     * EventHandler die wegen plaats door op alle plaatsen waar er een RoadSelectionTile staat er een RoadTile te plaatsen
     */

    private final EventHandler<MouseEvent> roadPlacer = e -> {
        roadSelectionTile.placeRoadIfValid();
        roadSelectionTile.setFill(roadSelectionTile.getRed());
        cityArea.setTranslateXY(roadSelectionTile, Math.max(Math.min(getR(e), 31), 0), Math.max(Math.min(getK(e), 31), 0));

        for (RoadSelectionTile roadSelectionDragTile : roadSelectionDragTiles) {
            roadSelectionDragTile.placeRoadIfValid();
        }
        roadSelectionDragTiles.clear();
    };


    /**
     * EventHandler voor het rondslepen van wegen
     */

    private final EventHandler<MouseEvent> roadDragHandler = e -> {
        int r = getR(e);
        int k = getK(e);
        for (RoadSelectionTile roadSelectionDragTile : roadSelectionDragTiles) {
            cityArea.getChildren().remove(roadSelectionDragTile);
        }
        roadSelectionDragTiles.clear();

        int min = Math.max(Math.min(r, rDrag), 0);
        int max = Math.min(Math.max(r, rDrag), 31);
        for (int i = min + 1; i < max; i++) {
            roadSelectionDragTiles.add(new RoadSelectionDragTile(i, kDrag, this));
        }

        min = Math.max(Math.min(k, kDrag), 0);
        max = Math.min(Math.max(k, kDrag), 31);
        r = Math.max(Math.min(r, 31), 0);
        //factor == 0 wanneer r != rDrag, anders factor == 1
        int factor = Math.abs(Math.abs(Integer.signum(r - rDrag)) - 1);

        for (int i = min + factor; i <= max - factor; i++) {
            roadSelectionDragTiles.add(new RoadSelectionDragTile(r, i, this));
        }
    };

    /**
     * Wordt opgeroepen om te wisselen tussen de verschillende modi wanneer er op een andere knop wordt geklikt.
     */

    private void switchMode(CursorTile tile, EventHandler<MouseEvent> eventHandler) {
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

    private int getR(MouseEvent e) {
        return (int) Math.round((2 * e.getY() - e.getX() + this.getWidth() / 2) / (2 * 64));
    }

    private int getK(MouseEvent e) {
        return (int) Math.round((e.getX() + 2 * e.getY() - this.getWidth() / 2) / (2 * 64));
    }


    public Map<Pair<Integer, Integer>, BuildingTile> getBuildingTiles() {
        return buildingTiles;
    }

    public Map<Pair<Integer, Integer>, RoadTile> getRoadTiles() {
        return roadTiles;
    }

    public void setEngineProperties(Properties engineProperties){
        this.engineProperties =engineProperties;
    }

    public Properties getEngineProperties(){
        return engineProperties;
    }

    public Properties getLevelsProperties(){
        return levelsProperties;
    }
}
