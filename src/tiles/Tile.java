package tiles;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.shape.Polygon;
import polis.MouseMovementTracker;

import java.util.ArrayList;
import java.util.List;

public abstract class Tile extends Polygon implements InvalidationListener, Observable {

    private final int cellSize;
    protected List<InvalidationListener> listenerList = new ArrayList<>();

    public Tile(int cellSize) {
        super(0, 0,
                64 * cellSize, 0.5 * 64 * cellSize,
                0, 64 * cellSize,
                -64 * cellSize, 0.5 * 64 * cellSize);
        setMouseTransparent(true);
        this.cellSize = cellSize;
    }

    public int getCellSize(){
        return cellSize;
    }

    public void checkValidity(int r, int k){}

    @Override
    public void invalidated(Observable observable) {
    }

    @Override
    public void addListener(InvalidationListener invalidationListener) {
        listenerList.add(invalidationListener);
    }

    @Override
    public void removeListener(InvalidationListener invalidationListener) {
        listenerList.remove(invalidationListener);
    }

    public void removeThis(MouseMovementTracker mouseMovementTracker){}

}
