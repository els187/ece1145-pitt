package hotciv.standard;

import hotciv.framework.*;
import java.util.*;

public class DeltaMapStrategy implements MapStrategy {

    // A simple implementation to draw the map of DeltaCiv
    public HashMap<Position, Tile> tiles;
    private HashMap<Position, Unit> units;
    private HashMap<Position, City> cities;
    private String[] layout;

    /**
     * Define the world as the DeltaCiv layout
     */
    public DeltaMapStrategy(){
        tiles = new HashMap<>();
        units = new HashMap<>();
        cities = new HashMap<>();

        // Basically we use a 'data driven' approach - code the
        // layout in a simple semi-visual representation, and
        // convert it to the actual Game representation.
        this.layout =
                new String[]{
                        "...ooMooooo.....",
                        "..ohhoooofffoo..",
                        ".oooooMooo...oo.",
                        ".ooMMMoooo..oooo",
                        "...ofooohhoooo..",
                        ".ofoofooooohhoo.",
                        "...ooo..........",
                        ".ooooo.ooohooM..",
                        ".ooooo.oohooof..",
                        "offfoooo.offoooo",
                        "oooooooo...ooooo",
                        ".ooMMMoooo......",
                        "..ooooooffoooo..",
                        "....ooooooooo...",
                        "..ooohhoo.......",
                        ".....ooooooooo..",
                };
    }
    // Conversion...
    @Override
    public HashMap<Position, Tile> defineTilesLayout () {

        String line;
        for (int r = 0; r < GameConstants.WORLDSIZE; r++) {
            line = layout[r];
            for (int c = 0; c < GameConstants.WORLDSIZE; c++) {
                char tileChar = line.charAt(c);
                String type = "error";
                if (tileChar == '.') {
                    type = GameConstants.OCEANS;
                }
                if (tileChar == 'o') {
                    type = GameConstants.PLAINS;
                }
                if (tileChar == 'M') {
                    type = GameConstants.MOUNTAINS;
                }
                if (tileChar == 'f') {
                    type = GameConstants.FOREST;
                }
                if (tileChar == 'h') {
                    type = GameConstants.HILLS;
                }
                Position p = new Position(r, c);
                tiles.put(p, new TileImpl(type));
            }
        }
        return tiles;
    }

    @Override
    public HashMap<Position, Unit> defineUnitsLayout () {
        units.put(new Position(2, 1), new UnitImpl(GameConstants.ARCHER, Player.RED));
        units.put(new Position(3, 2), new UnitImpl(GameConstants.LEGION, Player.BLUE));
        units.put(new Position(4, 3), new UnitImpl(GameConstants.SETTLER, Player.RED));
        units.put(new Position(6,4), new UnitImpl(GameConstants.UFO, Player.BLUE));
        return units;
    }

    @Override
    public HashMap<Position, City> defineCitiesLayout () {
        cities.put(new Position(8,12), new CityImpl(Player.RED));
        cities.put(new Position(4,5), new CityImpl(Player.BLUE));
        return cities;
    }
}