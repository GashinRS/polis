package tiles;

import javafx.scene.paint.Color;
import javafx.util.Pair;
import polis.MouseMovementTracker;

public class BigSelectionTile extends CursorTile{

    private Color color;

    public BigSelectionTile(MouseMovementTracker mouseMovementTracker) {
        super(2, mouseMovementTracker);
        setFill(getBlue());
    }

    @Override
    public void checkValidity(int r, int k) {
        super.checkValidity(r, k);
        if (getMouseMovementTracker().getTiles().get(new Pair<>(r, k)) == null && getMouseMovementTracker().getTiles().get(new Pair<>(r+1, k)) == null &&
                getMouseMovementTracker().getTiles().get(new Pair<>(r, k+1)) == null && getMouseMovementTracker().getTiles().get(new Pair<>(r+1, k+1)) == null){
            color = getBlue();
        } else {
            color = getRed();
        }
        setFill(color);
    }

    @Override
    public int getCellSize() {
        return 2;
    }

    @Override
    public boolean isValid(){
        return color.equals(getBlue());
    }
}
