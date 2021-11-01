package hotciv.standard;
import hotciv.framework.*;
import hotciv.standard.GameImpl;
import java.util.Map;

public class ZetaWinningStrategy implements WinningStrategy{

    private WinningStrategy BetaWinningStrategy;
    private WinningStrategy EpsilonWinningStrategy;
    private WinningStrategy state;

    //constructor
    public ZetaWinningStrategy(WinningStrategy BetaWinningStrategy, WinningStrategy EpsilonWinningStrategy) {
        this.BetaWinningStrategy = BetaWinningStrategy;
        this.EpsilonWinningStrategy = EpsilonWinningStrategy;
        this.state = null;
    }


    public Player getStrategicWinner(GameImpl game) {
        if (game.getNumRounds() > 20 ) {
            state = EpsilonWinningStrategy;
        } else {
            state = BetaWinningStrategy;
        }
        return state.getStrategicWinner(game);
    }
}
