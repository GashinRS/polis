package buttons;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

/**
 * gemeenschappelijke bovenklase voor alle buttons behalve de play button
 */

public abstract class MyButton extends Button {

    public MyButton(ImageView imageView, int width, int height) {
        this.setGraphic(imageView);
        this.setPrefSize(width, height);
        this.setFocusTraversable(false);
        //this.setOnAction(value -> backgroundHighlight());
    }

//    public void backgroundHighlight(){
//        this.setBackground(new Background(new BackgroundFill(Color.rgb(149,149,149), CornerRadii.EMPTY, Insets.EMPTY)));
//    }
}
