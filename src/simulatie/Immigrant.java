package simulatie;

import javafx.event.ActionEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import polis.MouseMovementTracker;

public class Immigrant extends Actor{

    public Immigrant(MouseMovementTracker mouseMovementTracker) {
        super(mouseMovementTracker);
        setFill(Color.GREY);
        setR(0);
        setK(14);
    }

    @Override
    public void act(ActionEvent ae) {
        move();
    }
}
