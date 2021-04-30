package buttons;

import polis.MouseMovementTracker;

public class RoadButton extends GameButton {

    public RoadButton(String filename, int width, int height, MouseMovementTracker mouseMovementTracker) {
        super(filename, width, height, mouseMovementTracker);
    }

    @Override
    public void buttonSelected(){
        getMouseMovementTracker().setRoadMode();
    }
}
