package hotciv.standard;
import hotciv.framework.*;

public class EpsilonWinningStrategy implements WinningStrategy {
    public Player getStrategicWinner(GameImpl game) {
        if (game.getRedPlayerWins() >= 3) {
            return Player.RED;
        } else if (game.getBluePlayerWins() >= 3) {
            return Player.BLUE;
        } else {
            return null;
        }
    }
}
