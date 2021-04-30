package tiles.bigPictureTile;

import polis.MouseMovementTracker;
import simulation.GeneralStatistics;
import simulation.InfoPanel;
import simulation.actors.Actor;
import simulation.actors.Goods;
import simulation.actors.Trader;

import java.util.ArrayList;
import java.util.List;

public class CommerceTile extends BigPictureTile {

    private double customersPerTrader;
    private double goodsPerCustomer;
    private double jobCapacity;
    private double goodsCapacity;
    private final List<Trader> traders = new ArrayList<>();
    private final List<Goods> goods = new ArrayList<>();

    public CommerceTile() {
        super("commerce");
    }

    @Override
    public void initialize(MouseMovementTracker mouseMovementTracker, int r, int k, GeneralStatistics generalStatistics){
        super.initialize(mouseMovementTracker, r, k, generalStatistics);
        customersPerTrader = Double.parseDouble(getMouseMovementTracker().getEngineProperties().getProperty("customers.per.trader"));
        goodsPerCustomer = Double.parseDouble(getMouseMovementTracker().getEngineProperties().getProperty("goods.per.customer"));
        getGeneralStatistics().increaseMaxCustomers(getCapacity());
        setJobCapacity();
        setGoodsCapacity();
    }

    @Override
    public boolean isCommerce(){
        return true;
    }

    @Override
    public void changeGeneralStatistics(int oldValue, int newValue, double oldValue2, double newValue2) {
        getGeneralStatistics().setCustomerStats(oldValue, newValue, oldValue2, newValue2);
    }


    public void addTrader(Trader trader){
        traders.add(trader);
        getGeneralStatistics().setCurrentJobs(traders.size()-1, traders.size());
    }

    public void removeTrader(Trader trader){
        traders.remove(trader);
        getGeneralStatistics().setCurrentJobs(traders.size(), traders.size()-1);
    }

    public void addGoods(Goods goods){
        this.goods.add(goods);
        getGeneralStatistics().setCurrentGoods(this.goods.size()-1, this.goods.size());
    }

    public void removeGoods(){
        this.goods.remove(0);
        getGeneralStatistics().setCurrentGoods(this.goods.size(), this.goods.size()-1);
    }

    public void setJobCapacity(){
        double oldJobCapacity = jobCapacity;
        jobCapacity = Math.max(getMinimalCapcity(), getCapacity()/customersPerTrader);
        getGeneralStatistics().setMaxJobs(oldJobCapacity, jobCapacity);

    }

    public boolean isAtMaxJobCapacity(){
        return traders.size() >= Math.floor(jobCapacity);
    }

    public void setGoodsCapacity(){
        double oldGoodsCapacity = goodsCapacity;
        goodsCapacity = Math.max(getMinimalCapcity(), getCapacity()*goodsPerCustomer);
        getGeneralStatistics().setMaxGoods(oldGoodsCapacity, goodsCapacity);
    }

    public boolean isAtMaxGoodsCapacity(){
        return goods.size() >= Math.floor(goodsCapacity);
    }

    @Override
    public void changeCapacity(double factor){
        super.changeCapacity(factor);
        setGoodsCapacity();
        setJobCapacity();
    }

    @Override
    public boolean canAcceptCustomer(){
        return goods.size() > getActors().size() && traders.size()*customersPerTrader > getActors().size();
    }


    @Override
    public void removeActor(Actor actor){
        super.removeActor(actor);
        removeGoods();
    }

    @Override
    public List<Number> getStats() {
        return List.of(traders.size(), jobCapacity, goods.size(), goodsCapacity, getActors().size(), getCapacity());
    }

    @Override
    public void removeThis() {
        super.removeThis();
        getGeneralStatistics().setMaxJobs(jobCapacity, 0);
        getGeneralStatistics().setMaxGoods(goodsCapacity, 0);
        getGeneralStatistics().setCurrentGoods(goods.size(), 0);
        getGeneralStatistics().setCurrentJobs(traders.size(), 0);
    }
}
