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
}
