package buttons;

import polis.MouseMovementTracker;

public class CommerceButton extends GameButton {

    public CommerceButton(String filename, int width, int height, MouseMovementTracker mouseMovementTracker) {
        super(filename, width, height, mouseMovementTracker);
    }


    @Override
    public void buttonSelected(){
        getMouseMovementTracker().setbuildingTileEventHandler("commerce");
    }
}
