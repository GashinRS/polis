package tiles;

import polis.MouseMovementTracker;

public class RoadSelectionDragTile extends RoadSelectionTile{

    private final int r;
    private final int k;

    public RoadSelectionDragTile(int r, int k, MouseMovementTracker mouseMovementTracker) {
        super(mouseMovementTracker);
        this.r = r;
        this.k = k;
        mouseMovementTracker.setTranslateXY(this, r, k);
        mouseMovementTracker.getChildren().add(this);
        checkValidity(r, k);
    }

    @Override
    public void invalidated(){
        getMouseMovementTracker().getChildren().remove(this);
        super.invalidated();
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
