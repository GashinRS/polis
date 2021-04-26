package tiles;

import javafx.beans.Observable;
import polis.MouseMovementTracker;

public class RoadSelectionDragTile extends RoadSelectionTile{

    private final int r;
    private final int k;

    public RoadSelectionDragTile(int r, int k, MouseMovementTracker mouseMovementTracker) {
        super(mouseMovementTracker);
        this.r = r;
        this.k = k;
        mouseMovementTracker.getCityArea().setTranslateXY(this, r, k);
        mouseMovementTracker.getCityArea().getChildren().add(this);
        checkValidity(r, k);
    }

    @Override
    public void invalidated(Observable o){
        getMouseMovementTracker().getCityArea().getChildren().remove(this);
        super.invalidated(o);
    }

    @Override
    public int getR() {
        return r;
    }

    @Override
    public int getK() {
        return k;
    }

}
