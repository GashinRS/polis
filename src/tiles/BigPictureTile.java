package tiles;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.util.Pair;
import polis.MouseMovementTracker;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * bug met imageview verwijderen
 */

public class BigPictureTile extends RemovableTile {

    private int imageNumber;
    private final MouseMovementTracker mouseMovementTracker;
    private ImageView imageView;
    private List<Image> images;

    public BigPictureTile(String type, int r, int k, MouseMovementTracker mouseMovementTracker) {
        super(2, r, k);
        this.mouseMovementTracker = mouseMovementTracker;
        imageNumber = 0;
        try (InputStream in0 = this.getClass().getResourceAsStream("/polis/tiles/" + type + "-0.png");
             InputStream in1 = this.getClass().getResourceAsStream("/polis/tiles/" + type + "-1.png");
             InputStream in2 = this.getClass().getResourceAsStream("/polis/tiles/" + type + "-2.png");
             InputStream in3 = this.getClass().getResourceAsStream("/polis/tiles/" + type + "-3.png")) {
            images = List.of(new Image(in0), new Image(in1), new Image(in2), new Image(in3));
            imageView = new ImageView(images.get(0));
            setImageNumber();
            mouseMovementTracker.getChildren().add(imageView);
            imageView.setTranslateX(64 * (32 - getR() + getK()));
            imageView.setTranslateY(64 * (getR() + getK()) / 2);
            imageView.setViewOrder(-r - k - 2);
        } catch (Exception ex) {
            System.err.println("bestand niet gevonden");
        }
        mouseMovementTracker.getTiles().put(new Pair<>(r, k), this);
        mouseMovementTracker.getTiles().put(new Pair<>(r + 1, k), this);
        mouseMovementTracker.getTiles().put(new Pair<>(r, k + 1), this);
        mouseMovementTracker.getTiles().put(new Pair<>(r + 1, k + 1), this);
        mouseMovementTracker.setTranslateXY(this, r, k);
    }

    @Override
    public void removeThis() {
        for (int i = 0; i < 4; i++) {
            mouseMovementTracker.getTiles().values().remove(this);
        }

        //onduidelijke bug
        imageView.setVisible(false);
        //mouseMovementTracker.getChildren().remove(imageView);
    }

    public void setImageNumber() {
        imageView.setImage(images.get(imageNumber % 4));
        double width = images.get(imageNumber % 4).getWidth();
        double height = images.get(imageNumber % 4).getHeight();
        imageView.setImage(images.get(imageNumber % 4));
        imageView.setX(-0.5 * width);
        imageView.setY(0.5 * width - height);
        imageNumber++;
    }
}
