package hotciv.standard;
//Oh no! There is a fake bug in this file. We must fix it by implementing a hotfix.
import hotciv.framework.*;

/** Skeleton implementation of HotCiv.
 
   This source code is from the book 
     "Flexible, Reliable Software:
       Using Patterns and Agile Development"
     published 2010 by CRC Press.
   Author: 
     Henrik B Christensen 
     Department of Computer Science
     Aarhus University
   
   Please visit http://www.baerbak.com/ for further information.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
 
       http://www.apache.org/licenses/LICENSE-2.0
 
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

*/

public class GameImpl implements Game {
  private Player playerInTurn = Player.RED;
  private int age = -4000;

  private Tile[][] tiles = new Tile[GameConstants.WORLDSIZE][GameConstants.WORLDSIZE];
  private Unit[][] units = new Unit[GameConstants.WORLDSIZE][GameConstants.WORLDSIZE];
  private City[][] cities = new City[GameConstants.WORLDSIZE][GameConstants.WORLDSIZE];
 
  public GameImpl() {
    for(int i = 0; i < GameConstants.WORLDSIZE;i++){
      for(int j = 0; j < GameConstants.WORLDSIZE;j++){
        tiles[i][j] = new TileImpl(GameConstants.PLAINS);
      }
    }

    //Set tiles to be of types oceans, hill, mountains
    tiles[1][0] = new TileImpl(GameConstants.OCEANS);
    tiles[0][1] = new TileImpl(GameConstants.HILLS);
    tiles[2][2] = new TileImpl(GameConstants.MOUNTAINS);

    //Set units to be of red archers, blue legions, red settlers
    units[2][0] = new UnitImpl(GameConstants.ARCHER, Player.RED);
    units[3][2] = new UnitImpl(GameConstants.LEGION, Player.BLUE);
    units[4][3] = new UnitImpl(GameConstants.SETTLER, Player.RED);

    //Set city to be owned by red and blue
    cities[1][1] = new CityImpl(Player.RED);
    cities[4][1] = new CityImpl(Player.BLUE);
  }

  public Tile getTileAt( Position p ) {
    return tiles[p.getRow()][p.getColumn()];
  }

  public Unit getUnitAt( Position p ) {
    return units[p.getRow()][p.getColumn()];
  }

  public City getCityAt( Position p ) {
    return cities[p.getRow()][p.getColumn()];
  }

  public Player getPlayerInTurn() {
    return playerInTurn;
  }
  public Player getWinner() {
    //If age is 3000, red wins
    if(getAge() == -3000){
      return Player.RED;
    }
    return null;
  }
  public int getAge() { return age; }
  public boolean moveUnit( Position from, Position to ) {
    Unit unitFrom = getUnitAt(from);
    Unit unitTo = getUnitAt(to);
    Tile tileTo = getTileAt(to);

    //Check if the destinationTile contains Mountains or Oceans
    if(tileTo.getTypeString().equals(GameConstants.MOUNTAINS)  || tileTo.getTypeString().equals(GameConstants.OCEANS)){
      return false;
    }

    if (unitFrom.getOwner() != playerInTurn)
      return false;

    //If destination tile is less than 1 unit from sourceTile, then move it
    if (Math.abs(to.getRow() - from.getRow()) <= 1 && Math.abs(to.getColumn() - from.getColumn()) <= 1) {
      //If tile isn't occupied then move the unit, set the old tile equal to null
      if(getUnitAt(to) == null) {
        units[to.getRow()][to.getColumn()] = unitFrom;
        units[from.getRow()][from.getColumn()] = null;
        return true;
      }
    }
    return false;
  }

  public void endOfTurn() {
    //If current player is red, switch to blue and add 6 productions
    if(playerInTurn == Player.RED){
      playerInTurn = Player.BLUE;
      CityImpl blueCity = (CityImpl) getCityAt(new Position(4,1));
      blueCity.setTreasury(6);
    } else {
      //If current player is blue, switch to red and add 6 productions
      playerInTurn = Player.RED;
      CityImpl redCity = (CityImpl) getCityAt(new Position(1,1));
      redCity.setTreasury(6);

      //Advance age by 100 years each round
      age = age + 100;
    }
  }

  public void changeWorkForceFocusInCityAt( Position p, String balance ) {}

  public void changeProductionInCityAt( Position p, String unitType ) {
    CityImpl city = (CityImpl) cities[p.getRow()][p.getColumn()];
    produceUnit(city,p,unitType);
  }

  public void performUnitActionAt( Position p ) {
    if (getUnitAt(p).getTypeString()== GameConstants.SETTLER) { //This if-case is empty for now, as there are no established unit action functions yet.
      return; //build city at position p
    } else if (getUnitAt(p).getTypeString() == GameConstants.LEGION) {
      return; //do nothing at position p
    } else if (getUnitAt(p).getTypeString() == GameConstants.ARCHER) {
      return; //fortify at position p
    } else if (getUnitAt(p).getTypeString() == null) {
      return;
    }
  }

  public void produceUnit(CityImpl c,  Position p, String unitType ){
    //If unitType is Archer and has enough to buy an archer, produce an Archer
    if (unitType.equals(GameConstants.ARCHER) && c.getTreasury() >= 10) {
      c.setProduction(GameConstants.ARCHER);
    //If unitType is Legion and has enough to buy a legion, produce a Legion
    } else if (unitType.equals(GameConstants.LEGION) && c.getTreasury() >= 15) {
      c.setProduction(GameConstants.LEGION);
    //If unitType is Settler and has enough to buy a settler, produce a Settler
    } else if (unitType.equals(GameConstants.SETTLER) && c.getTreasury() >= 30) {
      c.setProduction(GameConstants.SETTLER);
    }

    //Deduct the unitType's cost from the treasury
    c.removeTreasury(unitCost(c.getProduction()));
  }

  private int unitCost(String type) {
    if (type.equals(GameConstants.ARCHER)) {
      return 10;
    }
    else if (type.equals(GameConstants.LEGION)) {
      return 15;
    }
    else return 30;
  }
}
