package hotciv.standard;

import hotciv.framework.*;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestZetaCiv {
    private Game game;

    /**
     * Fixture for ZetaCiv testing.
     */

    @Before
    public void setUp() {
        game = new GameImpl(new ZetaCivFactory());
    }

    private void numberOfRounds(int rounds) {
        for (int i = 0; i < rounds; i++) {
            game.endOfTurn();
            game.endOfTurn();
        }
    }

    @Test
    public void noWinnerWhenOwningAllCitiesAfter20() {
        game.moveUnit(new Position(2,0), new Position(3,0));

        numberOfRounds(20);

        game.moveUnit(new Position(3, 0), new Position(4, 1));
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void noWinnerWhenWinning3AttacksBefore20() {
        game.moveUnit(new Position(2, 0), new Position(3, 1));

        game.changeProductionInCityAt(new Position(1, 1), GameConstants.SETTLER);
        game.endOfTurn();


        // BLUE LEGION wins when attacking RED SETTLER
        game.moveUnit(new Position(3, 2), new Position(4, 3));

        numberOfRounds(1);

        game.moveUnit(new Position(4, 3), new Position(3, 2));

        numberOfRounds(1);

        // BLUE LEGION wins when attacking RED ARCHER
        game.moveUnit(new Position(3, 2), new Position(3, 1));
        game.endOfTurn();

        numberOfRounds(2);

        game.moveUnit(new Position(1, 1), new Position(2, 1));
        game.endOfTurn();

        // BLUE LEGION wins when attacking RED SETTLER
        game.moveUnit(new Position(3, 1), new Position(2, 1));
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void winnerWhenWinning3AttacksAfter20() {
        numberOfRounds(19);

        game.changeProductionInCityAt(new Position(1, 1), GameConstants.ARCHER);
        game.moveUnit(new Position(2, 0), new Position(3, 1));
        game.moveUnit(new Position(4, 3), new Position(3, 3));
        game.endOfTurn();

        // BLUE LEGION wins when attacking RED ARCHER
        game.moveUnit(new Position(3, 2), new Position(3, 1));
        game.endOfTurn();

        game.moveUnit(new Position(3, 3), new Position(3, 2));
        game.endOfTurn();

        // BLUE LEGION wins when attacking RED SETTLER
        game.moveUnit(new Position(3, 1), new Position(3, 2));
        assertThat(game.getWinner(), is(nullValue()));
        game.endOfTurn();

        game.moveUnit(new Position(1, 1), new Position(2, 1));
        game.endOfTurn();

        //BLUE LEGION wins when attacking RED ARCHER
        game.moveUnit(new Position(3, 2), new Position(2, 1));
        assertThat(game.getWinner(), is(Player.BLUE));
    }

}
