package tiles.bigPictureTile;

import polis.MouseMovementTracker;
import tiles.bigPictureTile.BigPictureTile;

public class ResidenceTile extends BigPictureTile {

    public ResidenceTile() {
        super("residence");
    }

    @Override
    public boolean isResidence(){
        return true;
    }
}
