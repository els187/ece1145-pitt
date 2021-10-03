package hotciv.standard;
import hotciv.framework.*;
import java.util.*;
public class DeltaCityStart implements CityStart {

    Map<Position,CityImpl> citySetUp = new HashMap<Position,CityImpl>();
    public Map<Position,CityImpl> setCityCoords(){
        //Create new cities for Blue and Red players.
        citySetUp.put(new Position(4,5), new CityImpl(Player.BLUE, new Position(4,5)));
        citySetUp.put(new Position(8,12), new CityImpl(Player.RED, new Position(8,12)));
        return citySetUp;
    }

}
