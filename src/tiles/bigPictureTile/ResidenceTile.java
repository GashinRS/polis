package tiles.bigPictureTile;

import simulation.Actor;

public class ResidenceTile extends BigPictureTile {

    public ResidenceTile() {
        super("residence");
    }

    @Override
    public boolean isResidence(){
        return true;
    }

    @Override
    public void replaceResident(Actor oldResident, Actor newResident) {
        getActors().remove(oldResident);
        getActors().add(newResident);
    }
}
