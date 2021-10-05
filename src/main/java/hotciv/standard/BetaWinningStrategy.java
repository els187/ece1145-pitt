package hotciv.standard;

import hotciv.framework.*;
import hotciv.framework.Player;

public class BetaWinningStrategy implements WinningStrategy {
    @Override
    public Player getStrategicWinner(GameImpl game) {
        for (Position p : game.cities.keySet()) {
            if(game.getCityAt(p).getOwner() != game.getPlayerInTurn()){
                return null;
            }
        }
        return game.getPlayerInTurn();
    }
}