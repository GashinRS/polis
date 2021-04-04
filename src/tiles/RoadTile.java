package tiles;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.util.Pair;
import polis.MouseMovementTracker;

import java.io.InputStream;
import java.util.*;

public class RoadTile extends RemovableTile{

    //velden zijn protected omdat NonRemovableRoadTile overerft van RoadTile exact hetzelfde doet als RoadTile, met als
    //enige verschil dat het niet verwijderbaar is (methode removeThis doet niets)
    protected int r;
    protected int k;
    protected int imageNumber;
    protected List<RoadTile> neighbors;
    protected MouseMovementTracker mouseMovementTracker;

    public RoadTile(int r, int k, MouseMovementTracker mouseMovementTracker) {
        super(1);
        this.r=r;
        this.k=k;
        this.mouseMovementTracker=mouseMovementTracker;
        mouseMovementTracker.getRoadTiles().put(new Pair<>(r, k), this);
        mouseMovementTracker.getTiles().put(new Pair<>(r, k), this);
        mouseMovementTracker.setTranslateXY(this, r, k);
        imageNumber = checkNeighbors();
        for (RoadTile neighbor: neighbors){
            neighbor.invalidated();
        }
        setImage(imageNumber);
    }

    /**
     * Checkt welke buren er zijn en returnt een getal waarmee de afbeelding van de RoadTile ingesteld kan worden
     */
    public int checkNeighbors(){
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

    public void setImage(int number){
        try (InputStream in = this.getClass().getResourceAsStream("/polis/tiles/road-" + number + ".png")){
            ImagePattern imagePattern = new ImagePattern(new Image(in));
            this.setFill(imagePattern);
        } catch (Exception ex) {
            System.err.println("bestand niet gevonden");
        }
    }

    public void invalidated() {
        setImage(checkNeighbors());
    }

    public int getR() {
        return r;
    }

    public int getK() {
        return k;
    }

    @Override
    public void removeThis(MouseMovementTracker mouseMovementTracker){
        mouseMovementTracker.getRoadTiles().remove(new Pair<>(getR(), getK()));
        mouseMovementTracker.getTiles().remove(new Pair<>(getR(), getK()));
        for (RoadTile neighbor:neighbors){
            neighbor.invalidated();
        }
        mouseMovementTracker.getChildren().remove(this);
    }
}
