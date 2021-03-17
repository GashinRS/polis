package buttons;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class PlayButton extends Button {

    private final ImageView[] imageViews;
    private int counter;
    public PlayButton(ImageView playImageView, ImageView pauseImageView, int width, int height) {
        this.setGraphic(playImageView);
        this.setPrefSize(width, height);
        this.setFocusTraversable(false);
        counter = 0;
        imageViews = new ImageView[2];
        imageViews[0] = pauseImageView;
        imageViews[1] = playImageView;
        this.setOnAction(value -> setImage(imageViews[counter%2]));
    }

    private void setImage(ImageView imageView){
        this.setGraphic(imageView);
        counter++;
    }
}
