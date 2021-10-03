package hotciv.standard;

import hotciv.framework.*;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestBetaCiv {
    private Game game;

    /**
     * Fixture for betaciv testing.
     */
    @Before
    public void setUp() {
        game = new GameImpl(new BetaAgingStrategy(), new AlphaWinningStrategy());
    }

    private void numberOfRounds(int rounds) {
        for (int i = 0; i < rounds; i++) {
            // call endOfTurn twice to play one round, since there are two players
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
}
