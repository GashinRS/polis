package buttons;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import simulation.SimulationEngine;

import java.io.InputStream;

public class PlayButton extends Button implements MyButton {

    private final ImageView[] imageViews;
    private int counter;
    private final SimulationEngine simulationEngine;

    public PlayButton(String play, String pause, int width, int height, SimulationEngine simulationEngine) {
        this.simulationEngine=simulationEngine;
        imageViews = new ImageView[2];
        try (InputStream playIn = this.getClass().getResourceAsStream("/polis/buttons/" + play);
             InputStream pauseIn = this.getClass().getResourceAsStream("/polis/buttons/" + pause)){
            ImageView playImageView = new ImageView(new Image(playIn));
            ImageView pauseImageView = new ImageView(new Image(pauseIn));
            setGraphic(playImageView);
            imageViews[0] = pauseImageView;
            imageViews[1] = playImageView;
        } catch (Exception ex) {
            System.err.println("bestand niet gevonden");
        }
        this.setPrefSize(width, height);
        this.setFocusTraversable(false);
        counter = 0;
        this.setOnAction(value -> buttonPressed());
    }

    @Override
    public void buttonPressed(){
        setGraphic(imageViews[counter%2]);
        if (counter%2==0){
            simulationEngine.play();
        } else {
            simulationEngine.pause();
        }
        counter++;
    }
}
