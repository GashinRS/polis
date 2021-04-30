package simulation.actors;

import javafx.scene.paint.Color;
import polis.MouseMovementTracker;
import tiles.bigPictureTile.BigPictureTile;

import java.util.Properties;

public class Customer extends Actor {

    private final BigPictureTile shop;

    public Customer(MouseMovementTracker mouseMovementTracker, int r, int k, Properties engineProperties, BigPictureTile shop, int direction) {
        super(mouseMovementTracker, r, k, engineProperties, direction);
        setFill(Color.TRANSPARENT);
        this.shop=shop;
        setAge(Integer.parseInt(engineProperties.getProperty("customer.age")));
    }

    @Override
    public void act() {
        setAge(getAge()-1);
    }

    @Override
    public boolean isValid() {
        boolean isAgeValid = getAge() > 0;
        if (!isAgeValid){
            shop.removeActor(this);
            setNewActor(new Sleeper(getMouseMovementTracker(), getR(), getK(), getEngineProperties(), getDirection()));
        }
        return isAgeValid;
    }
}
