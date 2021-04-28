package simulation;

import javafx.scene.paint.Color;
import polis.MouseMovementTracker;
import simulation.Actor;

import java.util.Properties;

public class Customer extends Actor {

    public Customer(MouseMovementTracker mouseMovementTracker, int r, int k, Properties engineProperties) {
        super(mouseMovementTracker, r, k, engineProperties);
        setFill(Color.PURPLE);
    }

    @Override
    public void act() {

    }

    @Override
    public boolean isValid() {
        return true;
    }
}
