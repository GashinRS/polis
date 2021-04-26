package simulatie;

import polis.MouseMovementTracker;

public class Region {

    private final MouseMovementTracker mouseMovementTracker;

    public Region(MouseMovementTracker mouseMovementTracker) {
        this.mouseMovementTracker=mouseMovementTracker;
    }

    public void makeImmigrant(){
        mouseMovementTracker.getCityArea().getChildren().add(new Immigrant(mouseMovementTracker));
    }
}
