package buttons;

import polis.MouseMovementTracker;

public class SelectionButton extends GameButton {

    public SelectionButton(String filename, int width, int height, MouseMovementTracker mouseMovementTracker) {
        super(filename, width, height, mouseMovementTracker);
    }

//    @Override
//    public void handle(ActionEvent ae){
//        mouseMovementTracker.setSelectionMode();
//    }
    @Override
    public void buttonSelected(){
        mouseMovementTracker.setSelectionMode();
    }
}
