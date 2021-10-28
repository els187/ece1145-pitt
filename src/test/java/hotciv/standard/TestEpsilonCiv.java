package hotciv.standard;
import hotciv.framework.*;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestEpsilonCiv {
    private Game game;

    /**
     * Fixture for epsilonciv testing.
     */

    @Before
    public void setUp() {
        game = new GameImpl(new BetaAgingStrategy(), new BetaWinningStrategy(), new AlphaActionStrategy(), new AlphaMapStrategy(), new EpsilonBattleStrategy());
    }

    private void numberOfRounds(int rounds) {
        for (int i = 0; i < rounds; i++) {
            game.endOfTurn();
            game.endOfTurn();
        }
    }

    @Test
    public void shouldHaveCorrectTerrainFactors() {
        //cities
        assertThat(Utility2.getTerrainFactor(game, new Position(1,1)), is(3));
        //hills
        assertThat(Utility2.getTerrainFactor(game, new Position(0,1)), is(2));
        //plains
        assertThat(Utility2.getTerrainFactor(game, new Position(0,0)), is(1));
    }

}
