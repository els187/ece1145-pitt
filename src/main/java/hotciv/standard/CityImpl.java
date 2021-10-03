package hotciv.standard;

import hotciv.framework.*;

public class CityImpl implements City{
    Player cityOwner;
    private int size;
    private int treasury;
    private String production;


    public CityImpl(Player player, Position position) {
        this.cityOwner = player;
        this.size = 1;
        this.production = GameConstants.ARCHER;
    }

    public CityImpl(Player red) {
    }

    public Player getOwner() {
        return cityOwner;
    }

    public int getSize() {
        return size;
    }

    public int getTreasury(){
        return treasury;
    }

    public String getProduction() {
        // return GameConstants.ARCHER;
        return production;
    }

    public String getWorkforceFocus() {
        return null;
    }

    public void setTreasury(int n){
        treasury += n;
    }

    public void removeTreasury(int n){
        treasury -= n;
    }

    public void setProduction(String production) {
        this.production = production;
    }
}
