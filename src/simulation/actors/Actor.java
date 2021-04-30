package simulation.actors;

import javafx.scene.shape.Circle;
import javafx.util.Pair;
import polis.MouseMovementTracker;
import tiles.buildtingTiles.BuildingTile;

import java.util.*;

public abstract class Actor extends Circle {

    private final MouseMovementTracker mouseMovementTracker;
    private int r;
    private int k;
    private int age;
    private Pair<Integer, Integer> homeLocation;
    private final Properties engineProperties;
    private BuildingTile home;

    //deze lijst zal de actor bevatten waarin de huidige actor verandert, wanneer de verandering plaatsvindt
    private final List<Actor> newActors = new ArrayList<>();

    private static final Random RG = new Random();

    //0==ZW, 1==NW, 2==NO, 3==ZO, 4 == geen verplaatsing
    private static final int[][] DIRECTIONS = new int[][] {
            { 0,1,3,2,4}, {0,3,1,2,4}, {1,3,0,2,4}, {1,0,3,2,4}, {3,0,1,2,4}, {3,1,0,2,4}
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

    //wordt gebruikt om coordinaten te krijgen van de blokjes die rond de actor liggen
    private static final int [] RCO = new int[] {1, 0, -1, 0};
    private static final int [] KCO = new int[] {0, -1, 0, 1};
    private int direction;

    /**
     * Bij het aanmaken van een actor wordt altijd een richting doorgegeven zodat de nieuwe actor niet achteruit zal
     * gaan ten op zichte van de actor die hij hiervoor was.
     */

    public Actor(MouseMovementTracker mouseMovementTracker, int r, int k, Properties engineProperties, int direction) {
        super(DIRECTION_MAPPINGS.get(direction)[0], DIRECTION_MAPPINGS.get(direction)[1], 64/6);
        this.mouseMovementTracker=mouseMovementTracker;
        this.engineProperties=engineProperties;
        setViewOrder(-r-k-1.5);
        mouseMovementTracker.getCityArea().setTranslateXY(this, r, k);
        mouseMovementTracker.getCityArea().getChildren().add(this);
        this.direction=direction;
        this.r=r;
        this.k=k;
    }

    public int getDirection(){
        return direction;
    }

    /**
     * Deze methode wordt elke tick (in de simulatiemotor) uitgevoerd door alle geldige actors
     */

    public abstract void act();

    /**
     * Deze methode wordt gebruikt door de simulatiemotor om te kijken of de actor verwijderd moet worden.
     * In eerste instantie is dit wanneer de leeftijd 0 wordt, maar sommige actors hebben ook nog bijkomende voorwaarden
     * voor wanneer ze verwijderd moeten worden
     */

    public abstract boolean isValid();

    /**
     * Deze methode zorgt ervoor dat een actor een random richting zal kiezen om in te bewegen, waarbij hij enkel naar
     * achter gaat als het niet anders kan, en als dat ook niet kan blijft hij staan.
     */

    private void move(){
        int option = RG.nextInt(6);
        int index = 0;
        int newDirection = (direction + DIRECTIONS[option][index]) % 4;
        while (mouseMovementTracker.getRoadTiles().get(new Pair<>(r+RCO[newDirection], k+KCO[newDirection])) == null &&
        index < 4){
            index++;
            newDirection = (direction + DIRECTIONS[option][index]) % 4;
        }
        if (index < 4){
            direction = newDirection;
            r=r+RCO[newDirection];
            k=k+KCO[newDirection];
            mouseMovementTracker.getCityArea().setTranslateXY(this, r, k);
            setViewOrder(-r-k-1.5);
            setCenterX(DIRECTION_MAPPINGS.get(direction)[0]);
            setCenterY(DIRECTION_MAPPINGS.get(direction)[1]);
        }

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

    /**
     * Deze methode wordt gebruikt een een oude actor te vervangen door een nieuwe, bv wanneer een immigrant een slaper wordt.
     */

    public void setNewActor(Actor newActor){
        newActors.add(newActor);
        mouseMovementTracker.getCityArea().getChildren().remove(this);
        newActor.setHome(getHomeLocation().getKey(), getHomeLocation().getValue(), getHome());
        home.getActors().set(home.getActors().indexOf(this), newActor);
    }

    public void setHome(int r, int k, BuildingTile home){
        homeLocation = new Pair<>(r, k);
        this.home=home;
    }

    public BuildingTile getHome(){
        return home;
    }

    public Pair<Integer, Integer> getHomeLocation(){
        return homeLocation;
    }

    public Properties getEngineProperties(){
        return engineProperties;
    }

    /**
     * Deze methode wordt gebruikt om random rechts en links te checken of er een gebouw is.
     */

    public List<BuildingTile> getLeftAndRightBuildings(){
        int randomInt = RG.nextInt(2);
        List<BuildingTile> buildingTiles = new ArrayList<>();
        buildingTiles.add(mouseMovementTracker.getBuildingTiles().get(new Pair<>(r+LEFTRIGHT[direction][randomInt][0],
                k+LEFTRIGHT[direction][randomInt][1])));
        buildingTiles.add(mouseMovementTracker.getBuildingTiles().get(new Pair<>(r+LEFTRIGHT[direction][randomInt][2],
                k+LEFTRIGHT[direction][randomInt][3])));
        return buildingTiles;
    }

    public void continueSearching(){
        move();
        age -= 1;
    }
}
