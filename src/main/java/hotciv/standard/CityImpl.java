package hotciv.standard;

import hotciv.framework.*;

public class CityImpl implements City{
    Player cityOwner;
    private int size;
    private int treasury;
    private String production;

    public CityImpl(Player player) {
        this.cityOwner = player;
        this.production = GameConstants.ARCHER;
    }

    public Player getOwner() {
        return cityOwner;
    }

    public int getSize() {
        return 1;
    }

    public int getTreasury(){
        return treasury;
    }

    public String getProduction() {
        return production;
    }

    public String getWorkforceFocus() {
        return null;
    }

    public void incrementTreasury(){
        this.treasury += 6;
    }

    public void setProduction(String productionType){
        this.production = productionType;
    }

    public void deductTreasury(int unitCost){
        this.treasury -= unitCost;
    }

    public void setOwner(Player owner){
        this.cityOwner = owner;
    }
}
