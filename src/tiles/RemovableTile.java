package tiles;

import polis.MouseMovementTracker;

public abstract class RemovableTile extends Tile{

    public RemovableTile(int cellSize) {
        super(cellSize);
    }

    public abstract void removeThis(MouseMovementTracker mouseMovementTracker);
}
