package hotciv.framework;

import java.util.Map;

public interface MapStrategy {
    public default Map<Position, Tile> defineWorld() {return null;}
}
