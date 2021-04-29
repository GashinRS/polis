package simulation;

import javafx.scene.paint.Color;
import polis.MouseMovementTracker;
import tiles.bigPictureTile.BigPictureTile;

import java.util.Properties;

public class Sleeper extends Actor{


    public Sleeper(MouseMovementTracker mouseMovementTracker, int r, int k, Properties engineProperties, int direction) {
        super(mouseMovementTracker, r, k, engineProperties, direction);
        setAge(Integer.parseInt(engineProperties.getProperty("sleeper.age")));
        setFill(Color.TRANSPARENT);
    }

    @Override
    public void act() {
        setAge(getAge()-1);
        if (getAge() == 0){
            setNewActor(new JobSeeker(getMouseMovementTracker(), getR(), getK(), getEngineProperties(), getDirection()));
        }
    }

    @Override
    public boolean isValid() {
        return getAge() > 0;
    }
}
