package hotciv.standard;

import hotciv.framework.DieRollStrategy;

public class DieRollStub implements DieRollStrategy {
    private int fixedRoll = 1;

    @Override
    public int getDieRoll(){
        return fixedRoll;
    }
}
