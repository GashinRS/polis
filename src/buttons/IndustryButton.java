package buttons;

import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import polis.MouseMovementTracker;

public class IndustryButton extends MyButton {

    public IndustryButton(String filename, int width, int height, MouseMovementTracker mouseMovementTracker) {
        super(filename, width, height, mouseMovementTracker);
    }

    @Override
    public void handle(ActionEvent ae){
        mouseMovementTracker.setBigPictureTileEventHandler("industry");
    }
}
