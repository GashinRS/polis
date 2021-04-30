package simulation;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import java.util.ArrayList;
import java.util.List;

/**
 * Dit is het model voor de algemene statistieken over heel de simulatie. Elk gebouw houdt dit bij zodat de waarden
 * direct geupdate worden wanneer nodig.
 */

public class GeneralStatistics implements InfoPanelModel, Observable {

    //residence informatie
    private int currentInhabitants;
    private double maxInhabitants;

    //gedeelde informatie tussen industry en commerce voor jobs
    private int currentJobs;
    private double maxJobs;

    //commerce informatie
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


    public void setCurrentJobs(int currentJobsOld, int currentJobsNew){
        currentJobs += (-currentJobsOld + currentJobsNew);
        fireInvalidationEvent();
    }

    public void setMaxJobs(double maxJobsOld, double maxJobsNew){
        maxJobs += (-maxJobsOld + maxJobsNew);
        fireInvalidationEvent();
    }

    private final List<InvalidationListener> listeners = new ArrayList<>();

    private void fireInvalidationEvent(){
        for (InvalidationListener listener:listeners){
            listener.invalidated(this);
        }
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
