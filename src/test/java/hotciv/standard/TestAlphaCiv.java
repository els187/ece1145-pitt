package hotciv.standard;

import hotciv.framework.*;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.*;

/** Skeleton class for AlphaCiv test cases

    Updated Oct 2015 for using Hamcrest matchers

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
public class TestAlphaCiv {
  private Game game;

  /**
   * Fixture for alphaciv testing.
   */

  @Before
  public void setUp() {
    game = new GameImpl(new AlphaAgingStrategy());
  }

  // FRS p. 455 states that 'Red is the first player to take a turn'.
  @Test
  public void shouldBeRedAsStartingPlayer() {
    assertThat(game, is(notNullValue()));
    assertThat(game.getPlayerInTurn(), is(Player.RED));
  }

  @Test
  public void shouldBeBluesTurnAfterRed() {
    game.endOfTurn();
    assertThat(game, is(notNullValue()));
    assertThat(game.getPlayerInTurn(), is(Player.BLUE));
  }
  @Test
  public void startsAtYear4000BC() {
    assertThat(game, is(notNullValue()));
    assertThat(game.getAge(), is(-4000));
  }

  @Test
  public void ageIncreasesAfterEveryRound() {
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game, is(notNullValue()));
    assertThat(game.getAge(), is(-3900));
  }

  @Test
  public void redWinsIn3000BC() {
    for (int i = 0; i < 10; i++) {
      game.endOfTurn();
      game.endOfTurn();
    }
    assertThat(game, is(notNullValue()));
    assertThat(game.getWinner(), is(Player.RED));
  }

  @Test
  public void shouldHavePlainsEverywhereElse() {
    assertThat(game, is(notNullValue()));
    assertThat(game.getTileAt(new Position(0, 0)).getTypeString(), is(GameConstants.PLAINS));
    assertThat(game.getTileAt(new Position(1, 1)).getTypeString(), is(GameConstants.PLAINS));
    assertThat(game.getTileAt(new Position(3, 3)).getTypeString(), is(GameConstants.PLAINS));
    assertThat(game.getTileAt(new Position(15, 15)).getTypeString(), is(GameConstants.PLAINS));
  }

  @Test
  public void shouldHaveOceanAt1_0() {
    assertThat(game, is(notNullValue()));
    assertThat(game.getTileAt(new Position(1, 0)).getTypeString(), is(GameConstants.OCEANS));
  }

  @Test
  public void shouldHaveHillsAt0_1() {
    assertThat(game, is(notNullValue()));
    assertThat(game.getTileAt(new Position(0, 1)).getTypeString(), is(GameConstants.HILLS));
  }

  @Test
  public void shouldHaveMountainsAt2_2() {
    assertThat(game, is(notNullValue()));
    assertThat(game.getTileAt(new Position(2, 2)).getTypeString(), is(GameConstants.MOUNTAINS));
  }

  @Test
  public void shouldHaveRedArcherAt2_0(){
    assertThat(game, is(notNullValue()));
    assertThat(game.getUnitAt(new Position(2,0)).getTypeString(), is(GameConstants.ARCHER));
    assertThat(game.getUnitAt(new Position(2, 0)).getOwner(), is(Player.RED));
  }

  @Test
  public void shouldHaveBlueLegionAt3_2(){
    assertThat(game, is(notNullValue()));
    assertThat(game.getUnitAt(new Position(3,2)).getTypeString(), is(GameConstants.LEGION));
    assertThat(game.getUnitAt(new Position(3, 2)).getOwner(), is(Player.BLUE));
  }

  @Test
  public void shouldHaveRedSettlerAt4_3(){
    assertThat(game, is(notNullValue()));
    assertThat(game.getUnitAt(new Position(4,3)).getTypeString(), is(GameConstants.SETTLER));
    assertThat(game.getUnitAt(new Position(4, 3)).getOwner(), is(Player.RED));
  }

  @Test
  public void shouldHaveRedCityAt1_1() {
    assertThat(game, is(notNullValue()));
    assertThat(game.getCityAt(new Position(1, 1)).getOwner(), is(Player.RED));
  }

  @Test
  public void shouldHaveBlueCityAt4_1() {
    assertThat(game, is(notNullValue()));
    assertThat(game.getCityAt(new Position(4, 1)).getOwner(), is(Player.BLUE));
  }

  @Test
  public void populationSizeShouldBe1() {
    assertThat(game, is(notNullValue()));
    assertThat(game.getCityAt(new Position(1, 1)).getSize(), is(1));
    assertThat(game.getCityAt(new Position(4, 1)).getSize(), is(1));
  }

  @Test
  public void citiesProduce6PerRound() {
    for (int i = 0; i < 10; i++) {
      game.endOfTurn();
    }
    assertThat(game.getCityAt(new Position(1, 1)).getTreasury(), is(30));
    assertThat(game.getCityAt(new Position(4, 1)).getTreasury(), is(30));
  }

  @Test
  public void citiesCanProduceArchers(){
    CityImpl redCity = (CityImpl) game.getCityAt(new Position(1,1));
    redCity.setProduction(GameConstants.ARCHER);
    assertThat(redCity.getProduction(), is(GameConstants.ARCHER));
  }

  @Test
  public void citiesCanProduceLegions(){
    CityImpl redCity = (CityImpl) game.getCityAt(new Position(1,1));
    redCity.setProduction(GameConstants.LEGION);
    assertThat(redCity.getProduction(), is(GameConstants.LEGION));
  }

  @Test
  public void citiesCanProduceSettlers(){
    CityImpl redCity = (CityImpl) game.getCityAt(new Position(1,1));
    redCity.setProduction(GameConstants.SETTLER);
    assertThat(redCity.getProduction(), is(GameConstants.SETTLER));
  }

  @Test
  public void treasuryTotalIsDeductedAfterProducingForRedCity(){
    for (int i = 0; i < 10; i++) {
      game.endOfTurn();
    }

    City redCity = game.getCityAt(new Position(1,1));
    City blueCity = game.getCityAt(new Position(4,1));

    assertThat(redCity.getTreasury(), is(30));
    assertThat(blueCity.getTreasury(), is(30));

    game.changeProductionInCityAt(new Position(1,1), GameConstants.ARCHER);
    assertThat(redCity.getProduction(), is(GameConstants.ARCHER));
    assertThat(redCity.getTreasury(), is(20));

    game.changeProductionInCityAt(new Position(1,1), GameConstants.LEGION);
    assertThat(redCity.getProduction(), is(GameConstants.LEGION));
    assertThat(redCity.getTreasury(), is(5));
  }

  @Test
  public void treasuryTotalIsDeductedAfterProducingForBlueCity(){
    for (int i = 0; i < 22; i++) {
      game.endOfTurn();
    }

    City redCity = game.getCityAt(new Position(1,1));
    City blueCity = game.getCityAt(new Position(4,1));

    assertThat(redCity.getTreasury(), is(66));
    assertThat(blueCity.getTreasury(), is(66));

    game.changeProductionInCityAt(new Position(4,1), GameConstants.SETTLER);
    assertThat(blueCity.getProduction(), is(GameConstants.SETTLER));
    assertThat(blueCity.getTreasury(), is(36));
  }

  @Test
  public void unitsCannotMoveOverMountains(){
    assertThat(game.moveUnit(new Position(3,2), new Position(2,2)), is(Boolean.FALSE));
  }

  @Test
  public void unitsCannotMoveOverOceans(){
    assertThat(game.moveUnit(new Position(2,0), new Position(1,0)), is(Boolean.FALSE));
  }

  @Test
  public void shouldMoveArcherFrom2_0To2_1(){
    Unit oldArcherUnit = game.getUnitAt(new Position(2,0));

    assertThat(oldArcherUnit.getTypeString(),is(GameConstants.ARCHER));
    assertThat(game.moveUnit(new Position(2,0), new Position(2,1)), is(true));
    assertThat(game.getUnitAt(new Position(2,0)), is(nullValue()));

    Unit newArcherUnit = game.getUnitAt(new Position(2,1));
    assertThat(newArcherUnit.getTypeString(), is(GameConstants.ARCHER));
  }

  @Test
  public void shouldMoveLegionFrom3_2To3_3() {
    game.endOfTurn();

    Unit oldLegion = game.getUnitAt((new Position(3, 2)));
    assertThat(oldLegion.getTypeString(),is(GameConstants.LEGION));
    assertThat(game.moveUnit(new Position(3,2),new Position(3,3)),is(true));
    assertThat(game.getUnitAt(new Position(3,2)),is(nullValue()));

    Unit newLegion = game.getUnitAt(new Position(3,3));
    assertThat(newLegion.getTypeString(),is(GameConstants.LEGION));
  }

  @Test
  public void shouldMoveSettlerFrom4_3To4_4() {
    Unit oldLegion = game.getUnitAt((new Position(4, 3)));
    assertThat(oldLegion.getTypeString(),is(GameConstants.SETTLER));
    assertThat(game.moveUnit(new Position(4,3),new Position(4,4)),is(true));
    assertThat(game.getUnitAt(new Position(4,3)),is(nullValue()));

    Unit newLegion = game.getUnitAt(new Position(4,4));
    assertThat(newLegion.getTypeString(),is(GameConstants.SETTLER));
  }

  @Test
  public void shouldNotBeAbleToMoveMoreThan1Tile() {
    assertThat(game.moveUnit(new Position(2, 0), new Position(2, 6)),is(Boolean.FALSE));
    assertThat(game.moveUnit(new Position(2,0), new Position(0,0)), is(Boolean.FALSE));
  }

  @Test
  public void attackShouldAlwaysBeSuccessful() {
    assertThat(game.moveUnit(new Position(2, 0), new Position(3,0)), is(true));
    assertThat(game.moveUnit(new Position(3, 0), new Position(3,1)), is(true));
    Unit oldArcherUnit = game.getUnitAt(new Position(3,1));
    assertThat(oldArcherUnit.getTypeString(),is(GameConstants.ARCHER));
    Unit oldLegion = game.getUnitAt((new Position(3, 2)));
    assertThat(oldLegion.getTypeString(),is(GameConstants.LEGION));
    assertThat(game.moveUnit(new Position(3, 1), new Position(3,2)), is(true));
    Unit newArcherUnit = game.getUnitAt(new Position(3,2));
    assertThat(newArcherUnit.getTypeString(),is(GameConstants.ARCHER));
    assertThat(game.getUnitAt(new Position(3,1)),is(nullValue()));
  }

@Test
public void redCantMoveBlueUnits(){
  assertThat(game.getPlayerInTurn(), is(Player.RED));
  Unit legion = game.getUnitAt(new Position(3,2));
  assertThat(legion.getOwner(), is(Player.BLUE));
  assertThat(game.moveUnit(new Position(3,2), new Position(3,1)), is(false));
}
}

