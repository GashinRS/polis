package tiles;

import javafx.scene.paint.Color;
import polis.MouseMovementTracker;

public abstract class CursorTile extends Tile {

    private static final Color blue = Color.rgb(90, 155,255, 0.5);
    private static final Color red = Color.rgb(225, 40,70, 0.5);
    private final MouseMovementTracker mouseMovementTracker;
    private int r;
    private int k;

    public CursorTile(int cellSize, MouseMovementTracker mouseMovementTracker) {
        super(cellSize);
        this.mouseMovementTracker=mouseMovementTracker;
    }

    public void setRK(int r, int k) {
        this.r=r;
        this.k=k;
    }

    public int getR() {
        return r;
    }

    public int getK() {
        return k;
    }

    public Color getBlue(){
        return blue;
    }

    public Color getRed(){
        return red;
    }

    public MouseMovementTracker getMouseMovementTracker(){
        return mouseMovementTracker;
    }

    public void checkValidity(int r, int k){
        setRK(r, k);
        mouseMovementTracker.setTranslateXY(this, r, k);
    }

    public boolean isValid(){
        return false;
    }

    public abstract int getCellSize();

}
