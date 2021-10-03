package hotciv.standard;

import hotciv.framework.*;
import hotciv.framework.Player;

public class BetaWinningStrategy implements WinningStrategy{
    public Player winner;
    @Override
    public Player getStrategicWinner(GameImpl game) {
        winner = Player.RED;
        for(Position p: game.getCities().keySet()){
            CityImpl city = (CityImpl) game.getCityAt(p);
            if(!city.getOwner().equals(winner)){
                winner = city.getOwner();
            }
        }
        return winner;
    }
}
