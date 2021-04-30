package buttons;

import polis.MouseMovementTracker;

public class ResidenceButton extends GameButton {

    public ResidenceButton(String filename, int width, int height, MouseMovementTracker mouseMovementTracker) {
        super(filename, width, height, mouseMovementTracker);
    }

    @Override
    public void buttonSelected(){
        getMouseMovementTracker().setbuildingTileEventHandler("residence");
    }
}
