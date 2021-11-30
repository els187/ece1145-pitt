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
  private int bluePlayerWins = 0;
  private int redPlayerWins = 0;
  public int numRounds = 0;

  private AgingStrategy agingStrategy;
  private WinningStrategy winningStrategy;
  private ActionStrategy actionStrategy;
  private BattleStrategy battleStrategy;
  private WorkForceFocusStrategy workForceFocusStrategy;
  private PopulationStrategy populationStrategy;

  public HashMap<Position, City> cities;
  public HashMap<Position, Unit> units;
  public HashMap<Position, Tile> tiles;
  private ArrayList<GameObserver> gameObserver;

  public GameImpl(GameFactory gameFactory) {
    this.agingStrategy = gameFactory.agingStrategy();
    this.winningStrategy = gameFactory.winningStrategy();
    this.actionStrategy = gameFactory.actionStrategy();
    this.battleStrategy = gameFactory.battleStrategy();
    this.workForceFocusStrategy = gameFactory.workForceFocusStrategy();
    this.populationStrategy = gameFactory.populationStrategy();

    tiles = gameFactory.mapStrategy().defineTilesLayout();
    units = gameFactory.mapStrategy().defineUnitsLayout();
    cities = gameFactory.mapStrategy().defineCitiesLayout();

    gameObserver = new ArrayList();
  }

  public Tile getTileAt(Position p) {
    return tiles.get(p);
  }

  public Unit getUnitAt(Position p) {
    return units.get(p);
  }

  public City getCityAt(Position p) {
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
    if (!checkValidMove(from, to)) {
      return false;
    }

    Unit unitFrom = units.get(from);
    Unit unitTo = units.get(to);

    if (((UnitImpl) unitFrom).isFortified()) {
      return false;
    }

    //Attack the unit to get the battle winner
    if(unitTo != null && !attackWhenUnitIsPresent(from, to)) {
      return false;
    }

    //Place the unit at the destination and remove the unit from the source tile
    replaceUnit(from, to);

    //Decrement the unit move count
    ((UnitImpl)units.get(to)).setUnitMoveCount();

    //Set the owner
    setOwner(to);

    notifyWorldChangedAt(from);
    notifyWorldChangedAt(to);

    return true;
  }

  //Finishes the players turn and moves to the next turn
  public void endOfTurn() {
    if (playerInTurn == Player.RED) {
      playerInTurn = Player.BLUE;
    } else {
      playerInTurn = Player.RED;
      setTreasuryAtEndOfRound();
      produceUnits();
      calculateAge();
      resetMoveCount();
      winner = getWinner();
      numRounds++;
    }
    notifyTurnEnds(playerInTurn, age);
  }

  public void calculateAge(){
    age = agingStrategy.getStrategicAging(age);
  }

  public void setOwner(Position to){
    Unit unitTo = units.get(to);

    if (getCityAt(to) != null && unitTo.getTypeString() != GameConstants.UFO) {
      ((CityImpl)getCityAt(to)).setOwner(getPlayerInTurn());
    }
  }

  public void replaceUnit(Position from, Position to){
    Unit unitFrom = units.get(from);

    units.put(to, new UnitImpl(unitFrom.getTypeString(), unitFrom.getOwner()));
    createUnit(to, getUnitAt(from));
    units.remove(from);
  }

  public boolean attackWhenUnitIsPresent(Position from, Position to) {
    if (getUnitAt(from) != null && battleStrategy.getBattleOutcome(this, from, to)) {
      Unit unitFrom = units.get(from);
      units.remove(to);
      if (unitFrom.getOwner().equals(Player.RED)) {
        redPlayerWins++;
      } else {
        bluePlayerWins++;
      }
      return true;
    } else {
      units.remove(from);
      return false;
    }
  }

  public boolean checkValidMove(Position from, Position to){
    boolean tileIsWithinWorld = tiles.containsKey(to);
    //Check if destination tile is within the 16*16 limit
    if(!tileIsWithinWorld){
      return false;
    }

    //Check if distance is less than 1
    if(!distanceIsValid(from, to)){
      return false;
    }

    //Check if destination tile contains mountains or oceans and unit is not UFO
    if(!tileTypeIsValid(to, from)){
      return false;
    }

    Unit unitFrom = units.get(from);
    Unit unitTo = units.get(to);

    //Check if playerInTurn is trying to move its own unit
    if(!playerInTurnIsMovingUnit(from)) {
      return false;
    }

    if(moveCountEqualsZero(unitFrom)){
      return false;
    }

    //Return false if a friendly unit occupies the destination tile
    if (unitTo != null && unitFrom.getOwner() == unitTo.getOwner()) {
      return false;
    }
    return true;
  }

  public void changeWorkForceFocusInCityAt(Position p, String balance) {
    workForceFocusStrategy.produceInCity(this, p);
  }

  public void changeProductionInCityAt(Position p, String unitType) {
    City c = cities.get(p);
    if(getPlayerInTurn() == c.getOwner())
      ((CityImpl) c).setProduction(unitType);
  }

  public void performUnitActionAt(Position p) {
    actionStrategy.unitAction(p, this);
    notifyWorldChangedAt(p);
  }

  public void addObserver(GameObserver observer) {
    gameObserver.add(observer);
  }

  public void setTileFocus(Position position) {
    notifyTileFocusChangedAt(position);
  }

  public void setTreasuryAtEndOfRound(){
    for (Position p : cities.keySet()) {
      City c = getCityAt(p);
      changeWorkForceFocusInCityAt(p, c.getWorkforceFocus());
      populationStrategy.increaseCitySize(c);
    }
  }

  public void produceUnits() {
    for (Position pos : cities.keySet()) {
      City c = getCityAt(pos);
      boolean treasuryIsEnough = c.getTreasury() >= getUnitCost(c.getProduction());
      boolean unitIsPresent = units.containsKey(pos);

      //If treasury is enough then deduct treasury from the unit being produced
      if (treasuryIsEnough) {
        ((CityImpl)c).setTreasury(-getUnitCost(c.getProduction()));
        //If unit is not present, place it directly
        if (!unitIsPresent) {
          placeUnit(pos, c);
        } else {
          //If unit is present, place it clockwise starting from North
          placeUnitClockwise(pos, c);
        }
      }
    }
  }

  public void placeUnit(Position pos, City c){
    //If unit is not present, place it directly
    if(unitTypeIsValid(c)) {
      units.put(pos, new UnitImpl(c.getProduction(), c.getOwner()));
    }
  }

  public void placeUnitClockwise(Position pos, City c){
    //If unit is present, place it clockwise starting from North
    if(unitTypeIsValid(c)) {
      placeUnitsClockwise(pos, c);
    }
  }

  public boolean tileTypeIsValid(Position to, Position from) {
    Tile tileTo = getTileAt(to);

    boolean isUfo = units.get(from).getTypeString().equals(GameConstants.UFO);

    //Check if it is trying to move to mountains or oceans
    if ((tileTo.getTypeString().equals(GameConstants.MOUNTAINS) && !isUfo) ||
            (tileTo.getTypeString().equals(GameConstants.OCEANS) && !isUfo)) {
      return false;
    }
    return true;
  }

  //Check if playerInTurn is trying to move its own unit
  private boolean playerInTurnIsMovingUnit(Position from) {
    if(getUnitAt(from) != null && getUnitAt(from).getOwner() != getPlayerInTurn()) {
      return false;
    } else {
      return true;
    }
  }


  public boolean unitTypeIsValid(City c){
    if(c.getProduction().equals(GameConstants.ARCHER) || c.getProduction().equals(GameConstants.LEGION) ||
            c.getProduction().equals(GameConstants.SETTLER) || c.getProduction().equals(GameConstants.UFO)){
      return true;
    } else {
      return false;
    }
  }

  //Return true if position(to) is already in the adjacent coordinates from the given center(from).
  public boolean distanceIsValid(Position from, Position to) {
    for (Position pos : Utility.get8neighborhoodOf(from)) {
      if (pos.equals(to)) {
        return true;
      }
    }
    return false;
  }

  private boolean moveCountEqualsZero(Unit unitFrom) {
    if(unitFrom != null && unitFrom.getMoveCount() == 0) {
      return true;
    } else {
      return false;
    }
  }

  public void placeUnitsClockwise(Position pos, City c) {
    boolean unitPlacementIsSuccessful = false;
    //Iterating through all possible adjacent coordinates of the tiles and placing it into the first empty space
    for (Position n : Utility.get8neighborhoodOf(pos)) {
      if (!units.containsKey(n) && !unitPlacementIsSuccessful) {
        units.put(n, new UnitImpl(c.getProduction(), c.getOwner()));
        unitPlacementIsSuccessful = true;
      }
    }
  }

  public void resetMoveCount() {
    //Reset the move count
    for (Unit u : units.values()) {
      if (moveCountEqualsZero(u)){
        if(u.getTypeString().equals(GameConstants.UFO)){
          ((UnitImpl) u).setMoveCount(2);
        } else {
          ((UnitImpl) u).setMoveCount(1);
        }
      }
    }
  }

  private int getUnitCost(String type) {
    if (type.equals(GameConstants.ARCHER)) {
      return 10;
    } else if (type.equals(GameConstants.LEGION)) {
      return 15;
    } else if(type.equals(GameConstants.SETTLER)){
      return 30;
    } else {
      //UFO cost
      return 60;
    }
  }

  public void createCity(Position pos, City c) {
    cities.put(pos, c);
    notifyWorldChangedAt(pos);
  }

  public void removeCity(Position pos) {
    cities.remove(pos);
    notifyWorldChangedAt(pos);
  }

  public void createUnit(Position pos, Unit u) {
    units.put(pos, u);
    notifyWorldChangedAt(pos);
  }

  public void removeUnit(Position pos) {
    units.remove(pos);
    notifyWorldChangedAt(pos);
  }

  public void createTile(Position pos, Tile t) {
    tiles.put(pos, t);
    notifyWorldChangedAt(pos);
  }

  public int getBluePlayerWins(){
    return bluePlayerWins;
  }

  public int getRedPlayerWins(){
    return redPlayerWins;
  }

  public int getNumRounds() {
    return numRounds;
  }

  public void notifyWorldChangedAt(Position pos){
    for(GameObserver obs: gameObserver)
    {
      obs.worldChangedAt(pos);
    }
  }

  public void notifyTurnEnds(Player nextPlayer, int age){
    for(GameObserver obs: gameObserver)
    {
      obs.turnEnds(nextPlayer, age);
    }
  }

  public void notifyTileFocusChangedAt(Position position)
  {
    for(GameObserver obs: gameObserver)
    {
      obs.tileFocusChangedAt(position);
    }
  }
}