package tiles.bigPictureTile;

public class ResidenceTileFactory implements BigPictureTileFactory{

    @Override
    public BigPictureTile createBigPictureTile() {
        return new ResidenceTile();
    }
}
