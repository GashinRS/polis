package tiles.buildtingTiles;

public class IndustryTileFactory implements BuildingTileFactory {

    @Override
    public BuildingTile creatBuildingTile() {
        return new IndustryTile();
    }
}
