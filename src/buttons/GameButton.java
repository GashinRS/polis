package buttons;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import polis.MouseMovementTracker;

import java.io.InputStream;

/**
 * gemeenschappelijke bovenklase voor alle buttons behalve de play button
 */

public abstract class GameButton extends ToggleButton implements EventHandler<ActionEvent>, MyButton {

    protected MouseMovementTracker mouseMovementTracker;

    public GameButton(String filename, int width, int height, MouseMovementTracker mouseMovementTracker) {
        try (InputStream in = this.getClass().getResourceAsStream("/polis/buttons/" + filename + ".png")){
            ImageView imageView = new ImageView(new Image(in));
            setGraphic(imageView);
        } catch (Exception ex) {
            System.err.println("bestand niet gevonden");
        }
        setPrefSize(width, height);
        this.mouseMovementTracker = mouseMovementTracker;
        setFocusTraversable(false);
        setOnAction(this);
    }


    @Override
    public void handle(ActionEvent ae){
        buttonPressed();
    }

    public abstract void buttonSelected();

    @Override
    public void buttonPressed(){
        setSelected(true);
        buttonSelected();
    }

}
