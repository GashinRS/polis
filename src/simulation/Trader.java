package simulation;

import polis.MouseMovementTracker;

import java.util.Properties;

public class Trader extends Actor{
    public Trader(MouseMovementTracker mouseMovementTracker, int r, int k, Properties engineProperties) {
        super(mouseMovementTracker, r, k, engineProperties);
    }

    @Override
    public void act() {

    }

    @Override
    public boolean isValid() {
        return false;
    }
}
