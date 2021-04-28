package simulation;

import polis.MouseMovementTracker;

public class Sleeper extends Actor{

    public Sleeper(MouseMovementTracker mouseMovementTracker, int r, int k) {
        super(mouseMovementTracker, r, k);
    }

    @Override
    public void act() {

    }

    @Override
    public boolean isValid() {
        return false;
    }
}
