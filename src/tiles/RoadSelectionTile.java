package tiles;

import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.Map;

public class RoadSelectionTile extends SmallTile{

    private Color color;
    private Map<Pair<Integer, Integer>, Tile> tiles;

    public RoadSelectionTile(Map<Pair<Integer, Integer>, Tile> tiles) {
        setFill(Color.rgb(90, 155,255, 0.5));
        this.tiles = tiles;
    }

    @Override
    public void checkValidity(int r, int k){
        if (tiles.get(new Pair<>(r, k)) == null){
            color = Color.rgb(90, 155,255, 0.5);
        } else {
            color = Color.rgb(225, 40,70, 0.5);
        }
        setFill(color);
    }

    public Color getColor(){
        return color;
    }
}
