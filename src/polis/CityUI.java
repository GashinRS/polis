package polis;

import buttons.*;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import java.io.InputStream;
import java.util.List;

/**
 * Dit is de main UI van het programma die alle buttons (behalve de play button) bevat om de simulatie mee te kunnen uitvoeren
 */

public class CityUI extends VBox {

    private Button roadButton;
    private Button selectionButton;
    private Button bulldozerButton;
    private Button commerceButton;
    private Button industryButton;
    private Button residenceButton;

    //https://www.tutorialspoint.com/how-to-add-an-image-to-a-button-action-in-javafx
    //afbeelding op buttons krijgen
    public CityUI(){
        int buttonWidth = 75;
        int buttonHeight = 50;
        try (InputStream roadIn = this.getClass().getResourceAsStream("/polis/buttons/road.png");
             InputStream selectionIn = this.getClass().getResourceAsStream("/polis/buttons/selection.png");
             InputStream bulldozerIn = this.getClass().getResourceAsStream("/polis/buttons/bulldozer.png");
             InputStream commerceIn = this.getClass().getResourceAsStream("/polis/buttons/commerce.png");
             InputStream industryIn = this.getClass().getResourceAsStream("/polis/buttons/industry.png");
             InputStream residenceIn = this.getClass().getResourceAsStream("/polis/buttons/residence.png")){
            roadButton = new RoadButton(new ImageView(new Image(roadIn)), buttonWidth, buttonHeight);
            selectionButton = new SelectionButton(new ImageView(new Image(selectionIn)), buttonWidth, buttonHeight);
            bulldozerButton = new BulldozerButton(new ImageView(new Image(bulldozerIn)), buttonWidth, buttonHeight);
            commerceButton = new CommerceButton(new ImageView(new Image(commerceIn)), buttonWidth, buttonHeight);
            industryButton = new IndustryButton(new ImageView(new Image(industryIn)), buttonWidth, buttonHeight);
            residenceButton = new ResidenceButton(new ImageView(new Image(residenceIn)), buttonWidth, buttonHeight);
        } catch (Exception ex){
            System.err.println("bestand niet gevonden");
        }
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
