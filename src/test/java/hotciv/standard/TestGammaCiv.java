package hotciv.standard;

import hotciv.framework.*;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.*;

public class TestGammaCiv {
    private Game game;

    /**
     * Fixture for gammaciv testing.
     */

    @Before
    public void setUp() {
        game = new GameImpl(new AlphaAgingStrategy(), new AlphaWinningStrategy(), new DeltaMapStrategy()) {
        };
    }

    @Test
    public void shouldTurnSettlerIntoCity4_3(){
        game.performUnitActionAt(new Position(4,3));
        City c = game.getCityAt(new Position(4,3));
        assertThat("Should not have settler at 4,3", game.getUnitAt(new Position(4,3)),is(nullValue()));
        assertEquals("Should be a red city at 4,3", Player.RED,c.getOwner());
    }
    @Test
    public void shouldFortifyArcherAt2_0(){
        game.performUnitActionAt(new Position(2,0));
        Unit u = game.getUnitAt(new Position(2,0));
        assertEquals("Defense should be 6", 6, u.getDefensiveStrength());
        assertThat("Should not be able to move fortified archer", game.moveUnit(new Position(2,0), new Position(2,1)), is(false));
    }

    @Test
    public void shouldUndoFortifyArcherAt2_0(){
        game.performUnitActionAt(new Position(2,0));
        Unit u = game.getUnitAt(new Position(2,0));
        assertEquals("Defense should be 6", 6, u.getDefensiveStrength());
        game.performUnitActionAt(new Position(2,0));
        assertEquals("Defense should be 3", 3, u.getDefensiveStrength());
        assertThat("Should be able to move non-fortified archer", game.moveUnit(new Position(2,0), new Position(2,1)), is(true));
    }

}