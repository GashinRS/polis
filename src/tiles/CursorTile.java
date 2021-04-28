package tiles;

import javafx.scene.paint.Color;
import polis.MouseMovementTracker;

public abstract class CursorTile extends Tile {

    private static final Color BLUE = Color.rgb(90, 155,255, 0.5);
    private static final Color RED = Color.rgb(225, 40,70, 0.5);
    private final MouseMovementTracker mouseMovementTracker;
    private int r;
    private int k;

    public CursorTile(int cellSize, MouseMovementTracker mouseMovementTracker) {
        super(cellSize);
        this.mouseMovementTracker=mouseMovementTracker;
        setViewOrder(-100);
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
        return BLUE;
    }

    public Color getRed(){
        return RED;
    }

    public MouseMovementTracker getMouseMovementTracker(){
        return mouseMovementTracker;
    }

    public void checkValidity(int r, int k){
        setRK(r, k);
        mouseMovementTracker.getCityArea().setTranslateXY(this, r, k);
    }

    public boolean isValid(){
        return false;
    }

}
