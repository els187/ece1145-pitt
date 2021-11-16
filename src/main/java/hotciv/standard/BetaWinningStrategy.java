package hotciv.standard;

import hotciv.framework.*;
import hotciv.framework.Player;

public class BetaWinningStrategy implements WinningStrategy {
    @Override
    public Player getStrategicWinner(Game game) {
        for (Position p : ((GameImpl)game).cities.keySet()) {
            if(game.getCityAt(p).getOwner() != game.getPlayerInTurn()){
                return null;
            }
        }
        return game.getPlayerInTurn();
    }
}