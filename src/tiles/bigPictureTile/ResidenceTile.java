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
    public void changeCapacity(double factor) {
        super.changeCapacity(factor);
        int capacity = (int) getCapacity();
        if (getActors().size() > getCapacity()){
            for (int i = capacity; i < getActors().size(); i++){
                getActors().remove(i);
            }
        }
    }
}
