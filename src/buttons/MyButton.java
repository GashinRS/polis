package buttons;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
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

public abstract class MyButton extends ToggleButton implements EventHandler<ActionEvent>, InvalidationListener {

    protected MouseMovementTracker mouseMovementTracker;

    public MyButton(String filename, int width, int height, MouseMovementTracker mouseMovementTracker) {
        try (InputStream in = this.getClass().getResourceAsStream("/polis/buttons/" + filename + ".png")){
            ImageView imageView = new ImageView(new Image(in));
            this.setGraphic(imageView);
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
        //setBackground(new Background(new BackgroundFill(Color.rgb(150, 175,205, 0.6), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    @Override
    public void invalidated(Observable o){
        //setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
    }

}
