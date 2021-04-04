package tiles;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.util.Pair;
import polis.MouseMovementTracker;

import java.io.InputStream;

public class BigPictureTile extends RemovableTile{

    public BigPictureTile(String filename, int r, int k, MouseMovementTracker mouseMovementTracker) {
        super(2);
        try (InputStream in = this.getClass().getResourceAsStream("/polis/tiles/" + filename + "-0.png")){
            ImagePattern imagePattern = new ImagePattern(new Image(in));
            this.setFill(imagePattern);
        } catch (Exception ex) {
            System.err.println("bestand niet gevonden");
        }
        mouseMovementTracker.getTiles().put(new Pair<>(r, k), this);
        mouseMovementTracker.getTiles().put(new Pair<>(r + 1, k), this);
        mouseMovementTracker.getTiles().put(new Pair<>(r, k + 1), this);
        mouseMovementTracker.getTiles().put(new Pair<>(r + 1, k + 1), this);
        mouseMovementTracker.setTranslateXY(this, r, k);
    }

    /**
     * Verwijdert de tile uit de lijst van tiles
     */
    @Override
    public void removeThis(MouseMovementTracker mouseMovementTracker){
        for (int i = 0; i<4; i++){
            mouseMovementTracker.getTiles().values().remove(this);
        }
        mouseMovementTracker.getChildren().remove(this);
    }
}
