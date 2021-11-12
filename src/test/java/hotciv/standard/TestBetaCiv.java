package hotciv.standard;

import hotciv.framework.*;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestBetaCiv {
    private Game game;

    /**
     * Fixture for BetaCiv testing.
     */

    @Before
    public void setUp() {
        game = new GameImpl(new BetaCivFactory());
    }

    private void numberOfRounds(int rounds) {
        for (int i = 0; i < rounds; i++) {
            game.endOfTurn();
            game.endOfTurn();
        }
    }

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


    @Test
    public void conqueringAllCitiesIsTheWinner() {
        game.moveUnit(new Position(4,3), new Position(4,2));
        numberOfRounds(2);
        game.moveUnit(new Position(4,2), new Position(4,1));
        assertThat(game.getWinner(), is(Player.RED));
    }

    @Test
    public void redConquersBlueCity() {
        game.moveUnit(new Position (4, 3), new Position (4,2));
        game.endOfTurn();
        game.endOfTurn();
        game.moveUnit(new Position (4, 2), new Position (4,1));
        City c = game.getCityAt(new Position(4,1));
        assertThat(c.getOwner(), is(Player.RED));
        assertThat(game.getWinner(), is(Player.RED));
    }
}