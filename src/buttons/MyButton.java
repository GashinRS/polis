package buttons;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import polis.MouseMovementTracker;

import java.io.InputStream;

/**
 * gemeenschappelijke bovenklase voor alle buttons behalve de play button
 */

public abstract class MyButton extends Button implements EventHandler<ActionEvent> {

    protected MouseMovementTracker mouseMovementTracker;

    public MyButton(String filename, int width, int height, MouseMovementTracker mouseMovementTracker) {
        try (InputStream in = this.getClass().getResourceAsStream("/polis/buttons/" + filename + ".png")){
            ImageView imageView = new ImageView(new Image(in));
            this.setGraphic(imageView);
        } catch (Exception ex) {
            System.err.println("bestand niet gevonden");
        }
        this.setPrefSize(width, height);
        this.mouseMovementTracker = mouseMovementTracker;
        this.setFocusTraversable(false);
        this.setOnAction(this);
    }


    @Override
    public void handle(ActionEvent ae){
    }

}
