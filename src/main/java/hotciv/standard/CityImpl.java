package hotciv.standard;

import hotciv.framework.*;

public class CityImpl implements City{
    Player cityOwner;
    private int size;
    private int treasury;
    private String production;

    public CityImpl(Player player) {
        this.size = 1;
        this.cityOwner = player;
        this.production = GameConstants.ARCHER;
    }

    public Player getOwner() {
        return cityOwner;
    }

    //Population
    public int getSize() {
        return size;
    }

    //Production
    public int getTreasury() {
        return treasury;
    }

    public String getProduction() {
        return production;
    }

    public String getWorkforceFocus() {
        return GameConstants.productionFocus;
    }

    public void setTreasury(int treasury) {
        this.treasury += treasury;
    }

    public void setProduction(String productionType) {
        this.production = productionType;
    }


    public void setOwner(Player owner) {
        this.cityOwner = owner;
    }

    public void setSize(int size){
        this.size += size;
    }
}
