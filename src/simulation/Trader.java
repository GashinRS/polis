package simulation;

import javafx.scene.paint.Color;
import polis.MouseMovementTracker;
import tiles.bigPictureTile.CommerceTile;

import java.util.Properties;

public class Trader extends Actor{

    private final CommerceTile shop;

    public Trader(MouseMovementTracker mouseMovementTracker, int r, int k, Properties engineProperties, CommerceTile shop, int direction) {
        super(mouseMovementTracker, r, k, engineProperties, direction);
        this.shop=shop;
        shop.addTrader(this);
        setFill(Color.TRANSPARENT);
        setAge(Integer.parseInt(engineProperties.getProperty("trader.age")));
    }

    @Override
    public void act() {
        setAge(getAge()-1);
    }

    @Override
    public boolean isValid() {
        boolean isAgeValid = getAge() > 0;
        if (!isAgeValid){
            shop.removeTrader(this);
            Shopper shopper = new Shopper(getMouseMovementTracker(), getHomeLocation().getKey(),
                    getHomeLocation().getValue(), getEngineProperties(), getDirection());
            setNewActor(shopper);
        }
        return isAgeValid;
    }
}
