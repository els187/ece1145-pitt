package hotciv.framework;
import hotciv.standard.*;

public interface WinningStrategy {
    public Player getStrategicWinner(GameImpl game);
}
