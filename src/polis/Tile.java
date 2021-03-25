package polis;

import javafx.scene.shape.Polygon;

public class Tile extends Polygon {
    public Tile() {
        super(0, 0,
                64 * 1, 0.5 * 64 * 1,
                0, 64 * 1,
                -64 * 1, 0.5 * 64 * 1);
    }

}
