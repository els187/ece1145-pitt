package hotciv.standard;
import hotciv.framework.*;
import hotciv.framework.CityStart;
import java.util.*;
public class DeltaCityStart implements CityStart {

    Map<Position,City> citySetUp = new HashMap<Position,City>();
    public Map<Position,City> setCityCoords(){
        //Create new cities for Blue and Red players.
        citySetUp.put(new Position(4,5), new CityImpl(Player.BLUE, new Position(4,5)));
        citySetUp.put(new Position(8,12), new CityImpl(Player.RED, new Position(8,12)));
        return citySetUp;
    }

}
