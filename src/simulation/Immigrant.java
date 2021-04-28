package simulation;

import javafx.scene.paint.Color;
import javafx.util.Pair;
import polis.MouseMovementTracker;
import tiles.RemovableTile;
import tiles.bigPictureTile.BigPictureTile;

import java.util.List;
import java.util.Properties;

public class Immigrant extends Actor {

    private final Region region;
    private boolean residenceFound;

    public Immigrant(MouseMovementTracker mouseMovementTracker, int age, Region region, Properties engineProperties) {
        super(mouseMovementTracker, 0, 14, engineProperties);
        setFill(Color.GREY);
        setAge(age);
        this.region=region;
    }

    @Override
    public void act() {
        List<BigPictureTile> leftAndRightBuildings = getLeftAndRightBuildings();
        BigPictureTile buildingTile1 = leftAndRightBuildings.get(0);
        BigPictureTile buildingTile2 = leftAndRightBuildings.get(1);
        if ( buildingTile1 != null && buildingTile1.isResidence() && !buildingTile1.isAtMaxCapacity()){
            setResidence(buildingTile1);
        } else if (buildingTile2 != null && buildingTile2.isResidence() && !buildingTile2.isAtMaxCapacity()){
            setResidence(buildingTile2);
        } else {
            move();
            setAge(getAge()-1);
        }
    }

    public void setResidence(BigPictureTile residence){
        Sleeper sleeper = new Sleeper(getMouseMovementTracker(), getR(), getK(), getEngineProperties(), residence);
        residence.addActor(sleeper);
        setNewActor(sleeper);
        removeThis();
        residenceFound=true;
    }


    @Override
    public boolean isValid() {
        boolean isAgeValid = getAge() > 0;
        if (!isAgeValid){
            region.slowDown();
        }
        return isAgeValid && !residenceFound;
    }
}
