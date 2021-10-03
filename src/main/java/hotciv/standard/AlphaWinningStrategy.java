package hotciv.standard;
import hotciv.framework.*;

public class AlphaWinningStrategy implements WinningStrategy {
    @Override
    public Player getStrategicWinner(GameImpl game) {
        if (game.getAge() == -3000) {
            return Player.RED;
        }
        return null;
    }
}
