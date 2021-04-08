package tiles;

public abstract class RemovableTile extends Tile{

    private final int r;
    private final int k;

    public RemovableTile(int cellSize, int r, int k) {
        super(cellSize);
        this.r=r;
        this.k=k;
//        setViewOrder(-r -k -cellSize);
    }

    public abstract void removeThis();

    public int getR(){
        return r;
    }

    public int getK(){
        return k;
    }
}
