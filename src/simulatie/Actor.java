package simulatie;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javafx.util.Pair;
import polis.MouseMovementTracker;

import java.util.Map;
import java.util.Random;

public abstract class Actor extends Circle {

    private final MouseMovementTracker mouseMovementTracker;
    private int r;
    private int k;
    private static final Random RG = new Random();
    //0==ZW, 1==NW, 2==NO, 3==ZO
    //extra element 42 aan toegevoegd om nullpointer exception te vermijden en zodat de actor blijft staan
    //als hij geen enkele van de richtingen op kan

    private static final int[][] directions = new int[][] {
            { 0,1,3,2,42}, {0,3,1,2,42}, {1,3,0,2,42}, {1,0,3,2,42}, {3,0,1,2,42}, {3,1,0,2,42}
    };

    private static final Map<Integer, Integer[]> directionMappings = Map.of(
            0, new Integer[] {-64/4, 64/3}, //ZW
            1, new Integer[] {64/4, 64/3}, //NW
            2, new Integer[] {64/4, 2*(64/3)}, //NO
            3, new Integer[] {-64/4, 2*(64/3)} //ZO
    );

    private static final int [] rco = new int[] {1, 0, -1, 0};
    private static final int [] kco = new int[] {0, -1, 0, 1};
    private int direction;

    public Actor(MouseMovementTracker mouseMovementTracker) {
        super(directionMappings.get(0)[0], directionMappings.get(0)[1], 64/6);
        this.mouseMovementTracker=mouseMovementTracker;
        setViewOrder(-r-k-1.5);
        mouseMovementTracker.getCityArea().setTranslateXY(this, r, k);
        direction=0;
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(250),
                        this::act)
        );
        timeline.play();

    }

    public abstract void act(ActionEvent ae);

    public void move(){
        int option = RG.nextInt(6);
        int index = 0;
        int newDirection = (direction + directions[option][index]) % 4;
        while (mouseMovementTracker.getRoadTiles().get(new Pair<>(r+rco[newDirection], k+kco[newDirection])) == null &&
        index < 4){
            index++;
            newDirection = (direction + directions[option][index]) % 4;
        }
        if (index < 4){
            direction = newDirection;
            r=r+rco[newDirection];
            k=k+kco[newDirection];
            mouseMovementTracker.getCityArea().setTranslateXY(this, r, k);
            setViewOrder(-r-k-1.5);
            setCenterX(directionMappings.get(direction)[0]);
            setCenterY(directionMappings.get(direction)[1]);
        }

    }

    public void setR(int r){
        this.r=r;
    }

    public void setK(int k) {
        this.k=k;
    }

    public int getR() {
        return r;
    }

    public int getK() {
        return k;
    }


}
