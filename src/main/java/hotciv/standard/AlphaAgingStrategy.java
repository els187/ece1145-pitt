package hotciv.standard;

import hotciv.framework.*;

public class AlphaAgingStrategy implements AgingStrategy {
    @Override
    public int getStrategicAging(int currentAge){
        currentAge = currentAge + 100;
        return currentAge;
    }
}

