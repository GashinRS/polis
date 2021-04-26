package tiles.bigPictureTile;

import polis.MouseMovementTracker;
import tiles.bigPictureTile.BigPictureTile;

public class IndustryTile extends BigPictureTile {

    public IndustryTile() {
        super("industry");
    }

    @Override
    public boolean isIndustry(){
        return true;
    }
}
