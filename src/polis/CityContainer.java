package polis;

import buttons.MyButton;
import buttons.PlayButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import prog2.util.Viewport;
import simulation.GeneralStatistics;
import simulation.InfoPanel;
import simulation.SimulationEngine;

import java.util.Map;

/**
 * Dit is de hoofdcontainer waarin alle andere containers en nodes zitten.
 */

public class CityContainer extends StackPane {

    public CityContainer(){
        this.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setPrefSize(1500,1000);

        GeneralStatistics generalStatistics = new GeneralStatistics();
        InfoPanel infoPanel = new InfoPanel(generalStatistics);
        MouseMovementTracker mouseMovementTracker = new MouseMovementTracker(infoPanel, generalStatistics);
        CityArea cityArea = new CityArea(mouseMovementTracker);
        SimulationEngine simulationEngine = new SimulationEngine(mouseMovementTracker);

        CityUI cityUI = new CityUI(mouseMovementTracker);
        PlayButton playButton = new PlayButton("play.png", "pause.png", 75, 50, simulationEngine);
        Insets insets = new Insets(5.0, 5.0, 5.0, 5.0);

        setMargin(infoPanel, insets);
        setAlignment(infoPanel, Pos.BOTTOM_RIGHT);
        setMargin(cityUI, insets);
        setAlignment(cityUI, Pos.TOP_LEFT);
        setMargin(playButton, insets);
        setAlignment(playButton, Pos.BOTTOM_LEFT);

        this.getChildren().addAll(new Viewport(cityArea, 0.5), cityUI, playButton, infoPanel);

        Map<KeyCode, MyButton> buttonKeyMappings = Map.of(
                KeyCode.R, cityUI.getResidenceButton(),
                KeyCode.I, cityUI.getIndustryButton(),
                KeyCode.C, cityUI.getCommerceButton(),
                KeyCode.S, cityUI.getRoadButton(),
                KeyCode.B, cityUI.getBulldozerButton(),
                KeyCode.ESCAPE, cityUI.getSelectionButton(),
                KeyCode.SPACE, playButton);

        setOnKeyPressed(e -> {
            if (buttonKeyMappings.containsKey(e.getCode())) {
                MyButton button = buttonKeyMappings.get(e.getCode());
                button.buttonPressed();
            }
        });
    }

}
