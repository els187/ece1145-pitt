package hotciv.standard;

import hotciv.framework.*;

public class UnitImpl implements Unit{
    private String unitType;
    Player unitOwner;
    private boolean fortified;
    private int attack;
    private int defense;

    public UnitImpl(String unitType, Player unitOwner){
        this.unitType = unitType;
        this.unitOwner = unitOwner;
        this.fortified = false;
        if (unitType.equals(GameConstants.ARCHER)){
            defense = 3;
            attack = 2;
        }  else if (unitType.equals(GameConstants.LEGION)) {
            defense = 2;
            attack = 4;
        } else if (unitType.equals(GameConstants.SETTLER)) {
            attack = 0;
            defense = 3;
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

    public void setFortified(){
        fortified = !fortified;
    }

    public boolean getFortified(){
        return fortified;
    }

    public int getDefensiveStrength(){
        return defense;
    }

    public int getAttackingStrength(){
        return attack;
    }

    public void setDefensiveStrength(int i) {
        defense = i;
    }
}
