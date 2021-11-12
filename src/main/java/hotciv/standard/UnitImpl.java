package hotciv.standard;

import hotciv.framework.*;

public class UnitImpl implements Unit{
    private String unitType;
    private Player unitOwner;
    private boolean fortified;
    private int attackStrength, defensiveStrength;
    private int moveCount = 1;
    private int ufoMoveCount = 2;

    public UnitImpl(String unitType, Player unitOwner){
        this.unitType = unitType;
        this.unitOwner = unitOwner;
        setStrengths();
        this.fortified = false;
    }

    public void setStrengths(){
        if (unitType.equals(GameConstants.ARCHER)){
            this.defensiveStrength = 3;
            attackStrength = 2;
        }  else if (unitType.equals(GameConstants.LEGION)) {
            defensiveStrength = 2;
            attackStrength = 4;
        } else if (unitType.equals(GameConstants.SETTLER)) {
            defensiveStrength = 3;
            attackStrength = 0;
        } else if(unitType.equals(GameConstants.UFO)) {
            this.defensiveStrength = 8;
            this.attackStrength = 1;
        }
    }

    public String getTypeString(){
        return unitType;
    }

    public Player getOwner(){
        return unitOwner;
    }

    public int getMoveCount(){
        if(unitType.equals(GameConstants.UFO)){
            return ufoMoveCount;
        } else {
            return moveCount;
        }
    }

    public void setMoveCount(int i) {
        if(unitType.equals(GameConstants.UFO)){
            ufoMoveCount = i;
        } else {
            moveCount = i;
        }
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

    public void setUnitMoveCount() {
        if (unitType.equals(GameConstants.UFO)) {
            ufoMoveCount -= 1;
        } else
            moveCount -= 1;
    }
}
