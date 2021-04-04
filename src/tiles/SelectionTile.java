package tiles;

import javafx.scene.paint.Color;
import polis.MouseMovementTracker;

public class SelectionTile extends CursorTile{

    public SelectionTile(MouseMovementTracker mouseMovementTracker) {
        super(1, mouseMovementTracker);
        setFill(Color.TRANSPARENT);
        setStroke(Color.PURPLE);
        setStrokeWidth(5);
    }
}
