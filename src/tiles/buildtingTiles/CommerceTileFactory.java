package tiles.buildtingTiles;

public class CommerceTileFactory implements BuildingTileFactory {

    @Override
    public BuildingTile creatBuildingTile() {
        return new CommerceTile();
    }
}
