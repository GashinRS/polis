package buttons;

import polis.MouseMovementTracker;

public class CommerceButton extends GameButton {

    public CommerceButton(String filename, int width, int height, MouseMovementTracker mouseMovementTracker) {
        super(filename, width, height, mouseMovementTracker);
    }

//    @Override
//    public void handle(ActionEvent ae){
//        mouseMovementTracker.setBigPictureTileEventHandler("commerce");
//    }

    @Override
    public void buttonSelected(){
        mouseMovementTracker.setBigPictureTileEventHandler("commerce");
    }
}
