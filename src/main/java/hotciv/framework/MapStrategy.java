package hotciv.framework;
import hotciv.standard.*;
import java.util.Map;

public interface MapStrategy {
    public default Map<Position, Tile> defineWorld() {return null;}
}
