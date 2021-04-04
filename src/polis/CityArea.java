package polis;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import tiles.Area;

import java.io.InputStream;


public class CityArea extends Pane {

    private MouseMovementTracker mouseMovementTracker;

    public CityArea(){
        this.setPrefWidth(64 * 2 * 32);
        this.setPrefHeight(64 * 32);
        this.setFocusTraversable(true);

        Area area = new Area();
        area.setTranslateX(64 * 32);
        area.setTranslateY(0);

        this.getChildren().add(area);

//        try (InputStream in = this.getClass().getResourceAsStream("/polis/tiles/industry-3.png")){
//            //ImagePattern imagePattern = new ImagePattern(new Image(in));
//            ImageView imageView = new ImageView(new Image(in));
//            getChildren().add(imageView);
//            imageView.setTranslateX(64 * (32 - 1 + 1));
//            imageView.setTranslateY(64 * (1 + 1) / 2);
//        } catch (Exception ex) {
//            System.err.println("bestand niet gevonden");
//        }
    }

    public void setMouseMovementTracker(MouseMovementTracker mouseMovementTracker) {
        this.mouseMovementTracker = mouseMovementTracker;
        this.getChildren().add(mouseMovementTracker);
    }

    public MouseMovementTracker getMouseMovementTracker() {
        return mouseMovementTracker;
    }
}
