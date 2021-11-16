package hotciv.standard;
import hotciv.framework.*;

public class EpsilonWinningStrategy implements WinningStrategy {
    public Player getStrategicWinner(Game game) {
        if (((GameImpl)game).getRedPlayerWins() >= 3) {
            return Player.RED;
        } else if (((GameImpl)game).getBluePlayerWins() >= 3) {
            return Player.BLUE;
        } else {
            return null;
        }
    }
}