package tiles.bigPictureTile;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;
import polis.MouseMovementTracker;
import simulation.Actor;
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

public abstract class BigPictureTile extends Tile implements RemovableTile {

    private double capacity;
    private double minimalCapcity;
    private boolean activated;
    private int imageNumber;
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

    public BigPictureTile(String type) {
        super(2);
        this.type=type;
        imageNumber = 0;
    }

    @Override
    public void removeThis() {
        for (int i = 0; i < 4; i++) {
            mouseMovementTracker.getBuildingTiles().values().remove(this);
        }

        //onduidelijke bug
        //imageView.setVisible(false);
        mouseMovementTracker.getCityArea().getChildren().remove(imageView);
    }

    /**
     * TODO modulo operator mag volgens mij weg en het zou nog steeds moeten werken
     * TODO ook nog de downgrade wat cleaner maken
     */

    public void upgrade() {
        imageView.setImage(images.get(imageNumber % 4));
        double width = images.get(imageNumber % 4).getWidth();
        double height = images.get(imageNumber % 4).getHeight();
        imageView.setImage(images.get(imageNumber % 4));
        imageView.setX(-0.5 * width);
        imageView.setY(0.5 * width - height);
        imageNumber++;
        activated = true;
    }

    public void downgrade(){
        imageNumber -= 2;
        upgrade();
        imageNumber++;
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


    public void initialize(MouseMovementTracker mouseMovementTracker, int r, int k){
        this.mouseMovementTracker=mouseMovementTracker;
        try (InputStream in0 = this.getClass().getResourceAsStream("/polis/tiles/" + type + "-0.png");
             InputStream in1 = this.getClass().getResourceAsStream("/polis/tiles/" + type + "-1.png");
             InputStream in2 = this.getClass().getResourceAsStream("/polis/tiles/" + type + "-2.png");
             InputStream in3 = this.getClass().getResourceAsStream("/polis/tiles/" + type + "-3.png")) {
            images = List.of(new Image(in0), new Image(in1), new Image(in2), new Image(in3));
            imageView = new ImageView(images.get(0));
            //ivm NPE imageview/vieworder
            imageView.setMouseTransparent(true);

            upgrade();
            activated=false;
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
    }

    public void changeCapacity(double factor){
        capacity = Math.max(minimalCapcity, capacity*factor);
    }

    public double getCapacity(){
        return capacity;
    }

    public double getMinimalCapcity(){
        return minimalCapcity;
    }

    /**
     * TODO die == 1 wegwerken als er tijd is
     */
    public void addActor(Actor actor){
        actors.add(actor);
        if (actors.size() == Double.parseDouble(mouseMovementTracker.getLevelsProperties().getProperty(LEVEL_REQUIREMENTS.get(type).get(0)))){
            upgrade();
        } else if (actors.size() == Double.parseDouble(mouseMovementTracker.getLevelsProperties().getProperty(LEVEL_REQUIREMENTS.get(type).get(2)))) {
            upgrade();
        }
//        } else if (!activated){
//            upgrade();
//        }
    }

    public void removeActor(Actor actor){
        actors.remove(actor);
        if (actors.size() == Double.parseDouble(mouseMovementTracker.getLevelsProperties().getProperty(LEVEL_REQUIREMENTS.get(type).get(1)))){
            downgrade();
        } else if (actors.size() == Double.parseDouble(mouseMovementTracker.getLevelsProperties().getProperty(LEVEL_REQUIREMENTS.get(type).get(3)))){
            downgrade();
        }
    }

    public boolean isAtMaxCapacity(){
        return actors.size() >= capacity;
    }

    public List<Actor> getActors(){
        return actors;
    }

    public MouseMovementTracker getMouseMovementTracker() {
        return mouseMovementTracker;
    }

    public boolean isActivated(){
        return activated;
    }

    //wordt enkel gebruikt in ResidenceTile
    public void replaceResident(Actor oldResident, Actor newResident){}

    //wordt enkel gebruikt in CommerceTile
    public boolean canAcceptCustomer(){
        return false;
    }
}
