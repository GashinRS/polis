package tiles;

import javafx.scene.paint.Color;

public class BigTile extends Tile {

    public BigTile() {
        super(2);
        setFill(Color.rgb(90, 155,255, 0.5));
    }

    @Override
    public void checkValidity(int r, int k) {

    }
}
