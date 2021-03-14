package polis;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import prog2.util.Viewport;

public class CityMap extends HBox {
    private Viewport viewport;
    private CityArea cityArea;
    public CityMap(){
        this.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.getChildren().add(new Viewport(new CityArea(), 0.5));
        this.setPrefSize(1500,1000);
    }
}
