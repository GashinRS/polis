package tiles;

import polis.MouseMovementTracker;

/**
 * Deze klasse heeft exact dezelfde functionaliteit als RoadTile met het enige verschil dat het niet verwijderd kan worden
 * (vandaar dat de removeThis() methode niets doet).
 */

public class NonRemovableRoadTile extends RoadTile{

    public NonRemovableRoadTile(int r, int k, MouseMovementTracker mouseMovementTracker) {
        super(r, k, mouseMovementTracker);
    }

    @Override
    public void removeThis(){}

}
