package simulation;

import javafx.scene.paint.Color;
import polis.MouseMovementTracker;
import tiles.bigPictureTile.BigPictureTile;

import java.util.List;
import java.util.Properties;

public class JobSeeker extends Actor{

    private boolean workFound;

    public JobSeeker(MouseMovementTracker mouseMovementTracker, int r, int k, Properties engineProperties) {
        super(mouseMovementTracker, r, k, engineProperties);
        setFill(Color.PERU);
        setAge(Integer.parseInt(engineProperties.getProperty("jobseeker.age")));
    }

    @Override
    public void act() {
        List<BigPictureTile> leftAndRightBuildings = getLeftAndRightBuildings();
        BigPictureTile buildingTile1 = leftAndRightBuildings.get(0);
        BigPictureTile buildingTile2 = leftAndRightBuildings.get(1);
        if (buildingTile1 != null && !buildingTile1.isResidence() && !buildingTile1.isAtMaxCapacity()){
            checkWorkplaceType(buildingTile1);
        } else if (buildingTile2 != null && !buildingTile2.isResidence() && !buildingTile2.isAtMaxCapacity()){
            checkWorkplaceType(buildingTile2);
        } else {
            move();
            setAge(getAge()-1);
        }
    }

    public void checkWorkplaceType(BigPictureTile workplace){
        if (workplace.isCommerce()){
            setWorkplace(workplace, new Trader(getMouseMovementTracker(), getR(), getK(), getEngineProperties()));
        } else {
            setWorkplace(workplace, new Worker(getMouseMovementTracker(), getR(), getK(), getEngineProperties()));
        }
    }

    public void setWorkplace(BigPictureTile workplace, Actor employee){
        employee.setHome(getR(), getK(), getHome());
        workplace.addActor(employee);
        setNewActor(employee);
        removeThis();
        getHome().changeCapacity(Double.parseDouble(getEngineProperties().getProperty("factor.job.found")));
        workFound=true;
    }

    @Override
    public boolean isValid() {
        boolean isAgeValid = getAge() > 0;
        if (!isAgeValid){
            getHome().changeCapacity(Double.parseDouble(getEngineProperties().getProperty("factor.job.not.found")));
        }
        return isAgeValid && !workFound;
    }
}
