package polis;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RoadTile extends Tile implements Observable, InvalidationListener {

    private int r;
    private int k;
    private boolean duplicate;
    private int imageNumber;
    private List<InvalidationListener> listenerList;
    //Deze map bevat de mogelijke coordinaten van de buren van een RoadTile, en wordt gebruikt om bij het checkNeighbors
    //om 4 ifs in te korten naar 1 enkele if
    private Map<Integer, Map <Integer, Integer>> states;

    public RoadTile(int r, int k, List<RoadTile> roadTileList) {
        int index = 0;
        while (!duplicate && index < roadTileList.size()){
            duplicate = (roadTileList.get(index).getR() == r && roadTileList.get(index).getK() == k);
            index++;
        }
        if (!duplicate) {
            states = Map.of(
                    r-1, Map.of(k,1),
                    r, Map.of(k+1,2,
                            k-1, 8),
                    r+1, Map.of(k,4));
            this.r = r;
            this.k = k;
            listenerList = new ArrayList<>();
            imageNumber = checkNeighbor(roadTileList, true);
            //Voegt het object zelf toe als listener bij zijn buren
            for (InvalidationListener listener : listenerList) {
                RoadTile roadTile = (RoadTile) listener;
                roadTile.addListener(this);
            }
            setImage(imageNumber);
        }
    }

    /**
     * Checkt welke buren deze RoadTile heeft. Deze methode wordt 1 keer gebruikt in de constructor en wordt ook
     * opgeroepen in invalidated om te kijken of het aantal buren is veranderd. Enkel wanneer deze methode voor het eerst
     * wordt opgeroepen in de constructor moeten de buren als listener toegevoegd worden van dit object, vandaar
     * de boolean als parameter. In de for each loop wordt gekeken of de huidige tile een buur is, en zo ja, wordt aan de
     * hand van de map states het juiste getal bepaald om toe te voegen aan de variabele number.
     */
    public int checkNeighbor(List<RoadTile> roadTileList, boolean firstTime){
        int number = 0;
        for (RoadTile roadTile:roadTileList){
            if ((roadTile.getR() == r-1 && roadTile.getK() == k) || (roadTile.getR() == r && roadTile.getK() == k+1) ||
                    (roadTile.getR() == r+1 && roadTile.getK() == k) || (roadTile.getR() == r && roadTile.getK() == k-1)){
                number += states.get(roadTile.getR()).get(roadTile.getK());
                if (firstTime) {
                    addListener(roadTile);
                }
            }
        }
        return number;
    }

    /**
     * Stelt de correcte afbeelding van de RoadTile in en brengt alle listeners op de hoogte van deze verandering.
     */
    public void setImage(int number){
        try (InputStream in = this.getClass().getResourceAsStream("/polis/tiles/road-" + number + ".png")){
            ImagePattern imagePattern = new ImagePattern(new Image(in));
            this.setFill(imagePattern);
        } catch (Exception ex) {
            System.err.println("bestand niet gevonden");
        }
        for (InvalidationListener listener : listenerList) {
            listener.invalidated(this);
        }
    }

    /**
     * Er wordt een lijst van RoadTiles aangemaakt die alle buren bevat, deze wordt gebruikt om te kijken of het aantal
     * buren is veranderd. Als dit het geval is wordt een nieuwe imageNumber en afbeelding ingesteld.
     */
    @Override
    public void invalidated(Observable observable) {
        List<RoadTile> neighbors = new ArrayList<>();
        for (InvalidationListener listener: listenerList){
            RoadTile roadTile = (RoadTile) listener;
            neighbors.add(roadTile);
        }
        int number = checkNeighbor(neighbors, false);
        if (imageNumber != number){
            imageNumber = number;
            setImage(imageNumber);
        }
    }

    @Override
    public void addListener(InvalidationListener invalidationListener) {
        listenerList.add(invalidationListener);
    }

    @Override
    public void removeListener(InvalidationListener invalidationListener) {
        listenerList.remove(invalidationListener);
    }

    public int getR() {
        return r;
    }

    public int getK() {
        return k;
    }

    public boolean isDuplicate() {
        return duplicate;
    }
}
