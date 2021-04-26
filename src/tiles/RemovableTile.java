package tiles;

public interface RemovableTile{

    void removeThis();

    //wordt enkel gebruikt in BigPictureTile, maar staat hier zodat een if vermeden kan worden bij selection mode
    void upgrade();
}
