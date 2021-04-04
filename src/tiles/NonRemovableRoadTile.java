package tiles;

import polis.MouseMovementTracker;

public class NonRemovableRoadTile extends RoadTile{

    public NonRemovableRoadTile(int r, int k, MouseMovementTracker mouseMovementTracker) {
        super(r, k, mouseMovementTracker);
    }

    @Override
    public void removeThis(MouseMovementTracker mouseMovementTracker){}

}
