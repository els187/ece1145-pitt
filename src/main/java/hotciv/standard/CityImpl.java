package hotciv.standard;

import hotciv.framework.*;

public class CityImpl implements City{
    Player cityOwner;

    public CityImpl(Player player) {
        cityOwner = player;
    }

    public Player getOwner() {
        return cityOwner;
    }

    public int getSize() {
        return 1;
    }

    public int getTreasury(){
        return 0;
    }

    public String getProduction() {
        return null;
    }

    public String getWorkforceFocus() {
        return null;
    }

}
