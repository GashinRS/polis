package tiles;

import javafx.scene.paint.Color;
import javafx.util.Pair;
import polis.MouseMovementTracker;

public class RoadSelectionTile extends CursorTile{

    private Color color;

    public RoadSelectionTile(MouseMovementTracker mouseMovementTracker) {
        super(1, mouseMovementTracker);
        setFill(Color.rgb(90, 155,255, 0.5));
    }

    public void invalidated(){
        if (isValid()){
            getMouseMovementTracker().getChildren().add(new RoadTile(getR(), getK(), getMouseMovementTracker()));
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
    public int getCellSize() {
        return 1;
    }

    @Override
    public boolean isValid(){
        return color.equals(getBlue());
    }
}
