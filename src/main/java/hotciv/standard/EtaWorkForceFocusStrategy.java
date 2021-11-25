package hotciv.standard;

import java.util.*;
import hotciv.framework.*;

public class EtaWorkForceFocusStrategy implements WorkForceFocusStrategy {
    @Override
    public void produceInCity(Game game, Position p) {
        City city = game.getCityAt(p);
        String focus = city.getWorkforceFocus();

        Iterator<Position> posIterator = Utility.get8neighborhoodIterator(p);

        //Convert iterator to a list
        List<Position> positions = new ArrayList();
        while (posIterator.hasNext()) {
            positions.add(posIterator.next());
        }

        List<Tile> tileList = new ArrayList();
        for (Position pos : positions) {
            tileList.add(game.getTileAt(pos));
        }

        //Sort the list in descending order of food or resources
        TileComparator tileComp = new TileComparator(focus);
        Collections.sort(tileList, Collections.reverseOrder(tileComp));

        // Worker in city to produce 1 production and 1 food
        int productionTotal = 1;
        int foodTotal = 1;
        int workers = city.getSize() - 1;
        if (workers <= tileList.size()) {
            tileList = tileList.subList(0, workers);
        }

        //Add the production and food
        for (Tile t : tileList) {
            productionTotal += tileComp.productionConstants(t.getTypeString());
            foodTotal += tileComp.foodConstants(t.getTypeString());
        }

        //Update in city
        ((CityImpl)city).setTreasury(productionTotal);
        ((CityImpl)city).setFoodAmount(foodTotal);
    }
}