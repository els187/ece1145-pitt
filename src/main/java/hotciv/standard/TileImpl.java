package hotciv.standard;

import hotciv.framework.*;

public class TileImpl implements Tile{
    private String terrainType;

    public TileImpl(String terrainType){
        this.terrainType = terrainType;
    }

    public String getTypeString(){
        return terrainType;
    }

    public void setTypeString(String tileType){
        terrainType = tileType;
    }
}