package tiles.bigPictureTile;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;
import polis.MouseMovementTracker;
import simulation.GeneralStatistics;
import simulation.InfoPanel;
import simulation.InfoPanelModel;
import simulation.actors.Actor;
import tiles.RemovableTile;
import tiles.Tile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * bug met imageview verwijderen
 * TODO: refractoren naar BuildingTile
 */

public abstract class BigPictureTile extends Tile implements RemovableTile, Observable, InfoPanelModel {

    private double capacity;
    private double minimalCapcity;
    private MouseMovementTracker mouseMovementTracker;
    private ImageView imageView;
    private List<Image> images;
    private final String type;
    private final List<Actor> actors = new ArrayList<>();
    private static final Map<String, String> INITIAL_CAPACITIES = Map.of(
            "residence", "residential.capacity.initial",
            "commerce", "commercial.capacity.initial",
            "industry", "industrial.capacity.initial");
    private static final Map<String, String> MINIMAL_CAPACITIES = Map.of(
            "residence", "residential.capacity.minimal",
            "commerce", "commercial.capacity.minimal",
            "industry", "industrial.capacity.minimal");
    private static final Map<String, List<String>> LEVEL_REQUIREMENTS = Map.of(
            "residence", List.of("residential.level1to2", "residential.level2to1", "residential.level2to3", "residential.level3to2"),
            "commerce", List.of("commercial.level1to2", "commercial.level2to1", "commercial.level2to3", "commercial.level3to2"),
            "industry", List.of("industrial.level1to2", "industrial.level2to1", "industrial.level2to3", "industrial.level3to2")
    );
    private double[] upgradeThresholds;
    private double[] downgradeThresholds;
    private double upgradeThreshold;
    private double downgradeThreshold;
    private int level;
    private boolean existing;
    private GeneralStatistics generalStatistics;

    public BigPictureTile(String type) {
        super(2);
        this.type=type;
        level=0;
        existing=true;
    }

    @Override
    public void removeThis() {
        for (int i = 0; i < 4; i++) {
            mouseMovementTracker.getBuildingTiles().values().remove(this);
        }

        //onduidelijke bug
        //imageView.setVisible(false);
        mouseMovementTracker.getCityArea().getChildren().remove(imageView);
        existing=false;
        changeGeneralStatistics(actors.size(), 0, capacity, 0);
    }

    public boolean isExisting(){
        return existing;
    }

    public void upgrade() {
        imageView.setImage(images.get(level));
        double width = images.get(level).getWidth();
        double height = images.get(level).getHeight();
        //imageView.setImage(images.get(level));
        imageView.setX(-0.5 * width);
        imageView.setY(0.5 * width - height);
    }

    public void downgrade(){
        level -= 1;
        upgrade();
    }

    public void activate(){
        level = 1;
        upgrade();
    }

    public boolean isResidence(){
        return false;
    }

    public boolean isCommerce(){
        return false;
    }

    public boolean isIndustry(){
        return false;
    }

    public GeneralStatistics getGeneralStatistics(){
        return generalStatistics;
    }

    public abstract void changeGeneralStatistics(int oldValue, int newValue, double oldValue2, double newValue2);

    public void initialize(MouseMovementTracker mouseMovementTracker, int r, int k, GeneralStatistics generalStatistics){
        this.mouseMovementTracker=mouseMovementTracker;
        this.generalStatistics=generalStatistics;
        try (InputStream in0 = this.getClass().getResourceAsStream("/polis/tiles/" + type + "-0.png");
             InputStream in1 = this.getClass().getResourceAsStream("/polis/tiles/" + type + "-1.png");
             InputStream in2 = this.getClass().getResourceAsStream("/polis/tiles/" + type + "-2.png");
             InputStream in3 = this.getClass().getResourceAsStream("/polis/tiles/" + type + "-3.png")) {
            images = List.of(new Image(in0), new Image(in1), new Image(in2), new Image(in3));
            imageView = new ImageView(images.get(0));
            //ivm NPE imageview/vieworder
            imageView.setMouseTransparent(true);

            upgrade();
            mouseMovementTracker.getCityArea().getChildren().add(imageView);
            imageView.setTranslateX(64 * (32 - r + k));
            imageView.setTranslateY(64 * (r + k) / 2);
            imageView.setViewOrder(-r - k - 2);
        } catch (Exception ex) {
            System.err.println("bestand niet gevonden");
        }
        capacity=Double.parseDouble(mouseMovementTracker.getEngineProperties().getProperty(INITIAL_CAPACITIES.get(type)));
        minimalCapcity=Double.parseDouble(mouseMovementTracker.getEngineProperties().getProperty(MINIMAL_CAPACITIES.get(type)));
        //eventueel met een 2d array in for loop werken
        mouseMovementTracker.getBuildingTiles().put(new Pair<>(r, k), this);
        mouseMovementTracker.getBuildingTiles().put(new Pair<>(r + 1, k), this);
        mouseMovementTracker.getBuildingTiles().put(new Pair<>(r, k + 1), this);
        mouseMovementTracker.getBuildingTiles().put(new Pair<>(r + 1, k + 1), this);
        mouseMovementTracker.getCityArea().setTranslateXY(this, r, k);
        upgradeThresholds = new double[] {Double.parseDouble(mouseMovementTracker.getLevelsProperties().getProperty(LEVEL_REQUIREMENTS.get(type).get(0))),
                Double.parseDouble(mouseMovementTracker.getLevelsProperties().getProperty(LEVEL_REQUIREMENTS.get(type).get(2))),
        Double.POSITIVE_INFINITY};
        downgradeThresholds = new double[] {Double.NEGATIVE_INFINITY, Double.parseDouble(mouseMovementTracker.getLevelsProperties().getProperty(LEVEL_REQUIREMENTS.get(type).get(1))),
                Double.parseDouble(mouseMovementTracker.getLevelsProperties().getProperty(LEVEL_REQUIREMENTS.get(type).get(3)))};
        upgradeThreshold = upgradeThresholds[0];
        downgradeThreshold = downgradeThresholds[0];
    }

    public void changeCapacity(double factor){
        double oldCapacity = capacity;
        capacity = Math.max(minimalCapcity, capacity*factor);
        changeGeneralStatistics(actors.size(), actors.size(), oldCapacity, capacity);
        if (capacity >= upgradeThreshold){
            upgradeThreshold = upgradeThresholds[level];
            downgradeThreshold = downgradeThresholds[level];
            level++;
            upgrade();
        } else if (capacity < downgradeThreshold){
            downgrade();
            downgradeThreshold = downgradeThresholds[level-1];
            upgradeThreshold = upgradeThresholds[level-1];
        }
    }

    public double getCapacity(){
        return capacity;
    }

    public double getMinimalCapcity(){
        return minimalCapcity;
    }

    public void addActor(Actor actor){
        actors.add(actor);
        changeGeneralStatistics(actors.size()-1, actors.size(), capacity, capacity);
    }

    public void removeActor(Actor actor){
        actors.remove(actor);
        changeGeneralStatistics(actors.size(), actors.size()-1, capacity, capacity);
    }

    public boolean isAtMaxCapacity(){
        return actors.size() >= Math.floor(capacity);
    }

    public List<Actor> getActors(){
        return actors;
    }

    public MouseMovementTracker getMouseMovementTracker() {
        return mouseMovementTracker;
    }

    @Override
    public String getType(){
        return type;
    }

    public boolean isActivated(){
        return level != 0;
    }

    //wordt enkel gebruikt in CommerceTile
    public boolean canAcceptCustomer(){
        return false;
    }

    private List<InvalidationListener> listeners = new ArrayList<>();

    public void fireInvalidationEvent(){
        for (InvalidationListener listener:listeners){
            listener.invalidated(this);
        }
    }

    /**
     * Dit wordt gebruikt door het infopaneel om de statistieken op te halen
     */
    @Override
    public List<Number> getStats(){
        return List.of(actors.size(), capacity);
    }

    @Override
    public void addListener(InvalidationListener invalidationListener) {
        listeners.add(invalidationListener);
        fireInvalidationEvent();
    }

    @Override
    public void removeListener(InvalidationListener invalidationListener) {
        listeners.remove(invalidationListener);
    }
}
