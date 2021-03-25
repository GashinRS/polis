package polis;

import buttons.*;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
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
        Button roadButton = new RoadButton("road.png", buttonWidth, buttonHeight, mouseMovementTracker);
        Button selectionButton = new SelectionButton("selection.png", buttonWidth, buttonHeight, mouseMovementTracker);
        Button bulldozerButton = new BulldozerButton("bulldozer.png", buttonWidth, buttonHeight, mouseMovementTracker);
        Button commerceButton = new CommerceButton("commerce.png", buttonWidth, buttonHeight, mouseMovementTracker);
        Button industryButton = new IndustryButton("industry.png", buttonWidth, buttonHeight, mouseMovementTracker);
        Button residenceButton = new ResidenceButton("residence.png", buttonWidth, buttonHeight, mouseMovementTracker);
        this.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        this.getChildren().addAll(newRow(List.of(residenceButton, industryButton, commerceButton)),
                newRow(List.of(roadButton, bulldozerButton)),
                selectionButton);
        this.setMaxSize(300,200);
        this.setSpacing(10);
    }

    /**
     * Deze methode maakt een rij aan voor de VBox, bestaande uit een HBox met daarin de buttons. Dit maakt het
     * makkelijker om later nog nieuwe buttons toe te voegen aan een rij.
     */

    private HBox newRow(List<Button> buttons){
        HBox hBox = new HBox();
        for (Button button: buttons) {
            hBox.getChildren().add(button);
        }
        hBox.setSpacing(10);
        return hBox;
    }
}
