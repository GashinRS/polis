package simulation;

import polis.MouseMovementTracker;

public class Region {

    private final MouseMovementTracker mouseMovementTracker;
    private final int immigrantAge;
    private final double slowdownRate;
    private final SimulationEngine simulationEngine;

    public Region(MouseMovementTracker mouseMovementTracker, SimulationEngine simulationEngine) {
        this.mouseMovementTracker=mouseMovementTracker;
        this.immigrantAge=Integer.parseInt(simulationEngine.getEngineProperties().getProperty("immigrant.age"));
        this.simulationEngine=simulationEngine;
        this.slowdownRate=Double.parseDouble(simulationEngine.getEngineProperties().getProperty("region.factor.slow.down"));
    }

    public void makeImmigrant(){
        simulationEngine.addActor(new Immigrant(mouseMovementTracker, immigrantAge, this, simulationEngine.getEngineProperties()));
    }

    public void slowDown(){
        simulationEngine.setTempo(slowdownRate);
    }

}
