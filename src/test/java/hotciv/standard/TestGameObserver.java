package hotciv.standard;

import hotciv.framework.*;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestGameObserver {
    private Game game;
    private GameObserverStub gameObs;

    /**
     * Fixture for GameObserver testing.
     */

    @Before
    public void setUp() {
        game = new GameImpl(new SemiCivFactory(new DieRollStub()));
        gameObs = new GameObserverStub();
        game.addObserver(gameObs);
    }

    private void numberOfRounds(int rounds) {
        for (int i = 0; i < rounds; i++) {
            game.endOfTurn();
            game.endOfTurn();
        }
    }

    @Test
    public void shouldNotifyWhenWorldChanges() {
        ((GameImpl)game).createUnit(new Position(2, 2), new UnitImpl(GameConstants.ARCHER, Player.RED));
        //Unit created
        System.out.println(gameObs.getWorldChangedAtOutput(0));
        assertEquals(gameObs.getWorldChangedAtOutput(0), "World changed at: [2,2].");

        ((GameImpl)game).createCity(new Position(2, 3), new CityImpl(Player.RED));
        //City created
        System.out.println(gameObs.getWorldChangedAtOutput(1));
        assertEquals(gameObs.getWorldChangedAtOutput(1), "World changed at: [2,3].");

        game.moveUnit(new Position(2, 2), new Position(2, 3));

        //Unit removed from source
        System.out.println(gameObs.getWorldChangedAtOutput(2));
        assertEquals(gameObs.getWorldChangedAtOutput(2), "World changed at: [2,2].");

        //Unit moved to destination
        System.out.println(gameObs.getWorldChangedAtOutput(3));
        assertEquals(gameObs.getWorldChangedAtOutput(3), "World changed at: [2,3].");

        //Unit performs action
        game.performUnitActionAt(new Position(2,3));
        System.out.println(gameObs.getWorldChangedAtOutput(4));
        assertEquals(gameObs.getWorldChangedAtOutput(4), "World changed at: [2,3].");
    }

    @Test
    public void shouldNotifyWhenTurnEnds() {
        game.endOfTurn();
        assertEquals(gameObs.getTurnEndsOutput(0),"Next player in turn is: BLUE and the age is: -4000.");
        game.endOfTurn();
        assertEquals(gameObs.getTurnEndsOutput(1),"Next player in turn is: RED and the age is: -3900.");
        numberOfRounds(1);
        assertEquals(gameObs.getTurnEndsOutput(2),"Next player in turn is: BLUE and the age is: -3900.");
        assertEquals(gameObs.getTurnEndsOutput(3),"Next player in turn is: RED and the age is: -3800.");
    }

    @Test
    public void shouldNotifyWhenTileFocusIsChanged() {
        game.setTileFocus(new Position(0,2));
        assertEquals(gameObs.getTileFocusChangedAtOutput(0), "Tile focus changed at: [0,2].");
    }
}