package tiles.bigPictureTile;

public class CommerceTileFactory implements BigPictureTileFactory{

    @Override
    public BigPictureTile createBigPictureTile() {
        return new CommerceTile();
    }
}
