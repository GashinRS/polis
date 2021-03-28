package polis;

import buttons.PlayButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import prog2.util.Viewport;

/**
 * Dit is de hoofdcontainer waarin alle andere containers en nodes zitten.
 */

public class CityContainer extends StackPane {

    public CityContainer(){
        this.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setPrefSize(1500,1000);

        CityArea cityArea = new CityArea();
        MouseMovementTracker mouseMovementTracker = new MouseMovementTracker(cityArea);
        cityArea.setMouseMovementTracker(mouseMovementTracker);

        CityUI cityUI = new CityUI(mouseMovementTracker);
        PlayButton playButton = new PlayButton("play.png", "pause.png", 75, 50);
        Insets insets = new Insets(5.0, 5.0, 5.0, 5.0);
        setMargin(cityUI, insets);
        setAlignment(cityUI, Pos.TOP_LEFT);
        setMargin(playButton, insets);
        setAlignment(playButton, Pos.BOTTOM_LEFT);

        this.getChildren().addAll(new Viewport(cityArea, 0.5), cityUI, playButton);
    }
}
