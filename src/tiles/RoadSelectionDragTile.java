package tiles;

import javafx.beans.Observable;
import javafx.util.Pair;
import polis.MouseMovementTracker;

import java.util.List;
import java.util.Map;

public class RoadSelectionDragTile extends RoadSelectionTile{

    private final int r;
    private final int k;
    private MouseMovementTracker mouseMovementTracker;

    public RoadSelectionDragTile(Map<Pair<Integer, Integer>, Tile> tiles, int r, int k, MouseMovementTracker mouseMovementTracker) {
        super(tiles);
        this.r = r;
        this.k = k;
        this.mouseMovementTracker = mouseMovementTracker;
    }

    public void checkOutOfBound(List<RoadSelectionDragTile> roadSelectionTiles){
        if(!(r>=0 && r<32 && k>=0 && k<32)){
            roadSelectionTiles.remove(this);
            mouseMovementTracker.getChildren().remove(this);
        }
    }

//    @Override
//    public void checkValidity(int r, int k){
//        if(r>=0 && r<32 && k>=0 && k<32){
//            super.checkValidity(r, k);
//        } else {
//            mouseMovementTracker.getRoadSelectionDragTiles().remove(this);
//            mouseMovementTracker.getChildren().remove(this);
//        }
//    }

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
