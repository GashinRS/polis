package simulation.actors;

import javafx.scene.paint.Color;
import polis.MouseMovementTracker;
import tiles.buildtingTiles.BuildingTile;
import tiles.buildtingTiles.CommerceTile;

import java.util.List;
import java.util.Properties;

public class Goods extends Actor {

    private final BuildingTile origin;
    private boolean shopFound;

    public Goods(MouseMovementTracker mouseMovementTracker, int r, int k, Properties engineProperties, BuildingTile origin, int direction) {
        super(mouseMovementTracker, r, k, engineProperties, direction);
        this.origin=origin;
        setAge(Integer.parseInt(engineProperties.getProperty("goods.age")));
        setFill(Color.YELLOW);
    }

    @Override
    public void act() {
        List<BuildingTile> leftAndRightBuildings = getLeftAndRightBuildings();
        BuildingTile buildingTile1 = leftAndRightBuildings.get(0);
        BuildingTile buildingTile2 = leftAndRightBuildings.get(1);
        if (buildingTile1 != null && buildingTile1.isCommerce()){
            checkShop(buildingTile1);
        } else if (buildingTile2 != null && buildingTile2.isCommerce()){
            checkShop(buildingTile2);
        } else {
            continueSearching();
        }
    }

    /**
     * Als een winkel genoeg capaciteit heeft om goederen te aanvaarden wordt het goed toegevoegd aan de winkel,
     * anders zoekt het goed verder naar een andere winkel
     */

    private void checkShop(BuildingTile buildingTile){
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
