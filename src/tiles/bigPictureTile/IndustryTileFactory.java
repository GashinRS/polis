package tiles.bigPictureTile;

public class IndustryTileFactory implements BigPictureTileFactory{

    @Override
    public BigPictureTile createBigPictureTile() {
        return new IndustryTile();
    }
}
