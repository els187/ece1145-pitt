package hotciv.framework;
import hotciv.standard.*;

import java.util.HashMap;

public interface MapStrategy {
    HashMap<Position, Tile> defineTilesLayout();
    HashMap<Position, Unit> defineUnitsLayout();
    HashMap<Position, City> defineCitiesLayout();
}
