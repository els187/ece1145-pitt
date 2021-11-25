package hotciv.standard;
import hotciv.framework.*;

public class AlphaWorkForceFocusStrategy implements WorkForceFocusStrategy {

    public void produceInCity(Game game, Position p){
        City city = game.getCityAt(p);
        ((CityImpl)city).setTreasury(6);
        ((CityImpl)city).setFoodAmount(1);
    }
}