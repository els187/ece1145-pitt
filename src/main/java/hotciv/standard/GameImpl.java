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

    tiles[1][0] = new TileImpl(GameConstants.OCEANS);
    tiles[0][1] = new TileImpl(GameConstants.HILLS);
    tiles[2][2] = new TileImpl(GameConstants.MOUNTAINS);

    units[2][0] = new UnitImpl(GameConstants.ARCHER, Player.RED);
    units[3][2] = new UnitImpl(GameConstants.LEGION, Player.BLUE);
    units[4][3] = new UnitImpl(GameConstants.SETTLER, Player.RED);

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
    if(getAge() == -3000){
      return Player.RED;
    }
    return null;
  }
  public int getAge() { return age; }
  public boolean moveUnit( Position from, Position to ) {
    return false;
  }
  public void endOfTurn() {
    if(playerInTurn == Player.RED){
      playerInTurn = Player.BLUE;
      CityImpl blueCity = (CityImpl) getCityAt(new Position(4,1));
      blueCity.setTreasury(6);
    } else {
      playerInTurn = Player.RED;
      CityImpl redCity = (CityImpl) getCityAt(new Position(1,1));
      redCity.setTreasury(6);

      age = age + 100;
    }
  }
  public void changeWorkForceFocusInCityAt( Position p, String balance ) {}
  public void changeProductionInCityAt( Position p, String unitType ) {}
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
}
