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

    //https://www.tutorialspoint.com/how-to-add-an-image-to-a-button-action-in-javafx
    //afbeelding op buttons krijgen
    public CityUI(MouseMovementTracker mouseMovementTracker){
        int buttonWidth = 75;
        int buttonHeight = 50;
        ToggleButton roadButton = new RoadButton("road", buttonWidth, buttonHeight, mouseMovementTracker);
        ToggleButton selectionButton = new SelectionButton("selection", buttonWidth, buttonHeight, mouseMovementTracker);
        ToggleButton bulldozerButton = new BulldozerButton("bulldozer", buttonWidth, buttonHeight, mouseMovementTracker);
        ToggleButton commerceButton = new CommerceButton("commerce", buttonWidth, buttonHeight, mouseMovementTracker);
        ToggleButton industryButton = new IndustryButton("industry", buttonWidth, buttonHeight, mouseMovementTracker);
        ToggleButton residenceButton = new ResidenceButton("residence", buttonWidth, buttonHeight, mouseMovementTracker);

        ToggleGroup toggleGroup = new ToggleGroup();
        roadButton.setToggleGroup(toggleGroup);
        selectionButton.setToggleGroup(toggleGroup);
        bulldozerButton.setToggleGroup(toggleGroup);
        commerceButton.setToggleGroup(toggleGroup);
        industryButton.setToggleGroup(toggleGroup);;
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
}
