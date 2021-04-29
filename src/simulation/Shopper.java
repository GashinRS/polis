package simulation;

import javafx.scene.paint.Color;
import polis.MouseMovementTracker;
import tiles.bigPictureTile.BigPictureTile;

import java.util.List;
import java.util.Properties;

public class Shopper extends Actor {

    private boolean shoppingPlaceFound;

    public Shopper(MouseMovementTracker mouseMovementTracker, int r, int k, Properties engineProperties) {
        super(mouseMovementTracker, r, k, engineProperties);
        setFill(Color.LIGHTBLUE);
        setAge(Integer.parseInt(engineProperties.getProperty("shopper.age")));
    }

    @Override
    public void act() {
        List<BigPictureTile> leftAndRightBuildings = getLeftAndRightBuildings();
        BigPictureTile buildingTile1 = leftAndRightBuildings.get(0);
        BigPictureTile buildingTile2 = leftAndRightBuildings.get(1);
        if ( buildingTile1 != null && buildingTile1.isCommerce()){
            checkIfShopperCanBecomeCustomer(buildingTile1);
        } else if (buildingTile2 != null && buildingTile2.isCommerce()){
            checkIfShopperCanBecomeCustomer(buildingTile2);
        } else {
            continueSearching();
        }
    }

    public void checkIfShopperCanBecomeCustomer(BigPictureTile buildingTile){
        if (buildingTile.canAcceptCustomer()){
            Customer customer = new Customer(getMouseMovementTracker(), getR(), getK(), getEngineProperties());
            setNewActor(customer);
            System.out.println("bruh");
            shoppingPlaceFound = true;
            buildingTile.addActor(this);
            if (buildingTile.isAtMaxCapacity()){
                buildingTile.changeCapacity(Double.parseDouble(getEngineProperties().getProperty("factor.good.trade")));
            }
            getHome().changeCapacity(Double.parseDouble(getEngineProperties().getProperty("factor.shop.found")));
        } else {
            buildingTile.changeCapacity(Double.parseDouble(getEngineProperties().getProperty("factor.bad.trade")));
            continueSearching();
        }
    }

//    public void continueSearching(){
//        move();
//        setAge(getAge()-1);
//    }

    @Override
    public boolean isValid() {
        boolean isAgeValid = getAge() > 0;
        if (!isAgeValid){
            setNewActor(new Sleeper(getMouseMovementTracker(), getHomeLocation().getKey(),
                    getHomeLocation().getValue(), getEngineProperties()));
            getHome().changeCapacity(Double.parseDouble(getEngineProperties().getProperty("factor.shop.not.found")));
        }
        return getAge() > 0 && !shoppingPlaceFound;
    }
}
