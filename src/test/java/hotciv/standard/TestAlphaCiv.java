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
    game = new GameImpl();
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

}

