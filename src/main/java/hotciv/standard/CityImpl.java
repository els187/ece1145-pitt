package hotciv.standard;

import hotciv.framework.*;

public class CityImpl implements City{
    Player cityOwner;
    private int size;
    private int treasury;
    private String production;
    private String workFocus;

    public CityImpl(Player player) {
        this.size = 1;
        this.cityOwner = player;
        this.production = GameConstants.ARCHER;
        this.workFocus = GameConstants.productionFocus;
    }

    public Player getOwner() {
        return cityOwner;
    }

    public int getSize() {
        return size;
    }

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

    public void setWorkforceFocus(String workFocus) {
        this.workFocus = workFocus;
    }

    public void setOwner(Player owner) {
        this.cityOwner = owner;
    }

    public void setSize(int size){
        this.size = size;
    }

    public void incrementSize(int size){
        this.size += size;
    }
}
