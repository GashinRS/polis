package tiles.bigPictureTile;

import polis.MouseMovementTracker;
import tiles.bigPictureTile.BigPictureTile;

public class CommerceTile extends BigPictureTile {

    public CommerceTile() {
        super("commerce");
    }

    @Override
    public boolean isCommerce(){
        return true;
    }
}
