package buttons;

import polis.MouseMovementTracker;

public class ResidenceButton extends GameButton {

    public ResidenceButton(String filename, int width, int height, MouseMovementTracker mouseMovementTracker) {
        super(filename, width, height, mouseMovementTracker);
    }

//    @Override
//    public void handle(ActionEvent ae){
//        mouseMovementTracker.setBigPictureTileEventHandler("residence");
//    }
    @Override
    public void buttonSelected(){
        mouseMovementTracker.setBigPictureTileEventHandler("residence");
    }
}
