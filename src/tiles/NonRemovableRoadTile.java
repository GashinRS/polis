package tiles;

import javafx.util.Pair;
import polis.MouseMovementTracker;

import java.util.List;
import java.util.Map;

public class NonRemovableRoadTile extends RoadTile{

    public NonRemovableRoadTile(int r, int k, List<RoadTile> roadTileList, Map<Pair<Integer, Integer>, Tile> tiles) {
        super(r, k, roadTileList, tiles);
    }

    @Override
    public void removeThis(MouseMovementTracker mouseMovementTracker){}

}
