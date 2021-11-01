package hotciv.framework;
import hotciv.standard.*;

import java.util.Map;

public interface WinningStrategy {
    public Player getStrategicWinner(GameImpl game);

    Player getStrategicWinner(Map<Position, CityImpl> cities, int age);
}
