package hotciv.standard;

import hotciv.framework.*;
import java.util.*;

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
  private AgingStrategy agingStrategy;
  private WinningStrategy winningStrategy;


  private HashMap<Position, City> cities  = new HashMap<Position, City>();
  private HashMap<Position, Unit> units = new HashMap<Position, Unit>();
  private HashMap<Position, Tile> tiles = new HashMap<Position, Tile>();

  public GameImpl(AgingStrategy agingStrategy, WinningStrategy winningStrategy){
    this.agingStrategy = agingStrategy;
    this.winningStrategy = winningStrategy;

    for(int i = 0; i < GameConstants.WORLDSIZE;i++){
      for(int j = 0; j < GameConstants.WORLDSIZE;j++){
        tiles.put(new Position(i, j), new TileImpl(GameConstants.PLAINS));
      }
    }

    //Set tiles to be of types oceans, hill, mountains
    tiles.put(new Position(1, 0), new TileImpl(GameConstants.OCEANS));
    tiles.put(new Position(0, 1), new TileImpl(GameConstants.HILLS));
    tiles.put(new Position(2, 2), new TileImpl(GameConstants.MOUNTAINS));

    //Set units to be of red archers, blue legions, red settlers
    units.put(new Position(2,0), new UnitImpl(GameConstants.ARCHER, Player.RED));
    units.put(new Position(3,2), new UnitImpl(GameConstants.LEGION, Player.BLUE));;
    units.put(new Position(4,3), new UnitImpl(GameConstants.SETTLER, Player.RED));


    //Set cities to be owned by red and blue
    //cities.put(new Position(1,1), new CityImpl(Player.RED));
   // cities.put(new Position(4,1), new CityImpl(Player.BLUE));

  }

  public Tile getTileAt( Position p ) {
    return tiles.get(p);
  }
  public Unit getUnitAt( Position p ) {
    return units.get(p);
  }
  public City getCityAt( Position p ) {
    return cities.get(p);
  }

  public Player getPlayerInTurn() {
    return playerInTurn;
  }

  public Player getWinner() {
    return winningStrategy.getStrategicWinner(this);
  }

  public int getAge() {
    return age;
  }

  public boolean moveUnit( Position from, Position to ) {
    Unit unitFrom = getUnitAt(from);
    Tile tileTo = getTileAt(to);
    UnitImpl unitChoice = (UnitImpl) unitFrom;

    //Check if unit is fortified
    if (unitChoice.getFortified()) return false;

    //Check if the destinationTile contains Mountains or Oceans
    if(tileTo.getTypeString().equals(GameConstants.MOUNTAINS)  || tileTo.getTypeString().equals(GameConstants.OCEANS)){
      return false;
    }

    //Check if the player is trying to move another player's unit
    if (unitFrom.getOwner() != playerInTurn)
      return false;

    //If destination tile is less than 1 unit from sourceTile, then move it
    if (Math.abs(to.getRow() - from.getRow()) <= 1 && Math.abs(to.getColumn() - from.getColumn()) <= 1) {
      //If destination tile isn't occupied then move the unit and remove unit from old position
      if(getUnitAt(to) == null) {
        units.put(to, unitFrom);
        units.remove(from);
        return true;
      } //at this point, we have checked if the tile is occupied, so we can check if attack
      if(attackOnEnemySucceeded(from, to)) {return true;}
    }
    return false;
  }

  //In Alpha Civ, the attacker is always successful.
  private boolean attackOnEnemySucceeded(Position from, Position to) {
    boolean successfulAttack = true;
    if (successfulAttack) {
      units.put(to, getUnitAt(from));
      units.remove(from);
      return true;
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
      age = agingStrategy.getStrategicAging(age);
    }
  }

  public void changeWorkForceFocusInCityAt( Position p, String balance ) {}

  public void changeProductionInCityAt( Position p, String unitType ) {
    CityImpl city = (CityImpl) cities.get(p);
    city.setProduction(unitType);
    produceUnit(city,p,unitType);
  }

  public void performUnitActionAt( Position p ) {
    UnitImpl impUnit = (UnitImpl) getUnitAt(p);
    if (getUnitAt(p).getTypeString()== GameConstants.SETTLER) { //build city at position p (temporary)
      units.remove(p);
      cities.put(p, new CityImpl(impUnit.getOwner(), new Position(4, 5)));
    } else if (getUnitAt(p).getTypeString() == GameConstants.LEGION) {
      return; //do nothing at position p
    } else if (getUnitAt(p).getTypeString() == GameConstants.ARCHER) { //fortify at position p
      impUnit.setFortified();
      if (impUnit.getFortified() == true) {
        impUnit.setDefensiveStrength(impUnit.getDefensiveStrength() * 2);
      } else {
        impUnit.setDefensiveStrength(impUnit.getDefensiveStrength() / 2);
      }
    } else if (getUnitAt(p).getTypeString() == null) {
      return;
    }
  }

  //*********************************************METHODS THAT WE ADDED**************************************************

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
    c.removeTreasury(getUnitCost(c.getProduction()));
  }

  private int getUnitCost(String type) {
    if (type.equals(GameConstants.ARCHER)) {
      return 10;
    }
    else if (type.equals(GameConstants.LEGION)) {
      return 15;
    }
    else return 30;
  }

  public HashMap<Position, City> getCities() {
    return cities;
  }
}