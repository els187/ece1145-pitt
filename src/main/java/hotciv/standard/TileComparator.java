package hotciv.standard;
import hotciv.framework.*;

import java.util.Comparator;

public class TileComparator implements Comparator<Tile> {
    private String focus;

    public TileComparator(String focus) {
        this.focus = focus;
    }

    @Override
    public int compare(Tile t1, Tile t2) {
        int t1Value;
        int t2Value;
        if (focus.equals(GameConstants.foodFocus)) {
            t1Value = foodConstants(t1.getTypeString());
            t2Value = foodConstants(t2.getTypeString());
        } else if (focus.equals(GameConstants.productionFocus)) {
            t1Value = productionConstants(t1.getTypeString());
            t2Value = productionConstants(t2.getTypeString());
        } else {
            return 0;
        }

        return t1Value - t2Value;
    }

    public static int productionConstants(String tileType)
    {
        if(tileType.equals(GameConstants.FOREST))
        {
            return 3;
        }
        if(tileType.equals(GameConstants.HILLS))
        {
            return 2;
        }
        if(tileType.equals(GameConstants.MOUNTAINS))
        {
            return 1;
        }
        return 0;
    }

    public static int foodConstants(String tileType)
    {
        if(tileType.equals(GameConstants.PLAINS))
        {
            return 3;
        }
        if(tileType.equals(GameConstants.OCEANS))
        {
            return 1;
        }
        return 0;
    }
}
