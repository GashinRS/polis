package tiles.bigPictureTile;

import polis.MouseMovementTracker;
import simulation.GeneralStatistics;
import tiles.bigPictureTile.BigPictureTile;

public class IndustryTile extends BigPictureTile {

    public IndustryTile() {
        super("industry");
    }

    @Override
    public boolean isIndustry(){
        return true;
    }

    @Override
    public void initialize(MouseMovementTracker mouseMovementTracker, int r, int k, GeneralStatistics generalStatistics) {
        super.initialize(mouseMovementTracker, r, k, generalStatistics);
        getGeneralStatistics().increaseMaxJobs(getCapacity());
    }

    @Override
    public void changeGeneralStatistics(int oldValue, int newValue, double oldValue2, double newValue2) {
        getGeneralStatistics().setCurrentJobs(oldValue, newValue);
        getGeneralStatistics().setMaxJobs(oldValue2, newValue2);
    }

}
