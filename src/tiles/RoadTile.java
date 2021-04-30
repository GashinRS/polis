package tiles;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.util.Pair;
import polis.MouseMovementTracker;

import java.io.InputStream;
import java.util.*;

public class RoadTile extends Tile implements RemovableTile{

    private final int r;
    private final int k;
    private List<RoadTile> neighbors;
    private final MouseMovementTracker mouseMovementTracker;

    public RoadTile(int r, int k, MouseMovementTracker mouseMovementTracker) {
        super(1);
        this.r=r;
        this.k=k;
        this.mouseMovementTracker=mouseMovementTracker;
        setViewOrder(-r -k -1);
        mouseMovementTracker.getRoadTiles().put(new Pair<>(r, k), this);
        mouseMovementTracker.getCityArea().setTranslateXY(this, r, k);
        int imageNumber = checkNeighbors();
        for (RoadTile neighbor: neighbors){
            neighbor.invalidated();
        }
        setImage(imageNumber);
    }

    /**
     * Checkt welke buren er zijn en returnt een getal waarmee de afbeelding van de RoadTile ingesteld kan worden
     */

    private int checkNeighbors(){
        int number = 0;
        neighbors = new ArrayList<>();
        RoadTile noordOost = mouseMovementTracker.getRoadTiles().get(new Pair<>(r-1, k));
        RoadTile zuidOost = mouseMovementTracker.getRoadTiles().get(new Pair<>(r, k+1));
        RoadTile zuidWest = mouseMovementTracker.getRoadTiles().get(new Pair<>(r+1, k));
        RoadTile noordWest = mouseMovementTracker.getRoadTiles().get(new Pair<>(r, k-1));
        neighbors.add(noordOost);
        neighbors.add(zuidOost);
        neighbors.add(zuidWest);
        neighbors.add(noordWest);
        //aan de hand van een binaire waarde wordt bepaald welke afbeeldingnummer de RoadTile moet hebben
        int binaryValue = 1;
        for (RoadTile neighbor: neighbors){
            if (neighbor != null){
                number += binaryValue;
            }
            binaryValue *= 2;
        }
        neighbors.removeAll(Collections.singleton(null));
        return number;
    }

    private void setImage(int number){
        try (InputStream in = this.getClass().getResourceAsStream("/polis/tiles/road-" + number + ".png")){
            ImagePattern imagePattern = new ImagePattern(new Image(in));
            this.setFill(imagePattern);
        } catch (Exception ex) {
            System.err.println("bestand niet gevonden");
        }
    }

    private void invalidated() {
        setImage(checkNeighbors());
    }

    private int getR() {
        return r;
    }

    private int getK() {
        return k;
    }

    @Override
    public void removeThis(){
        mouseMovementTracker.getRoadTiles().remove(new Pair<>(getR(), getK()));
        mouseMovementTracker.getBuildingTiles().remove(new Pair<>(getR(), getK()));
        for (RoadTile neighbor:neighbors){
            neighbor.invalidated();
        }
        mouseMovementTracker.getCityArea().getChildren().remove(this);
    }
}
