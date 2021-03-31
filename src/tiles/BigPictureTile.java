package tiles;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.util.Pair;
import polis.MouseMovementTracker;

import java.io.InputStream;
import java.util.Map;

public class BigPictureTile extends BigTile{

    private Map<Pair<Integer, Integer>, Tile> tiles;

    public BigPictureTile(String filename, Map<Pair<Integer, Integer>, Tile> tiles) {
        try (InputStream in = this.getClass().getResourceAsStream("/polis/tiles/" + filename + "-0.png")){
            ImagePattern imagePattern = new ImagePattern(new Image(in));
            this.setFill(imagePattern);
        } catch (Exception ex) {
            System.err.println("bestand niet gevonden");
        }
        this.tiles = tiles;
    }

    /**
     * Verwijdert de tile uit de lijst van tiles
     */
    @Override
    public void removeThis(MouseMovementTracker mouseMovementTracker){
        for (int i = 0; i<4; i++){
            tiles.values().remove(this);
        }
        mouseMovementTracker.getChildren().remove(this);
    }
}
