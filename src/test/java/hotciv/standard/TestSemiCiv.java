package hotciv.standard;

import hotciv.framework.*;

import org.junit.*;

import java.time.temporal.ValueRange;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestSemiCiv {
    private GameImpl game;

    /**
     * Fixture for SemiCiv testing.
     */

    @Before
    public void setUp() {
        game = new GameImpl(new SemiCivFactory(new DieRollStub()));
    }

    private void numberOfRounds(int rounds) {
        for (int i = 0; i < rounds; i++) {
            game.endOfTurn();
            game.endOfTurn();
        }
    }

    //***********************************TESTING BETA AGING STRATEGY****************************************************
    @Test
    public void worldAgesCorrectly() {
        assertThat(game.getAge(), is(-4000));
        numberOfRounds(39);
        assertThat(game.getAge(), is(-100));
        numberOfRounds(1);
        assertThat(game.getAge(), is(-1));
        numberOfRounds(1);
        assertThat(game.getAge(), is(1));
        numberOfRounds(1);
        assertThat(game.getAge(), is(50));
        numberOfRounds(34);
        assertThat(game.getAge(), is(1750));
        numberOfRounds(6);
        assertThat(game.getAge(), is(1900));
        numberOfRounds(14);
        assertThat(game.getAge(), is(1970));
        numberOfRounds(1);
        assertThat(game.getAge(), is(1971));
        numberOfRounds(1);
        assertThat(game.getAge(), is(1972));
    }

    //************************************TESTING GAMMA ACTION STRATEGY*************************************************
    @Test
    public void shouldFortifyArcherAt2_0(){
        game.createUnit(new Position(2,0), new UnitImpl(GameConstants.ARCHER, Player.RED));
        assertThat(game.getUnitAt(new Position(2,0)).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getUnitAt(new Position(2,0 )).getDefensiveStrength(), is(3));
        game.performUnitActionAt(new Position(2,0 ));
        assertThat(game.getUnitAt(new Position(2,0 )).getDefensiveStrength(), is(3 * 2));
    }

    @Test
    public void settlerAt4_3IsReplaced(){
        game.createUnit(new Position(4,3), new UnitImpl(GameConstants.SETTLER, Player.RED));
        assertThat(game.getUnitAt(new Position(4,3)), is (notNullValue()));
        game.performUnitActionAt(new Position(4,3));
        assertThat(game.getUnitAt(new Position(4,3)), is(nullValue()));
        assertThat(game.getCityAt(new Position(4,3)), is(notNullValue()));
    }

    @Test
    public void newCityHasASizeOfOne() {
        game.createUnit(new Position(4,3), new UnitImpl(GameConstants.SETTLER, Player.RED));
        assertThat(game.getUnitAt(new Position(4,3)), is (notNullValue()));
        game.performUnitActionAt(new Position(4,3));
        assertThat(game.getUnitAt(new Position(4,3)), is(nullValue()));
        assertThat(game.getCityAt(new Position(4,3)).getSize(), is(1));
    }

    @Test
    public void archerIsStationary() {
        game.createUnit(new Position(2,0), new UnitImpl(GameConstants.ARCHER, Player.RED));
        assertThat(game.getUnitAt(new Position(2,0 )).getTypeString(), is(GameConstants.ARCHER));
        game.performUnitActionAt(new Position(2,0));
        assertFalse(game.moveUnit(new Position(2,0), new Position(2,1)));
    }

    @Test
    public void settlerUnitOwnerEqualsCityOwner () {
        game.createUnit(new Position(4,3), new UnitImpl(GameConstants.SETTLER, Player.RED));
        assertThat(game.getUnitAt(new Position(4,3)).getOwner(), is (Player.RED));
        game.performUnitActionAt(new Position(4,3));
        assertThat(game.getUnitAt(new Position(4,3)), is(nullValue()));
        assertThat(game.getCityAt(new Position(4,3)).getOwner(), is(Player.RED));
    }

    //********************************TESTING DELTA MAP STRATEGY****************************************************************
    @Test
    public void shouldHaveRedCityAt8_12 (){
        assertThat(game.getCityAt(new Position(8,12)).getOwner(), is(Player.RED));
    }

    @Test
    public void shouldHaveBlueCityAt4_5 () {
        assertThat(game.getCityAt(new Position(4,5)).getOwner(), is(Player.BLUE));
    }

    @Test
    public void shouldHavePlainsAt1_8 () {
        assertThat(game.getTileAt(new Position(1,8)).getTypeString(), is(GameConstants.PLAINS));
    }

    @Test
    public void shouldHaveMountainsAt2_6 () {
        assertThat(game.getTileAt(new Position(2,6)).getTypeString(), is(GameConstants.MOUNTAINS));
    }

    @Test
    public void shouldHaveHillsAt1_3 () {
        assertThat(game.getTileAt(new Position(4,8)).getTypeString(), is(GameConstants.HILLS));
    }

    @Test
    public void shouldHaveForestAt4_4 () {
        assertThat(game.getTileAt(new Position(4,4 )).getTypeString(), is(GameConstants.FOREST));
    }

    //******************************TESTS FROM TEST UTILITY 2 FILES*****************************************************

    @Test public void shouldGiveSum0ForBlueAtP2_4() {
        assertThat("Blue unit at (2,4) should get +0 support",
                Utility2.getFriendlySupport( game, new Position(2,4), Player.BLUE), is(+0));
    }

    @Test public void shouldGiveSum0ForBlueAtP3_2() {
        assertThat("Blue unit at (3,2) should get +0 support",
                Utility2.getFriendlySupport( game, new Position(3,2), Player.BLUE), is(+0));
    }

    @Test public void shouldGiveSum1ForBlueAtP2_4() {
        game.createUnit(new Position(3,5), new UnitImpl(GameConstants.LEGION, Player.BLUE));
        assertThat("Blue unit at (2,4) should get +1 support",
                Utility2.getFriendlySupport( game, new Position(2,4), Player.BLUE), is(+1));
    }

    @Test public void shouldGiveSum3ForRedAtP2_0() {
        game.createUnit(new Position(1,1), new UnitImpl(GameConstants.ARCHER, Player.RED));
        game.createUnit(new Position(3,0), new UnitImpl(GameConstants.ARCHER, Player.RED));
        game.createUnit(new Position(2,1), new UnitImpl(GameConstants.LEGION, Player.RED));
        assertThat("Red unit at (2,0) should get +3 support",
                Utility2.getFriendlySupport( game, new Position(2,0), Player.RED), is(+3));
    }

    @Test public void shouldGiveSum2ForRedAtP1_1() {
        game.createUnit(new Position(1,2), new UnitImpl(GameConstants.ARCHER, Player.RED));
        game.createUnit(new Position(0,0), new UnitImpl(GameConstants.ARCHER, Player.RED));
        assertThat("Red unit at (1,1) should get +2 support",
                Utility2.getFriendlySupport( game, new Position(1,1), Player.RED), is(+2));
    }

    //*****************************TESTING EPSILON WINNING & BATTLE STRATEGY********************************************

    @Test
    public void checkDiceValue() {
        DieRollStrategy rollStrategy = new EpsilonDieRollStrategy();
        int die = rollStrategy.getDieRoll();

        //Die value is between 1-6
        assertThat(ValueRange.of(1, 6).isValidIntValue(die), is(true));
    }

    @Test
    public void redWinsAfter3Attacks() {
        game.createUnit(new Position(15, 9), new UnitImpl(GameConstants.LEGION, Player.RED));
        game.createUnit(new Position(13, 5), new UnitImpl(GameConstants.LEGION, Player.RED));
        game.createUnit(new Position(10, 3), new UnitImpl(GameConstants.LEGION, Player.RED));
        game.createUnit(new Position(15, 8), new UnitImpl(GameConstants.SETTLER, Player.BLUE));
        game.createUnit(new Position(13, 6), new UnitImpl(GameConstants.SETTLER, Player.BLUE));
        game.createUnit(new Position(10, 4), new UnitImpl(GameConstants.SETTLER, Player.BLUE));

        //Red legion to blue settler position
        game.moveUnit(new Position(15, 9), new Position(15, 8));
        numberOfRounds(1);

        //Red legion to blue settler position
        game.moveUnit(new Position(13, 5), new Position(13, 6));
        numberOfRounds(1);

        //Red legion to blue settler position
        game.moveUnit(new Position(10, 3), new Position(10, 4));

        //Red wins three battles
        assertThat(Player.RED, is(game.getUnitAt(new Position(15, 8)).getOwner()));
        assertThat(Player.RED, is(game.getUnitAt(new Position(13, 6)).getOwner()));
        assertThat(Player.RED, is(game.getUnitAt(new Position(10, 4)).getOwner()));

        //Red wins the game
        assertThat(game.getWinner(), is(Player.RED));
    }

    @Test
    public void blueWinsAfter3Attacks() {
        game.createUnit(new Position(15, 9), new UnitImpl(GameConstants.LEGION, Player.BLUE));
        game.createUnit(new Position(13, 5), new UnitImpl(GameConstants.LEGION, Player.BLUE));
        game.createUnit(new Position(10, 3), new UnitImpl(GameConstants.LEGION, Player.BLUE));
        game.createUnit(new Position(15, 8), new UnitImpl(GameConstants.ARCHER, Player.RED));
        game.createUnit(new Position(13, 6), new UnitImpl(GameConstants.ARCHER, Player.RED));
        game.createUnit(new Position(10, 4), new UnitImpl(GameConstants.ARCHER, Player.RED));

        game.endOfTurn();

        //Blue legion to red archer position
        game.moveUnit(new Position(15, 9), new Position(15, 8));
        numberOfRounds(1);

        //Blue legion to red archer position
        game.moveUnit(new Position(13, 5), new Position(13, 6));
        numberOfRounds(1);

        //Blue legion to red archer position
        game.moveUnit(new Position(10, 3), new Position(10, 4));

        //Blue wins three battles
        assertThat(Player.BLUE, is(game.getUnitAt(new Position(15, 8)).getOwner()));
        assertThat(Player.BLUE, is(game.getUnitAt(new Position(13, 6)).getOwner()));
        assertThat(Player.BLUE, is(game.getUnitAt(new Position(10, 4)).getOwner()));

        //Blue wins the game
        assertThat(game.getWinner(), is(Player.BLUE));
    }

    @Test
    public void threeSuccessfulDefeatsWontMakeYouAWinner(){
        assertNull(game.getWinner());
        game.createUnit(new Position(12, 12), new UnitImpl(GameConstants.ARCHER, Player.BLUE));
        game.createUnit(new Position(12, 13), new UnitImpl(GameConstants.LEGION, Player.BLUE));
        game.moveUnit(new Position(12, 12), new Position(12, 13));
        assertThat(game.getUnitAt(new Position(12, 13)).getOwner(), is(Player.BLUE));

        game.endOfTurn();
        game.createUnit(new Position(9, 9), new UnitImpl(GameConstants.ARCHER, Player.RED));
        game.createUnit(new Position(8, 9), new UnitImpl(GameConstants.LEGION, Player.BLUE));
        game.moveUnit(new Position(9, 9), new Position(8, 9));
        assertThat(game.getUnitAt(new Position(8, 9)).getOwner(), is(Player.BLUE));

        game.createUnit(new Position(14, 14), new UnitImpl(GameConstants.ARCHER, Player.BLUE));
        game.createUnit(new Position(13, 14), new UnitImpl(GameConstants.LEGION, Player.RED));
        game.moveUnit(new Position(14, 14), new Position(13, 14));
        assertThat(game.getUnitAt(new Position(13, 14)).getOwner(), is(Player.RED));

        game.createUnit(new Position(14,10 ), new UnitImpl(GameConstants.ARCHER, Player.RED));
        game.createUnit(new Position(13, 9), new UnitImpl(GameConstants.LEGION, Player.BLUE));
        game.moveUnit(new Position(14, 10), new Position(13, 9));
        assertThat(game.getUnitAt(new Position(13, 9)).getOwner(), is(Player.BLUE));

        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void attackerGetsRemovedUponDefeat() {
        game.createUnit(new Position(8,9), new UnitImpl(GameConstants.SETTLER, Player.BLUE));
        game.createUnit(new Position(8,7), new UnitImpl(GameConstants.SETTLER, Player.BLUE));
        game.createUnit(new Position(8,8), new UnitImpl(GameConstants.LEGION, Player.BLUE));
        game.createUnit(new Position(7,8), new UnitImpl(GameConstants.ARCHER, Player.RED));

        //Red archer to blue legion position
        game.moveUnit(new Position(7,8), new Position(8,8));

        //Red archer gets removed
        assertThat(game.getUnitAt(new Position(7,8)), is(nullValue()));

        //Blue remains still
        assertThat(game.getUnitAt(new Position(8,8)).getOwner(), is(Player.BLUE));
    }

    @Test
    public void attackerWinsWithSupport() {
        game.createUnit(new Position(11,11), new UnitImpl(GameConstants.ARCHER, Player.RED));
        game.createUnit(new Position(11,10), new UnitImpl(GameConstants.SETTLER, Player.RED));

        game.createUnit(new Position(12,12), new UnitImpl(GameConstants.LEGION, Player.BLUE));
        assertThat(Utility2.getFriendlySupport(game ,new Position (11,11), game.getUnitAt(new Position (11,11)).getOwner()), is(1));
        assertThat(Utility2.getFriendlySupport(game ,new Position (12,12), game.getUnitAt(new Position (12,12)).getOwner()), is(0));
        assertThat(Utility2.getTerrainFactor(game, new Position(11,11)), is(1));
        assertThat(Utility2.getTerrainFactor(game, new Position(12,12)), is(1));
        game.moveUnit(new Position(11,11), new Position(12,12));
        assertThat(game.getUnitAt(new Position(12,12)).getOwner(), is(Player.RED));
        assertThat(game.getUnitAt(new Position(12,12)).getTypeString(), is(GameConstants.ARCHER));
    }

    @Test
    public void defenderWinsWithSupport() {
        game.createUnit(new Position(11,11), new UnitImpl(GameConstants.LEGION, Player.RED));
        game.createUnit(new Position(12,12), new UnitImpl(GameConstants.LEGION, Player.BLUE));
        game.createUnit(new Position(12,13), new UnitImpl(GameConstants.ARCHER, Player.BLUE));
        game.createUnit(new Position(13,12), new UnitImpl(GameConstants.SETTLER, Player.BLUE));
        assertThat(Utility2.getFriendlySupport(game ,new Position (11,11), game.getUnitAt(new Position (11,11)).getOwner()), is(0));
        assertThat(Utility2.getFriendlySupport(game ,new Position (12,12), game.getUnitAt(new Position (12,12)).getOwner()), is(2));
        assertThat(Utility2.getTerrainFactor(game, new Position(11,11)), is(1));
        assertThat(Utility2.getTerrainFactor(game, new Position(12,12)), is(1));
        game.moveUnit(new Position(11,11), new Position(12,12));
        assertThat(game.getUnitAt(new Position(12,12)).getOwner(), is(Player.BLUE));
        assertThat(game.getUnitAt(new Position(11,11)), is(nullValue()));
    }

    @Test
    public void redArcherGetsTerrainBonusOn() {
        EpsilonBattleStrategy attackingStrategy = new EpsilonBattleStrategy(new DieRollStub());

        //Red archer on hill
        game.createUnit(new Position(1, 3), new UnitImpl(GameConstants.ARCHER, Player.RED));

        int attackingStrength = attackingStrategy.calculateAttackingStrength(game,new Position(1, 3));
        int defensiveStrength = attackingStrategy.calculateDefendingStrength(game,new Position(1, 3));

        //(unitStrength + support) * terrainFactor * dieRoll
        assertThat(attackingStrength, is((2 + 0) * 2 * 1));
        assertThat(defensiveStrength, is((3 + 0) * 2 * 1));
    }
}