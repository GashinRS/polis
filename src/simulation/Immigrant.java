package simulation;

import javafx.scene.paint.Color;
import polis.MouseMovementTracker;

public class Immigrant extends Actor {

    private int age;
    private final Region region;

    public Immigrant(MouseMovementTracker mouseMovementTracker, int age, Region region) {
        super(mouseMovementTracker, 0, 14);
        setFill(Color.GREY);
        this.age=age;
        this.region=region;
    }

    @Override
    public void act() {
        age -= 1;
        move();
    }

    @Override
    public boolean isValid() {
        boolean valid = age >= 0;
        if (!valid){
            region.slowDown();
        }
        return valid;
    }
}
