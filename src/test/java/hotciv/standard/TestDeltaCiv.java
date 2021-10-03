package hotciv.standard;
import hotciv.framework.*;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.*;

public class TestDeltaCiv {
    private Game game;

    @Before
    public void setUp() {
        game = new GameImpl(new AlphaAgingStrategy(), new AlphaWinningStrategy());
    }

    @Test
    public void shouldHaveRedCityat8_12() {
        assertThat(game, is(notNullValue()));
        assertThat(game.getCityAt(new Position(8,12)).getOwner(), is(Player.RED));
    }

    @Test
    public void shouldHaveBlueCityat4_5(){
        assertThat(game, is(notNullValue()));
        assertThat(game.getCityAt(new Position(4,5)).getOwner(), is(Player.BLUE));
    }

}
