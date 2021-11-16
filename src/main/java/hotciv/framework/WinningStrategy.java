package hotciv.framework;
import hotciv.standard.*;

import java.util.Map;

public interface WinningStrategy {
    public Player getStrategicWinner(Game game);
}
