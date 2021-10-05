package hotciv.standard;
import java.util.HashMap;
import hotciv.framework.*;


public class AlphaMapStrategy implements MapStrategy {

    private HashMap<Position, TileImpl> tiles;
    private HashMap<Position, UnitImpl> units;
    private HashMap<Position, CityImpl> cities;

    public AlphaMapStrategy() {
        tiles = new HashMap();
        units = new HashMap();
        cities = new HashMap();
    }

    @Override
    public HashMap<Position, TileImpl> defineTilesLayout() {

        for (int i = 0; i < GameConstants.WORLDSIZE; i++) {
            for (int j = 0; j < GameConstants.WORLDSIZE; j++) {
                tiles.put(new Position(i, j), new TileImpl(GameConstants.PLAINS));
            }
        }
        tiles.put(new Position(1, 0), new TileImpl(GameConstants.OCEANS));
        tiles.put(new Position(0, 1), new TileImpl(GameConstants.HILLS));
        tiles.put(new Position(2, 2), new TileImpl(GameConstants.MOUNTAINS));
        return tiles;
    }

    @Override
    public HashMap<Position, UnitImpl> defineUnitsLayout() {
        units.put(new Position(2, 0), new UnitImpl(GameConstants.ARCHER, Player.RED));
        units.put(new Position(3, 2), new UnitImpl(GameConstants.LEGION, Player.BLUE));
        units.put(new Position(4, 3), new UnitImpl(GameConstants.SETTLER, Player.RED));
        return units;
    }

    @Override
    public HashMap<Position, CityImpl> defineCitiesLayout() {
        cities.put(new Position(1, 1), new CityImpl(Player.RED));
        cities.put(new Position(4, 1), new CityImpl(Player.BLUE));
        return cities;
    }
}