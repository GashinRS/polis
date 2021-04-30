package tiles.buildtingTiles;

import polis.MouseMovementTracker;
import simulation.GeneralStatistics;

public class IndustryTile extends BuildingTile {

    public IndustryTile() {
        super("industry");
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
