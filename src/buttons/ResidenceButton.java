package buttons;

import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import polis.MouseMovementTracker;

public class ResidenceButton extends MyButton {

    public ResidenceButton(String filename, int width, int height, MouseMovementTracker mouseMovementTracker) {
        super(filename, width, height, mouseMovementTracker);
    }

    @Override
    public void handle(ActionEvent ae){
        mouseMovementTracker.setBigPictureTileEventHandler("residence");
    }
}
