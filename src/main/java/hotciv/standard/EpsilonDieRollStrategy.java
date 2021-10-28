package hotciv.standard;

import hotciv.framework.DieRollStrategy;

public class EpsilonDieRollStrategy implements DieRollStrategy {
    @Override
    public int getDieRoll() {
        int dice = (int) (Math.random()*6+1);
        return dice;
    }
}
