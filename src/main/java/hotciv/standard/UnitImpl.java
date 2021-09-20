package hotciv.standard;

import hotciv.framework.*;

public class UnitImpl implements Unit{
    private String unitType;
    Player unitOwner;

    public UnitImpl(String unitType, Player unitOwner){
        this.unitType = unitType;
        this.unitOwner = unitOwner;
    }

    public String getTypeString(){
        return unitType;
    }

    public Player getOwner(){
        return unitOwner;
    }

    public int getMoveCount(){
        return 0;
    }

    public int getDefensiveStrength(){
        return 0;
    }

    public int getAttackingStrength(){
        return 0;
    }
}
