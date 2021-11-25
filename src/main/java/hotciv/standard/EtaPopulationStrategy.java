package hotciv.standard;
import hotciv.framework.*;

public class EtaPopulationStrategy implements PopulationStrategy {
    @Override
    public void increaseCitySize(City c){
        int currentPopulation = c.getSize();

        if(((CityImpl)c).getFoodAmount() >= (5 + (currentPopulation * 3)) && currentPopulation < 9 ){
            ((CityImpl)c).setSize(currentPopulation + 1);
            ((CityImpl)c).resetFoodAmount();
        }
    }
}
