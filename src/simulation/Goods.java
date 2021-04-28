package simulation;

import polis.MouseMovementTracker;
import simulation.Actor;

import java.util.Properties;

public class Goods extends Actor {

    public Goods(MouseMovementTracker mouseMovementTracker, int r, int k, Properties engineProperties) {
        super(mouseMovementTracker, r, k, engineProperties);
    }

    @Override
    public void act() {

    }

    @Override
    public boolean isValid() {
        return true;
    }
}
