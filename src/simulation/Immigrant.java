package simulation;

import javafx.scene.paint.Color;
import javafx.util.Pair;
import polis.MouseMovementTracker;
import tiles.RemovableTile;
import tiles.bigPictureTile.BigPictureTile;

public class Immigrant extends Actor {

    private int age;
    private final Region region;
    private boolean residenceFound;

    public Immigrant(MouseMovementTracker mouseMovementTracker, int age, Region region) {
        super(mouseMovementTracker, 0, 14);
        setFill(Color.GREY);
        this.age=age;
        this.region=region;
    }

    @Override
    public void act() {
        int leftRight = getRandomLeftRight();
        BigPictureTile buildingTile1 = getMouseMovementTracker().getBuildingTiles().
                get(new Pair<>(getR()+getLeftright()[getDirection()][leftRight][0], getK()+getLeftright()[getDirection()][leftRight][1]));
        BigPictureTile buildingTile2 = getMouseMovementTracker().getBuildingTiles().
                get(new Pair<>(getR()+getLeftright()[getDirection()][leftRight][2], getK()+getLeftright()[getDirection()][leftRight][3]));
        if ( buildingTile1 != null && buildingTile1.isResidence() && !buildingTile1.isAtMaxCapacity()){
            buildingTile1.addActor(this);
            residenceFound=true;
        } else if (buildingTile2 != null && buildingTile2.isResidence() && !buildingTile2.isAtMaxCapacity()){
            buildingTile2.addActor(this);
            residenceFound=true;
        } else {
            move();
            age -= 1;
        }
    }


    @Override
    public boolean isValid() {
        boolean valid = age > 0;
        if (!valid){
            region.slowDown();
        }
        return valid && !residenceFound;
    }
}
