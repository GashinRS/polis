package tiles.bigPictureTile;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;
import polis.MouseMovementTracker;
import tiles.RemovableTile;
import tiles.Tile;

import java.io.InputStream;
import java.util.List;

/**
 * bug met imageview verwijderen
 */

public class BigPictureTile extends Tile implements RemovableTile {

    private int imageNumber;
    private MouseMovementTracker mouseMovementTracker;
    private ImageView imageView;
    private List<Image> images;
    private final String type;
    private int r;
    private int k;

    public BigPictureTile(String type) {
        super(2);
        this.type=type;
        imageNumber = 0;
    }

    @Override
    public void removeThis() {
        for (int i = 0; i < 4; i++) {
            mouseMovementTracker.getTiles().values().remove(this);
        }

        //onduidelijke bug
        //imageView.setVisible(false);
        mouseMovementTracker.getCityArea().getChildren().remove(imageView);
    }

    public void upgrade() {
        imageView.setImage(images.get(imageNumber % 4));
        double width = images.get(imageNumber % 4).getWidth();
        double height = images.get(imageNumber % 4).getHeight();
        imageView.setImage(images.get(imageNumber % 4));
        imageView.setX(-0.5 * width);
        imageView.setY(0.5 * width - height);
        imageNumber++;
    }

    public boolean isResidence(){
        return false;
    }

    public boolean isCommerce(){
        return false;
    }

    public boolean isIndustry(){
        return false;
    }

    public void setR(int r) {
        this.r = r;
    }

    public void setK(int k) {
        this.k = k;
    }

    public void setMouseMovementTracker(MouseMovementTracker mouseMovementTracker) {
        this.mouseMovementTracker = mouseMovementTracker;
    }

    public void initialize(){
        try (InputStream in0 = this.getClass().getResourceAsStream("/polis/tiles/" + type + "-0.png");
             InputStream in1 = this.getClass().getResourceAsStream("/polis/tiles/" + type + "-1.png");
             InputStream in2 = this.getClass().getResourceAsStream("/polis/tiles/" + type + "-2.png");
             InputStream in3 = this.getClass().getResourceAsStream("/polis/tiles/" + type + "-3.png")) {
            images = List.of(new Image(in0), new Image(in1), new Image(in2), new Image(in3));
            imageView = new ImageView(images.get(0));
            //ivm NPE imageview/vieworder
            imageView.setMouseTransparent(true);

            upgrade();
            mouseMovementTracker.getCityArea().getChildren().add(imageView);
            imageView.setTranslateX(64 * (32 - r + k));
            imageView.setTranslateY(64 * (r + k) / 2);
            imageView.setViewOrder(-r - k - 2);
        } catch (Exception ex) {
            System.err.println("bestand niet gevonden");
        }
        mouseMovementTracker.getTiles().put(new Pair<>(r, k), this);
        mouseMovementTracker.getTiles().put(new Pair<>(r + 1, k), this);
        mouseMovementTracker.getTiles().put(new Pair<>(r, k + 1), this);
        mouseMovementTracker.getTiles().put(new Pair<>(r + 1, k + 1), this);
        mouseMovementTracker.getCityArea().setTranslateXY(this, r, k);
    }
}
