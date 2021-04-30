package tiles;

import javafx.scene.paint.Color;
import javafx.util.Pair;
import polis.MouseMovementTracker;

/**
 * Dit is het cursorblokje dat de muis volgt bij het leggen van wegen.
 */

public class RoadSelectionTile extends CursorTile {

    private Color color;

    public RoadSelectionTile(MouseMovementTracker mouseMovementTracker) {
        super(1, mouseMovementTracker);
        setFill(Color.rgb(90, 155,255, 0.5));
    }

    public void placeRoadIfValid(){
        if (isValid()){
            getMouseMovementTracker().getCityArea().getChildren().add(new RoadTile(getR(), getK(), getMouseMovementTracker()));
        }
    }

    @Override
    public void checkValidity(int r, int k){
        super.checkValidity(r, k);
        if (getMouseMovementTracker().getBuildingTiles().get(new Pair<>(r, k)) == null &&
            getMouseMovementTracker().getRoadTiles().get(new Pair<>(r, k)) == null){
            color = getBlue();
        } else {
            color = getRed();
        }
        setFill(color);
    }

    @Override
    public boolean isValid(){
        return color.equals(getBlue());
    }
}
