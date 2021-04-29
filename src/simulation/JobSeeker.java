package simulation;

import javafx.scene.paint.Color;
import polis.MouseMovementTracker;
import tiles.bigPictureTile.BigPictureTile;
import tiles.bigPictureTile.CommerceTile;

import java.util.List;
import java.util.Properties;

public class JobSeeker extends Actor{

    private boolean workFound;

    public JobSeeker(MouseMovementTracker mouseMovementTracker, int r, int k, Properties engineProperties, int direction) {
        super(mouseMovementTracker, r, k, engineProperties, direction);
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
            continueSearching();
        }
    }

    public void checkWorkplaceType(BigPictureTile workplace){
        if (workplace.isCommerce()){
            CommerceTile shop = (CommerceTile) workplace;
            if (!shop.isAtMaxJobCapacity()) {
                if (!shop.isActivated()){
                    shop.activate();
                }
                Trader trader = new Trader(getMouseMovementTracker(), getR(), getK(), getEngineProperties(), shop, getDirection());
                prepareWork(trader);
            } else {
                continueSearching();
            }
        } else {
            if (!workplace.isAtMaxCapacity()){
                if (!workplace.isActivated()){
                    workplace.activate();
                }
                Worker worker = new Worker(getMouseMovementTracker(), getR(), getK(), getEngineProperties(), workplace, getDirection());
                workplace.addActor(worker);
                prepareWork(worker);
            } else {
                continueSearching();
            }
        }
    }

    public void prepareWork(Actor employee){
        //dit mag niet bij commerce, mss wel bij industry ma dan best daar id constructor fixen
        //workplace.addActor(employee);
        setNewActor(employee);
        getHome().changeCapacity(Double.parseDouble(getEngineProperties().getProperty("factor.job.found")));
        workFound=true;
    }

    @Override
    public boolean isValid() {
        boolean isAgeValid = getAge() > 0;
        if (!isAgeValid){
            setNewActor(new Sleeper(getMouseMovementTracker(), getHomeLocation().getKey(),
                    getHomeLocation().getValue(), getEngineProperties(), getDirection()));
            getHome().changeCapacity(Double.parseDouble(getEngineProperties().getProperty("factor.job.not.found")));
        }
        return isAgeValid && !workFound;
    }
}
