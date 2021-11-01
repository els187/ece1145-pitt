package hotciv.standard;
import hotciv.framework.*;

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

    //determine winning strategy


    @Override
    public Player getStrategicWinner(GameImpl game) {
        return null;
    }



    /* @Override //BetaCiv Winning Strat: first player to conquer all in the world
    public Player getStrategicWinner(GameImpl game) {

        //while num rounds is < 20
        for (Position p : game.cities.keySet()) {
            if(game.getCityAt(p).getOwner() != game.getPlayerInTurn()){
                return null;
            }
        }

        //EpsilonCiv --
        // if (numRounds > 20) { if game lasts longer than 20 rounds
        if (game.getRedPlayerWins() >= 3) {
            return Player.RED;
        } else if (game.getBluePlayerWins() >= 3) {
            return Player.BLUE;
        } else {
            return null;
        }
    //return game.getPlayerInTurn();
    */
}
