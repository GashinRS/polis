package tiles;

import javafx.beans.Observable;
import javafx.util.Pair;

import java.util.Map;

public class RoadSelectionDragTile extends RoadSelectionTile{

    private final int r;
    private final int k;

    public RoadSelectionDragTile(Map<Pair<Integer, Integer>, Tile> tiles, int r, int k) {
        super(tiles);
        this.r = r;
        this.k = k;
    }

    @Override
    public void invalidated(Observable observable){

    }

    public int getR() {
        return r;
    }

    public int getK() {
        return k;
    }

}
