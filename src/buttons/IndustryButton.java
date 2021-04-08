package buttons;

import polis.MouseMovementTracker;

public class IndustryButton extends GameButton {

    public IndustryButton(String filename, int width, int height, MouseMovementTracker mouseMovementTracker) {
        super(filename, width, height, mouseMovementTracker);
    }

//    @Override
//    public void handle(ActionEvent ae){
//        mouseMovementTracker.setBigPictureTileEventHandler("industry");
//    }
    @Override
    public void buttonSelected(){
        mouseMovementTracker.setBigPictureTileEventHandler("industry");
    }
}
