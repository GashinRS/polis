package tiles.bigPictureTile;

import polis.MouseMovementTracker;
import simulation.Actor;
import simulation.Goods;
import simulation.Trader;

import java.util.ArrayList;
import java.util.List;

public class CommerceTile extends BigPictureTile {

    private double customersPerTrader;
    private double goodsPerCustomer;
    private double jobCapacity;
    private double goodsCapacity;
    private final List<Trader> traders = new ArrayList<>();
    private final List<Goods> goods = new ArrayList<>();
    private int reservedGoods;

    public CommerceTile() {
        super("commerce");
    }

    @Override
    public void initialize(MouseMovementTracker mouseMovementTracker, int r, int k){
        super.initialize(mouseMovementTracker, r, k);
        customersPerTrader = Double.parseDouble(getMouseMovementTracker().getEngineProperties().getProperty("customers.per.trader"));
        goodsPerCustomer = Double.parseDouble(getMouseMovementTracker().getEngineProperties().getProperty("goods.per.customer"));
        setJobCapacity();
        setGoodsCapacity();
    }

    @Override
    public boolean isCommerce(){
        return true;
    }

    public void addTrader(Trader trader){
        traders.add(trader);
    }

    public void removeTrader(Trader trader){
        traders.remove(trader);
    }

    public List<Trader> getTraders(){
        return traders;
    }

    public void addGoods(Goods goods){
        this.goods.add(goods);
    }

    public void removeGoods(){
        this.goods.remove(0);
    }

    public List<Goods> getGoods(){
        return goods;
    }

    public void setJobCapacity(){
        jobCapacity = Math.max(getMinimalCapcity(), getCapacity()/customersPerTrader);
    }

    public boolean isAtMaxJobCapacity(){
        return traders.size() >= jobCapacity;
    }

    public void setGoodsCapacity(){
        goodsCapacity = Math.max(getMinimalCapcity(), getCapacity()*goodsPerCustomer);
    }

    public boolean isAtMaxGoodsCapacity(){
        return goods.size() >= goodsCapacity;
    }

    @Override
    public void changeCapacity(double factor){
        super.changeCapacity(factor);
        setGoodsCapacity();
        setJobCapacity();
    }

    @Override
    public boolean canAcceptCustomer(){
        return goods.size() > reservedGoods && traders.size() > 0;
    }

    @Override
    public void addActor(Actor actor) {
        super.addActor(actor);
        reservedGoods++;
    }

    @Override
    public void removeActor(Actor actor){
        super.removeActor(actor);
        removeGoods();
        reservedGoods -= 1;
    }
}
