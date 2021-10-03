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
        game = new GameImpl(new BetaAgingStrategy(), new BetaWinningStrategy());
    }

    private void numberOfRounds(int rounds) {
        for (int i = 0; i < rounds; i++) {
            game.endOfTurn();
            game.endOfTurn();
        }
    }

    @Test
    public void worldAgesCorrectly() {
        assertEquals(-4000, game.getAge());
        numberOfRounds(39);
        assertEquals(-100, game.getAge());
        numberOfRounds(1);
        assertEquals(-1, game.getAge());
        numberOfRounds(1);
        assertEquals(1, game.getAge());
        numberOfRounds(1);
        assertEquals(50, game.getAge());
        numberOfRounds(34);
        assertEquals(1750, game.getAge());
        numberOfRounds(6);
        assertEquals(1900, game.getAge());
        numberOfRounds(14);
        assertEquals(1970, game.getAge());
        numberOfRounds(1);
        assertEquals(1971, game.getAge());
        numberOfRounds(1);
        assertEquals(1972, game.getAge());
    }
}