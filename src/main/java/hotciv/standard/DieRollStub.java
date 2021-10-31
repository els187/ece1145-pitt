package hotciv.standard;

import hotciv.framework.DieRollStrategy;

public class DieRollStub implements DieRollStrategy {

    @Override
    public int getDieRoll(){
        return 1;
    }
}
