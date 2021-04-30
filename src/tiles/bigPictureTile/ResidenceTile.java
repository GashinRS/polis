package tiles.bigPictureTile;


import polis.MouseMovementTracker;
import simulation.GeneralStatistics;

public class ResidenceTile extends BigPictureTile {

    public ResidenceTile() {
        super("residence");
    }

    @Override
    public boolean isResidence(){
        return true;
    }

    @Override
    public void initialize(MouseMovementTracker mouseMovementTracker, int r, int k, GeneralStatistics generalStatistics) {
        super.initialize(mouseMovementTracker, r, k, generalStatistics);
        getGeneralStatistics().increaseMaxInhabitants(getCapacity());
    }

    @Override
    public void changeGeneralStatistics(int oldValue, int newValue, double oldValue2, double newValue2) {
        getGeneralStatistics().setResidenceStats(oldValue, newValue, oldValue2, newValue2);
    }

}
