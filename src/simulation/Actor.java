package simulation;

import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javafx.util.Pair;
import polis.MouseMovementTracker;
import tiles.bigPictureTile.BigPictureTile;

import java.util.*;

public abstract class Actor extends Circle {

    private final MouseMovementTracker mouseMovementTracker;
    private int r;
    private int k;
    private int age;
    private Pair<Integer, Integer> homeLocation;
    private final Properties engineProperties;
    private BigPictureTile home;
    //deze lijst zal de actor bevatten waarin de huidige actor verandert, wanneer de verandering plaatsvindt
    private final List<Actor> newActors = new ArrayList<>();
    private static final Random RG = new Random();
    //0==ZW, 1==NW, 2==NO, 3==ZO
    //extra element 42 aan toegevoegd om nullpointer exception te vermijden en zodat de actor blijft staan
    //als hij geen enkele van de richtingen op kan
    /**
     * TODO: vervang die 42 best door iets anders dat meer functional is
     */
    private static final int[][] DIRECTIONS = new int[][] {
            { 0,1,3,2,42}, {0,3,1,2,42}, {1,3,0,2,42}, {1,0,3,2,42}, {3,0,1,2,42}, {3,1,0,2,42}
    };
    //wordt gebruikt om random rechts of links te bepalen afhankelijk van de richting van de actor
    private static final int[][][] LEFTRIGHT = new int[][][] {
            {{0, 1, 0, -1}, {0, -1, 0, 1}}, {{-1, 0, 1, 0}, {1, 0, -1, 0}}, {{0, 1, 0, -1}, {0, -1, 0, 1}}, {{-1, 0, 1, 0}, {1, 0, -1, 0}}
    };

    private static final Map<Integer, Integer[]> DIRECTION_MAPPINGS = Map.of(
            0, new Integer[] {-64/4, 64/3}, //ZW
            1, new Integer[] {64/4, 64/3}, //NW
            2, new Integer[] {64/4, 2*(64/3)}, //NO
            3, new Integer[] {-64/4, 2*(64/3)} //ZO
    );

    private static final int [] rco = new int[] {1, 0, -1, 0};
    private static final int [] kco = new int[] {0, -1, 0, 1};
    private int direction;

    public Actor(MouseMovementTracker mouseMovementTracker, int r, int k, Properties engineProperties) {
        super(DIRECTION_MAPPINGS.get(0)[0], DIRECTION_MAPPINGS.get(0)[1], 64/6);
        this.mouseMovementTracker=mouseMovementTracker;
        this.engineProperties=engineProperties;
        setViewOrder(-r-k-1.5);
        mouseMovementTracker.getCityArea().setTranslateXY(this, r, k);
        mouseMovementTracker.getCityArea().getChildren().add(this);
        direction=0;
        this.r=r;
        this.k=k;
    }

    public abstract void act();

    /**
     * Deze methode wordt gebruikt door de simulatiemotor om te kijken of de actor verwijderd moet worden
     */
    public abstract boolean isValid();

    public void move(){
        int option = RG.nextInt(6);
        int index = 0;
        int newDirection = (direction + DIRECTIONS[option][index]) % 4;
        while (mouseMovementTracker.getRoadTiles().get(new Pair<>(r+rco[newDirection], k+kco[newDirection])) == null &&
        index < 4){
            index++;
            newDirection = (direction + DIRECTIONS[option][index]) % 4;
        }
        if (index < 4){
            direction = newDirection;
            r=r+rco[newDirection];
            k=k+kco[newDirection];
            mouseMovementTracker.getCityArea().setTranslateXY(this, r, k);
            setViewOrder(-r-k-1.5);
            setCenterX(DIRECTION_MAPPINGS.get(direction)[0]);
            setCenterY(DIRECTION_MAPPINGS.get(direction)[1]);
        }

    }

    public void setR(int r){
        this.r=r;
    }

    public void setK(int k) {
        this.k=k;
    }

    public int getR() {
        return r;
    }

    public int getK() {
        return k;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public MouseMovementTracker getMouseMovementTracker(){
        return mouseMovementTracker;
    }

    public List<Actor> getNewActor() {
        return newActors;
    }

    public void setNewActor(Actor newActor){
        newActors.add(newActor);
        mouseMovementTracker.getCityArea().getChildren().remove(this);
        newActor.setHome(getHomeLocation().getKey(), getHomeLocation().getValue(), getHome());
        home.replaceResident(this, newActor);
    }

    public void setHome(int r, int k, BigPictureTile home){
        homeLocation = new Pair<>(r, k);
        this.home=home;
    }

    public BigPictureTile getHome(){
        return home;
    }

    public Pair<Integer, Integer> getHomeLocation(){
        return homeLocation;
    }

    public Properties getEngineProperties(){
        return engineProperties;
    }


    public List<BigPictureTile> getLeftAndRightBuildings(){
        int randomInt = RG.nextInt(2);
        List<BigPictureTile> buildingTiles = new ArrayList<>();
        buildingTiles.add(mouseMovementTracker.getBuildingTiles().get(new Pair<>(r+LEFTRIGHT[direction][randomInt][0],
                k+LEFTRIGHT[direction][randomInt][1])));
        buildingTiles.add(mouseMovementTracker.getBuildingTiles().get(new Pair<>(r+LEFTRIGHT[direction][randomInt][2],
                k+LEFTRIGHT[direction][randomInt][3])));
        return buildingTiles;
    }
}
