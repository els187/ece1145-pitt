package hotciv.standard;
import hotciv.framework.*;
import java.util.*;
public class AlphaWinningStrategy implements WinningStrategy {
    @Override
    public Player getStrategicWinner(Game game) {
        if (game.getAge() == -3000) {
            return Player.RED;
        }
        return null;
    }
}
