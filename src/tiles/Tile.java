package tiles;

import javafx.scene.shape.Polygon;

/**
 * Bovenklasse voor alle tiles waarmee makkelijk een tile van een bepaalde grootte aangemaakt kan worden.
 */

public abstract class Tile extends Polygon {

    private final int cellSize;

    public Tile(int cellSize) {
        super(0, 0,
                64 * cellSize, 0.5 * 64 * cellSize,
                0, 64 * cellSize,
                -64 * cellSize, 0.5 * 64 * cellSize);
        setMouseTransparent(true);
        this.cellSize=cellSize;
    }

    public int getCellSize(){
        return cellSize;
    }

}
