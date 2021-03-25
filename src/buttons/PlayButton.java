package buttons;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;

public class PlayButton extends Button {

    private final ImageView[] imageViews;
    private int counter;
    public PlayButton(String play, String pause, int width, int height) {
        imageViews = new ImageView[2];
        try (InputStream playIn = this.getClass().getResourceAsStream("/polis/buttons/" + play);
             InputStream pauseIn = this.getClass().getResourceAsStream("/polis/buttons/" + pause)){
            ImageView playImageView = new ImageView(new Image(playIn));
            ImageView pauseImageView = new ImageView(new Image(pauseIn));
            this.setGraphic(playImageView);
            imageViews[0] = pauseImageView;
            imageViews[1] = playImageView;
        } catch (Exception ex) {
            System.err.println("bestand niet gevonden");
        }
        this.setPrefSize(width, height);
        this.setFocusTraversable(false);
        counter = 0;
        this.setOnAction(value -> setImage(imageViews[counter%2]));
    }

    private void setImage(ImageView imageView){
        this.setGraphic(imageView);
        counter++;
    }
}
