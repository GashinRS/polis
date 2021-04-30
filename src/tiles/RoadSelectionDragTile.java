package tiles;

import polis.MouseMovementTracker;

/**
 * Dit zijn de blokjes die getoond worden wanneer er met de muis gesleept wordt tijdens het leggen van wegen.
 */

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

    /**
     * Tijdens het leggen van wegen wordt deze methode opgeroepen wanneer de muis losgelaten wordt
     */

    @Override
    public void placeRoadIfValid(){
        getMouseMovementTracker().getCityArea().getChildren().remove(this);
        super.placeRoadIfValid();
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
