package tiles.buildtingTiles;

public class ResidenceTileFactory implements BuildingTileFactory {

    @Override
    public BuildingTile creatBuildingTile() {
        return new ResidenceTile();
    }
}
