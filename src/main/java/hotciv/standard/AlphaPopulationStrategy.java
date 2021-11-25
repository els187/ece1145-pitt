package hotciv.standard;

import hotciv.framework.City;
import hotciv.framework.PopulationStrategy;

public class AlphaPopulationStrategy implements PopulationStrategy {

    @Override
    public void increaseCitySize(City c) {
        ((CityImpl)c).setSize(1);
    }
}
