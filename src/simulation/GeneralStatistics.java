package simulation;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import java.util.ArrayList;
import java.util.List;

public class GeneralStatistics implements InfoPanelModel, Observable {

    private int currentInhabitants;
    private double maxInhabitants;

    private int currentJobs;
    private double maxJobs;

    private int currentCustomers;
    private double maxCustomers;
    private int currentGoods;
    private double maxGoods;


    @Override
    public List<Number> getStats() {
        return List.of(currentInhabitants, maxInhabitants, currentJobs, maxJobs,
                currentGoods, maxGoods, currentCustomers, maxCustomers);
    }

    @Override
    public String getType() {
        return "statistics";
    }

    public void setResidenceStats(int currentInhabitantsOld, int currentInhabitantsNew,
                                  double maxInhabitantsOld, double maxInhabitantsNew){
        currentInhabitants += (-currentInhabitantsOld + currentInhabitantsNew);
        maxInhabitants += (-maxInhabitantsOld + maxInhabitantsNew);
        fireInvalidationEvent();
    }

    public void setCustomerStats(int currentCustomersOld, int currentCustomersNew, double maxCustomersOld,
                                 double maxCustomersNew){
        currentCustomers += (-currentCustomersOld + currentCustomersNew);
        maxCustomers += (-maxCustomersOld + maxCustomersNew);
        fireInvalidationEvent();
    }

    public void setCurrentGoods(int currentGoodsOld, int currentGoodsNew){
        currentGoods += (-currentGoodsOld + currentGoodsNew);
        fireInvalidationEvent();
    }

    public void setMaxGoods(double maxGoodsOld, double maxGoodsNew){
        maxGoods += (-maxGoodsOld + maxGoodsNew);
        fireInvalidationEvent();
    }

    /**
     * De increase methoden worden enkel gebruikt bij het aanmaken van een BuildingTile
     */

    public void increaseMaxJobs(double maxJobs){
        this.maxJobs += maxJobs;
        fireInvalidationEvent();
    }

    public void increaseMaxInhabitants(double maxInhabitants){
        this.maxInhabitants += maxInhabitants;
        fireInvalidationEvent();
    }

    public void increaseMaxCustomers(double maxCustomers){
        this.maxCustomers += maxCustomers;
    }

    public void setCurrentJobs(int currentJobsOld, int currentJobsNew){
        currentJobs += (-currentJobsOld + currentJobsNew);
        fireInvalidationEvent();
    }

    public void setMaxJobs(double maxJobsOld, double maxJobsNew){
        maxJobs += (-maxJobsOld + maxJobsNew);
        fireInvalidationEvent();
    }

    private final List<InvalidationListener> listeners = new ArrayList<>();

    public void fireInvalidationEvent(){
        for (InvalidationListener listener:listeners){
            listener.invalidated(this);
        }
    }

    @Override
    public void addListener(InvalidationListener invalidationListener) {
        listeners.add(invalidationListener);
        fireInvalidationEvent();
    }

    @Override
    public void removeListener(InvalidationListener invalidationListener) {
        listeners.remove(invalidationListener);
    }
}
