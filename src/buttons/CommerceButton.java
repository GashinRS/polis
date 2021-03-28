package buttons;

import javafx.event.ActionEvent;
import polis.MouseMovementTracker;

public class CommerceButton extends MyButton {

    public CommerceButton(String filename, int width, int height, MouseMovementTracker mouseMovementTracker) {
        super(filename, width, height, mouseMovementTracker);
    }

    @Override
    public void handle(ActionEvent ae){
        mouseMovementTracker.setBigPictureTileEventHandler("commerce");
    }
}
