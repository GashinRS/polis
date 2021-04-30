package polis;

import buttons.*;
import javafx.geometry.Insets;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.List;

/**
 * Dit is de main UI van het programma die alle buttons (behalve de play button) bevat om de simulatie mee te kunnen uitvoeren
 */

public class CityUI extends VBox {

    private final GameButton roadButton;
    private final GameButton selectionButton;
    private final GameButton bulldozerButton;
    private final GameButton commerceButton;
    private final GameButton industryButton;
    private final GameButton residenceButton;

    public CityUI(MouseMovementTracker mouseMovementTracker){
        int buttonWidth = 75;
        int buttonHeight = 50;
        roadButton = new RoadButton("road", buttonWidth, buttonHeight, mouseMovementTracker);
        selectionButton = new SelectionButton("selection", buttonWidth, buttonHeight, mouseMovementTracker);
        bulldozerButton = new BulldozerButton("bulldozer", buttonWidth, buttonHeight, mouseMovementTracker);
        commerceButton = new CommerceButton("commerce", buttonWidth, buttonHeight, mouseMovementTracker);
        industryButton = new IndustryButton("industry", buttonWidth, buttonHeight, mouseMovementTracker);
        residenceButton = new ResidenceButton("residence", buttonWidth, buttonHeight, mouseMovementTracker);

        ToggleGroup toggleGroup = new ToggleGroup();
        roadButton.setToggleGroup(toggleGroup);
        selectionButton.setToggleGroup(toggleGroup);
        bulldozerButton.setToggleGroup(toggleGroup);
        commerceButton.setToggleGroup(toggleGroup);
        industryButton.setToggleGroup(toggleGroup);
        residenceButton.setToggleGroup(toggleGroup);

        this.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        this.getChildren().addAll(new Row(List.of(residenceButton, industryButton, commerceButton)),
                new Row(List.of(roadButton, bulldozerButton)),
        selectionButton);
        this.setMaxSize(300,200);
        this.setSpacing(10);
    }

    /**
     * Deze binnenklasse is een rij van VBox, bestaande uit een HBox met daarin de buttons. Dit maakt het
     * makkelijker om later nog nieuwe buttons toe te voegen aan een rij.
     */

    private static class Row extends HBox{
        Row(List<ToggleButton> buttons){
            for (ToggleButton button: buttons) {
            getChildren().add(button);
            }
            setSpacing(10);
        }
    }

    public GameButton getRoadButton() {
        return roadButton;
    }

    public GameButton getSelectionButton() {
        return selectionButton;
    }

    public GameButton getBulldozerButton() {
        return bulldozerButton;
    }

    public GameButton getCommerceButton() {
        return commerceButton;
    }

    public GameButton getIndustryButton() {
        return industryButton;
    }

    public GameButton getResidenceButton() {
        return residenceButton;
    }
}
