package eventHandlers;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;
import polis.MouseMovementTracker;
import tiles.BigPictureTile;
import tiles.BigSelectionTile;
import tiles.Tile;

import java.util.Map;

public class BigPictureTileHandler implements EventHandler<MouseEvent> {

    private final String bigPictureTileType;
    private final MouseMovementTracker mouseMovementTracker;
    private Map<Pair<Integer, Integer>, Tile> tiles;
    private BigSelectionTile bigSelectionTile;

    public BigPictureTileHandler(String bigPictureTileType, MouseMovementTracker mouseMovementTracker,
                                 Map<Pair<Integer, Integer>, Tile> tiles, BigSelectionTile bigSelectionTile) {
        this.bigPictureTileType = bigPictureTileType;
        this.mouseMovementTracker = mouseMovementTracker;
        this.tiles = tiles;
        this.bigSelectionTile = bigSelectionTile;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        int r = mouseMovementTracker.getR(mouseEvent);
        int k = mouseMovementTracker.getK(mouseEvent);
        //if conditie optimizen
        if (bigSelectionTile.isValid() && r>=0 && r<31 && k>=0 && k<31) {
            BigPictureTile bigPictureTile = new BigPictureTile(bigPictureTileType, tiles);
            mouseMovementTracker.getChildren().add(bigPictureTile);
            tiles.put(new Pair<>(mouseMovementTracker.getR(mouseEvent), mouseMovementTracker.getK(mouseEvent)), bigPictureTile);
            tiles.put(new Pair<>(mouseMovementTracker.getR(mouseEvent) + 1, mouseMovementTracker.getK(mouseEvent)), bigPictureTile);
            tiles.put(new Pair<>(mouseMovementTracker.getR(mouseEvent), mouseMovementTracker.getK(mouseEvent) + 1), bigPictureTile);
            tiles.put(new Pair<>(mouseMovementTracker.getR(mouseEvent) + 1, mouseMovementTracker.getK(mouseEvent) + 1), bigPictureTile);
            mouseMovementTracker.setTranslateXY(bigPictureTile, mouseMovementTracker.getR(mouseEvent), mouseMovementTracker.getK(mouseEvent));
        }
    }
}
