package buttons;

import polis.MouseMovementTracker;

public class BulldozerButton extends GameButton {

    public BulldozerButton(String filename, int width, int height, MouseMovementTracker mouseMovementTracker) {
        super(filename, width, height, mouseMovementTracker);
    }

//    @Override
//    public void handle(ActionEvent ae){
//        mouseMovementTracker.setBulldozerMode();
//    }
    @Override
    public void buttonSelected(){
        mouseMovementTracker.setBulldozerMode();
    }
}
