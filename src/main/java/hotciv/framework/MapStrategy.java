package hotciv.framework;
import hotciv.standard.*;

import java.util.HashMap;

public interface MapStrategy {
    HashMap<Position, TileImpl> defineTilesLayout();
    HashMap<Position, UnitImpl> defineUnitsLayout();
    HashMap<Position, CityImpl> defineCitiesLayout();
}
