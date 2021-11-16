package hotciv.standard;
import hotciv.framework.*;

public class ZetaWinningStrategy implements WinningStrategy{

    BetaWinningStrategy betaWinner;
    EpsilonWinningStrategy epsilonWinner;

    //constructor
    public ZetaWinningStrategy(BetaWinningStrategy betaWinningStrategy, EpsilonWinningStrategy epsilonWinningStrategy) {
        this.betaWinner = betaWinningStrategy;
        this.epsilonWinner = epsilonWinningStrategy;
    }

    @Override
    public Player getStrategicWinner(Game game) {
        if (game.getNumRounds() >= 20 ) {
            return epsilonWinner.getStrategicWinner(game);
        } else if(game.getNumRounds() < 20){
            return betaWinner.getStrategicWinner(game);
        }
        return null;
    }
}
