package simulation.actors;

import javafx.scene.paint.Color;
import polis.MouseMovementTracker;
import tiles.bigPictureTile.BigPictureTile;

import java.util.Properties;

public class Worker extends Actor {

    private final BigPictureTile workplace;

    public Worker(MouseMovementTracker mouseMovementTracker, int r, int k, Properties engineProperties, BigPictureTile workplace, int direction) {
        super(mouseMovementTracker, r, k, engineProperties, direction);
        this.workplace = workplace;
        setFill(Color.TRANSPARENT);
        setAge(Integer.parseInt(engineProperties.getProperty("worker.age")));
    }

    @Override
    public void act() {
        setAge(getAge()-1);
        if (getAge() % Integer.parseInt(getEngineProperties().getProperty("steps.per.goods")) == 0 && workplace.isExisting()){
            getNewActor().add(new Goods(getMouseMovementTracker(), getR(), getK(), getEngineProperties(), workplace, getDirection()));
        }
    }

    @Override
    public boolean isValid() {
        boolean isAgeValid = getAge() > 0;
        if (!isAgeValid) {
            workplace.removeActor(this);
            Shopper shopper = new Shopper(getMouseMovementTracker(), getHomeLocation().getKey(),
                    getHomeLocation().getValue(), getEngineProperties(), getDirection());
            setNewActor(shopper);
        }
        return isAgeValid;
    }
}
