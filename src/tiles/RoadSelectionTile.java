package tiles;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import polis.MouseMovementTracker;

public class RoadSelectionTile extends CursorTile implements InvalidationListener {

    private Color color;

    public RoadSelectionTile(MouseMovementTracker mouseMovementTracker) {
        super(1, mouseMovementTracker);
        setFill(Color.rgb(90, 155,255, 0.5));
    }

    @Override
    public void invalidated(Observable o){
        if (isValid()){
            getMouseMovementTracker().getCityArea().getChildren().add(new RoadTile(getR(), getK(), getMouseMovementTracker()));
        }
    }

    @Override
    public void checkValidity(int r, int k){
        super.checkValidity(r, k);
        if (getMouseMovementTracker().getTiles().get(new Pair<>(r, k)) == null){
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
