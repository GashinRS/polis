package tiles;

import javafx.scene.paint.Color;
import javafx.util.Pair;
import polis.MouseMovementTracker;

public class BigSelectionTile extends CursorTile{

    private Color color;
    //deze array ondersteunt bij het checken of een gebouw geplaats kan worden
    private static final int[][] filledTiles= new int[][] {
            {0, 1, 0, 1}, {0, 0, 1, 1}
    };

    public BigSelectionTile(MouseMovementTracker mouseMovementTracker) {
        super(2, mouseMovementTracker);
        setFill(getBlue());
    }

    @Override
    public void checkValidity(int r, int k) {
        super.checkValidity(r, k);
        boolean valid = true;
        int i = 0;
        while (valid && i < 4){
            valid = getMouseMovementTracker().getBuildingTiles().get(new Pair<>(r+filledTiles[0][i], k+filledTiles[1][i])) == null &&
                    getMouseMovementTracker().getRoadTiles().get(new Pair<>(r+filledTiles[0][i], k+filledTiles[1][i])) == null;
            i++;
        }
        if (valid){
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
