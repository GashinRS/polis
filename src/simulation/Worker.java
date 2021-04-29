package simulation;

import javafx.scene.paint.Color;
import polis.MouseMovementTracker;
import tiles.bigPictureTile.BigPictureTile;

import java.util.Properties;

public class Worker extends Actor{

    private final BigPictureTile workplace;

    public Worker(MouseMovementTracker mouseMovementTracker, int r, int k, Properties engineProperties, BigPictureTile workplace) {
        super(mouseMovementTracker, r, k, engineProperties);
        this.workplace = workplace;
        setFill(Color.TRANSPARENT);
        setAge(Integer.parseInt(engineProperties.getProperty("worker.age")));
    }

    @Override
    public void act() {
        setAge(getAge()-1);
        if (getAge() % Integer.parseInt(getEngineProperties().getProperty("steps.per.goods")) == 0){
            getNewActor().add(new Goods(getMouseMovementTracker(), getR(), getK(), getEngineProperties(), workplace));
        }
    }

    @Override
    public boolean isValid() {
        boolean isAgeValid = getAge() > 0;
        if (!isAgeValid) {
            workplace.removeActor(this);
            Shopper shopper = new Shopper(getMouseMovementTracker(), getHomeLocation().getKey(),
                    getHomeLocation().getValue(), getEngineProperties());
            setNewActor(shopper);
        }
        return isAgeValid;
    }
}
