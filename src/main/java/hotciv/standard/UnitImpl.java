package hotciv.standard;

import hotciv.framework.*;

public class UnitImpl implements Unit{
    private String unitType;
    private Player unitOwner;
    private boolean fortified;
    private int attackStrength;
    private int defensiveStrength;
    private int moveCount;

    public UnitImpl(String unitType, Player unitOwner){
        this.unitType = unitType;
        this.unitOwner = unitOwner;
        this.moveCount = 1;
        this.fortified = false;

        if (unitType.equals(GameConstants.ARCHER)){
            defensiveStrength = 3;
            attackStrength = 2;
        }  else if (unitType.equals(GameConstants.LEGION)) {
            defensiveStrength = 2;
            attackStrength = 4;
        } else if (unitType.equals(GameConstants.SETTLER)) {
            defensiveStrength = 3;
            attackStrength = 0;
        }
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

    public void setMoveCount(int i) {
        moveCount = i;
    }

    public int getDefensiveStrength(){
        return defensiveStrength;
    }

    public int getAttackingStrength(){
        return attackStrength;
    }

    public void fortify(){
        defensiveStrength *= 2;
        fortified = true;

    }

    public void deFortify() {
        defensiveStrength /= 2;
        fortified = false;
    }

    public boolean isFortified(){
        if(fortified == true){
            return true;
        } else {
            return false;
        }
    }
}
