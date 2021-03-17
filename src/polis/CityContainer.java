package polis;

import buttons.PlayButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import prog2.util.Viewport;

import java.io.InputStream;

/**
 * Dit is de hoofdcontainer waarin alle andere containers en nodes zitten.
 */

public class CityContainer extends StackPane {

    private Button playButton;

    public CityContainer(){
        this.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setPrefSize(1500,1000);
        CityUI cityUI = new CityUI();

        try(InputStream playIn = this.getClass().getResourceAsStream("/polis/buttons/play.png");
            InputStream pauseIn = this.getClass().getResourceAsStream("/polis/buttons/pause.png")){
            playButton = new PlayButton(new ImageView(new Image(playIn)), new ImageView(new Image(pauseIn)), 75, 50);
        } catch (Exception ex){
            System.err.println("bestand niet gevonden");
        }

        Insets insets = new Insets(5.0, 5.0, 5.0, 5.0);
        setMargin(cityUI, insets);
        setAlignment(cityUI, Pos.TOP_LEFT);
        setMargin(playButton, insets);
        setAlignment(playButton, Pos.BOTTOM_LEFT);

        this.getChildren().addAll(new Viewport(new CityArea(), 0.5), cityUI, playButton);
    }
}
