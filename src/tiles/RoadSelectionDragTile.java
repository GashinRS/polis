package tiles;

import javafx.util.Pair;
import polis.MouseMovementTracker;

import java.util.Map;

public class RoadSelectionDragTile extends RoadSelectionTile{

    private final int r;
    private final int k;

    public RoadSelectionDragTile(Map<Pair<Integer, Integer>, Tile> tiles, int r, int k) {
        super(tiles);
        this.r = r;
        this.k = k;
    }

    public int getR() {
        return r;
    }

    public int getK() {
        return k;
    }

}
