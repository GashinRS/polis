package tiles;

import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.Map;

public class BigSelectionTile extends BigTile{

    private Color color;
    private Map<Pair<Integer, Integer>, Tile> tiles;

    public BigSelectionTile(Map<Pair<Integer, Integer>, Tile> tiles) {
        setFill(Color.rgb(90, 155,255, 0.5));
        this.tiles = tiles;
    }

    @Override
    public void checkValidity(int r, int k) {
        if (tiles.get(new Pair<>(r, k)) == null && tiles.get(new Pair<>(r+1, k)) == null &&
                tiles.get(new Pair<>(r, k+1)) == null && tiles.get(new Pair<>(r+1, k+1)) == null){
            color = Color.rgb(90, 155,255, 0.5);
        } else {
            color = Color.rgb(225, 40,70, 0.5);
        }
        setFill(color);
    }

    //code duplicatie met roadSelectionTile
    public boolean isValid(){
        return color.equals(Color.rgb(90, 155, 255, 0.5));
    }
}
