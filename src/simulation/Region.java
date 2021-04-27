package simulation;

import polis.MouseMovementTracker;


public class Region {

    private final MouseMovementTracker mouseMovementTracker;
    private final int immigrantAge;
    private final double slowdownRate;
    private final SimulationEngine simulationEngine;

    public Region(MouseMovementTracker mouseMovementTracker, int immigrantAge,
                  SimulationEngine simulationEngine, double slowdownRate) {
        this.mouseMovementTracker=mouseMovementTracker;
        this.immigrantAge=immigrantAge;
        this.simulationEngine=simulationEngine;
        this.slowdownRate=slowdownRate;
    }

    public void makeImmigrant(){
        simulationEngine.addActor(new Immigrant(mouseMovementTracker, immigrantAge, this));
    }

    public void slowDown(){
        simulationEngine.setTempo(slowdownRate);
    }

}
