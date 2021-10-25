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
  private Player winner;
  private int age = -4000;
  private AgingStrategy agingStrategy;
  private WinningStrategy winningStrategy;
  private ActionStrategy actionStrategy;
  private MapStrategy mapStrategy;


  public HashMap<Position, CityImpl> cities;
  public HashMap<Position, UnitImpl> units;
  public HashMap<Position, TileImpl> tiles;

  public GameImpl(AgingStrategy agingStrategy, WinningStrategy winningStrategy,
                  ActionStrategy actionStrategy, MapStrategy mapStrategy) {
    this.agingStrategy = agingStrategy;
    this.winningStrategy = winningStrategy;
    this.actionStrategy = actionStrategy;
    this.mapStrategy = mapStrategy;

    tiles = mapStrategy.defineTilesLayout();
    units = mapStrategy.defineUnitsLayout();
    cities = mapStrategy.defineCitiesLayout();
  }

  public TileImpl getTileAt(Position p) {
    return tiles.get(p);
  }

  public UnitImpl getUnitAt(Position p) {
    return units.get(p);
  }

  public CityImpl getCityAt(Position p) {
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

  public boolean moveUnit(Position from, Position to) {
    if(!checkValidMove(from, to)){
      return false;
    }

    Unit unitFrom = units.get(from);
    Unit unitTo = units.get(to);

    if (((UnitImpl) unitFrom).isFortified()) {
      return false;
    }

    //Place the unit at the destination and remove the unit from the source tile, decrement the move count
    units.put(to, new UnitImpl(unitFrom.getTypeString(), unitFrom.getOwner()));
    units.remove(from);
    units.get(to).setMoveCount(units.get(to).getMoveCount() - 1);

    //If city is not owned by anyone, set the owner
    if(getCityAt(to) != null) {
      getCityAt(to).setOwner(unitFrom.getOwner());
    }
    return true;
  }

  public boolean checkValidMove(Position from, Position to){
    //Check if distance is less than 1
    if(!distanceIsValid(from, to)){
      return false;
    }
    //Check if destination tile contains mountains or oceans
    if(!tileTypeIsValid(to)){
      return false;
    }

    Unit unitFrom = units.get(from);
    Unit unitTo = units.get(to);

    //Return false if the destination tile is already occupied and if it is a friendly unit
    if (unitTo != null && unitFrom.getOwner() == unitTo.getOwner()) {
      //units.remove(unitTo);
      return false;
    }

    //If playerInTurn is trying to move another player's unit, return false
    if(unitFrom.getOwner() != getPlayerInTurn()) {
      return false;
    }
    return true;
  }

  //Finishes the players turn and moves to the next turn
  public void endOfTurn() {
    if (playerInTurn == Player.RED) {
      playerInTurn = Player.BLUE;

    } else {
      playerInTurn = Player.RED;
      produceUnits();
      age = agingStrategy.getStrategicAging(age);
      winner = getWinner();
    }
  }

  public void changeWorkForceFocusInCityAt(Position p, String balance) {
  }

  public void changeProductionInCityAt(Position p, String unitType) {
    CityImpl c = cities.get(p);
    if(getPlayerInTurn() == c.getOwner())
      c.setProduction(unitType);
  }

  public void performUnitActionAt(Position p) {
    actionStrategy.unitAction(p, this);
  }

  public void produceUnits() {
    for (Position p : cities.keySet()) {
      CityImpl c = getCityAt(p);
      c.incrementTreasury();

      boolean treasuryIsEnough = c.getTreasury() >= getUnitCost(c.getProduction());
      boolean unitIsPresent = units.containsKey(p);

      //If treasury is enough then deduct treasury from the unit being produced
      if (treasuryIsEnough) {
        c.deductTreasury(getUnitCost(c.getProduction()));
        //If unit is not present, place it directly
        if (!unitIsPresent) {
          units.put(p, new UnitImpl(c.getProduction(), c.getOwner()));
        } else {
          //If unit is present, place it clockwise starting from North
          placeUnitsClockwise(p, c);
        }
      }
    }
    resetMoveCount();
  }

  public boolean tileTypeIsValid(Position to) {
    Tile tileTo = getTileAt(to);

    //Check if it is trying to move to mountains or oceans
    if (tileTo.getTypeString() == GameConstants.MOUNTAINS ||
            tileTo.getTypeString() == GameConstants.OCEANS){
      return false;
    }
    return true;
  }

  //Return true if position(to) is already in the adjacent coordinates from the given center(from).
  public boolean distanceIsValid(Position from, Position to) {
    for (Position p : Utility.get8neighborhoodOf(from)) {
      if (p.equals(to)) {
        return true;
      }
    }
    return false;
  }

  public void placeUnitsClockwise(Position pos, City c) {
    boolean unitPlacementIsSuccessful = false;
    //Iterating through all possible adjacent coordinates of the tiles and placing it into the first empty space
    for (Position n : Utility.get8neighborhoodOf(pos)) {
      if (!units.containsKey(n) && unitPlacementIsSuccessful == false) {
        units.put(n, new UnitImpl(c.getProduction(), c.getOwner()));
        unitPlacementIsSuccessful = true;
      }
    }
  }

  public void resetMoveCount() {
    //Reset the move count
    for (UnitImpl u : units.values()) {
      u.setMoveCount(1);
    }
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

  public void createCity(Position pos, CityImpl c) {
    cities.put(pos, c);
  }

  public void removeUnit(Position pos) {
    units.remove(pos);
  }
}