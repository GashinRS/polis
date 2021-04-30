package simulation.actors;

import javafx.scene.paint.Color;
import polis.MouseMovementTracker;

import java.util.Properties;

public class Sleeper extends Actor {

    public Sleeper(MouseMovementTracker mouseMovementTracker, int r, int k, Properties engineProperties, int direction) {
        super(mouseMovementTracker, r, k, engineProperties, direction);
        setAge(Integer.parseInt(engineProperties.getProperty("sleeper.age")));
        setFill(Color.TRANSPARENT);
    }

    @Override
    public void act() {
        setAge(getAge()-1);
    }

    @Override
    public boolean isValid() {
        boolean isAgeValid = getAge() > 0;
        if (!isAgeValid && getHome().isExisting()){
            if (getHome().getActors().indexOf(this) > getHome().getCapacity()){
                getHome().getActors().remove(this);
            } else {
                setNewActor(new JobSeeker(getMouseMovementTracker(), getR(), getK(), getEngineProperties(), getDirection()));
            }
        }
        return isAgeValid && getHome().isExisting();
    }
}
