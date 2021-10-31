package hotciv.standard;
import hotciv.framework.*;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import java.time.temporal.ValueRange;


public class TestEpsilonCiv {
    private GameImpl game;

    /**
     * Fixture for epsilonciv testing.
     */

    @Before
    public void setUp() {
        game = new GameImpl(new AlphaAgingStrategy(), new EpsilonWinningStrategy(),
                new AlphaActionStrategy(), new AlphaMapStrategy(),
                new EpsilonBattleStrategy(new DieRollStub()));
    }

    private void numberOfRounds(int rounds) {
        for (int i = 0; i < rounds; i++) {
            game.endOfTurn();
            game.endOfTurn();
        }
    }

    @Test
    public void checkDiceValue() {
        DieRollStrategy rollStrategy = new EpsilonDieRollStrategy();
        int die = rollStrategy.getDieRoll();

        //Die value is between 1-6
        assertThat(ValueRange.of(1, 6).isValidIntValue(die), is(true));
    }

    @Test
    public void redWinsAfter3Attacks() {
        game.createUnit(new Position(6, 2), new UnitImpl(GameConstants.LEGION, Player.RED));
        game.createUnit(new Position(4, 5), new UnitImpl(GameConstants.LEGION, Player.RED));
        game.createUnit(new Position(8, 8), new UnitImpl(GameConstants.LEGION, Player.RED));
        game.createUnit(new Position(6, 1), new UnitImpl(GameConstants.SETTLER, Player.BLUE));
        game.createUnit(new Position(5, 5), new UnitImpl(GameConstants.SETTLER, Player.BLUE));
        game.createUnit(new Position(9, 8), new UnitImpl(GameConstants.SETTLER, Player.BLUE));

        //Red legion to blue settler position
        game.moveUnit(new Position(6, 2), new Position(6, 1));
        numberOfRounds(1);

        //Red legion to blue settler position
        game.moveUnit(new Position(4, 5), new Position(5, 5));
        numberOfRounds(1);

        //Red legion to blue settler position
        game.moveUnit(new Position(8, 8), new Position(9, 8));

        //Red wins three battles
        assertThat(Player.RED, is(game.getUnitAt(new Position(6, 1)).getOwner()));
        assertThat(Player.RED, is(game.getUnitAt(new Position(5, 5)).getOwner()));
        assertThat(Player.RED, is(game.getUnitAt(new Position(9, 8)).getOwner()));

        //Red wins the game
        assertThat(game.getWinner(), is(Player.RED));
    }

    @Test
    public void blueWinsAfter3Attacks() {
        game.createUnit(new Position(6, 2), new UnitImpl(GameConstants.LEGION, Player.BLUE));
        game.createUnit(new Position(4, 5), new UnitImpl(GameConstants.LEGION, Player.BLUE));
        game.createUnit(new Position(8, 8), new UnitImpl(GameConstants.LEGION, Player.BLUE));
        game.createUnit(new Position(6, 1), new UnitImpl(GameConstants.ARCHER, Player.RED));
        game.createUnit(new Position(5, 5), new UnitImpl(GameConstants.ARCHER, Player.RED));
        game.createUnit(new Position(9,8), new UnitImpl(GameConstants.ARCHER, Player.RED));

        game.endOfTurn();

        //Blue legion to red archer position
        game.moveUnit(new Position(6, 2), new Position(6, 1));
        numberOfRounds(1);

        //Blue legion to red archer position
        game.moveUnit(new Position(4, 5), new Position(5, 5));
        numberOfRounds(1);

        //Blue legion to red archer position
        game.moveUnit(new Position(8, 8), new Position(9,8));

        //Blue wins three battles
        assertThat(Player.BLUE, is(game.getUnitAt(new Position(6,1)).getOwner()));
        assertThat(Player.BLUE, is(game.getUnitAt(new Position(5,5)).getOwner()));
        assertThat(Player.BLUE, is(game.getUnitAt(new Position(9,8)).getOwner()));

        //Blue wins the game
        assertThat(game.getWinner(), is(Player.BLUE));
    }

    @Test
    public void redDoesNotWinAfter2BattlesWonAnd1BattleLost() {
        game.createUnit(new Position(6, 3), new UnitImpl(GameConstants.LEGION, Player.RED));
        game.createUnit(new Position(8, 8), new UnitImpl(GameConstants.LEGION, Player.RED));
        game.createUnit(new Position(4, 9), new UnitImpl(GameConstants.ARCHER, Player.RED));
        game.createUnit(new Position(6, 4), new UnitImpl(GameConstants.SETTLER, Player.BLUE));
        game.createUnit(new Position(8,9), new UnitImpl(GameConstants.SETTLER, Player.BLUE));
        game.createUnit(new Position(4,8), new UnitImpl(GameConstants.LEGION, Player.BLUE));


        //Red legion to blue settler position
        game.moveUnit(new Position(6, 3), new Position(6,4));
        numberOfRounds(1);

        //Red legion to blue settler position
        game.moveUnit(new Position(8, 8), new Position(8, 9));
        game.endOfTurn();

        //Blue legion to red archer position
        game.moveUnit(new Position(4, 8), new Position(4, 9));

        //Red wins 2, blue wins 1
        assertThat(Player.RED, is(game.getUnitAt(new Position(6, 4)).getOwner()));
        assertThat(Player.RED, is(game.getUnitAt(new Position(8, 9)).getOwner()));
        assertThat(Player.BLUE, is(game.getUnitAt(new Position(4, 9)).getOwner()));

        //Winner is null
        assertThat(game.getWinner(), is(nullValue()));
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
}
