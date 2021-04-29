package simulation;

import javafx.scene.paint.Color;
import polis.MouseMovementTracker;
import simulation.Actor;
import tiles.bigPictureTile.BigPictureTile;
import tiles.bigPictureTile.CommerceTile;

import java.util.List;
import java.util.Properties;

public class Goods extends Actor {

    private final BigPictureTile origin;
    private boolean shopFound;

    public Goods(MouseMovementTracker mouseMovementTracker, int r, int k, Properties engineProperties, BigPictureTile origin) {
        super(mouseMovementTracker, r, k, engineProperties);
        this.origin=origin;
        setAge(Integer.parseInt(engineProperties.getProperty("goods.age")));
        setFill(Color.YELLOW);
    }

    @Override
    public void act() {
        List<BigPictureTile> leftAndRightBuildings = getLeftAndRightBuildings();
        BigPictureTile buildingTile1 = leftAndRightBuildings.get(0);
        BigPictureTile buildingTile2 = leftAndRightBuildings.get(1);
        if (buildingTile1 != null && buildingTile1.isCommerce()){
            checkShop(buildingTile1);
        } else if (buildingTile2 != null && buildingTile2.isCommerce()){
            checkShop(buildingTile2);
        } else {
            continueSearching();
        }
    }

    public void checkShop(BigPictureTile buildingTile){
        CommerceTile shop = (CommerceTile) buildingTile;
        if (!shop.isAtMaxGoodsCapacity()){
            if (!shop.isActivated()){
                shop.activate();
            }
            shop.addGoods(this);
            shopFound = true;
            origin.changeCapacity(Double.parseDouble(getEngineProperties().getProperty("factor.goods.delivered")));
        } else {
            continueSearching();
        }
    }

    @Override
    public boolean isValid() {
        boolean isAgeValid = getAge() > 0;
        if (!isAgeValid){
            origin.changeCapacity(Double.parseDouble(getEngineProperties().getProperty("factor.goods.not.delivered")));
        }
        return getAge() > 0 && !shopFound;
    }
}
