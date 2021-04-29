package simulation;

import javafx.scene.paint.Color;
import polis.MouseMovementTracker;
import simulation.Actor;
import tiles.bigPictureTile.BigPictureTile;

import java.util.Properties;

public class Goods extends Actor {

    private final BigPictureTile origin;

    public Goods(MouseMovementTracker mouseMovementTracker, int r, int k, Properties engineProperties, BigPictureTile origin) {
        super(mouseMovementTracker, r, k, engineProperties);
        this.origin=origin;
        setAge(Integer.parseInt(engineProperties.getProperty("goods.age")));
        setFill(Color.YELLOW);
    }

    @Override
    public void act() {
        continueSearching();
    }

    @Override
    public boolean isValid() {
        return getAge() > 0;
    }
}
