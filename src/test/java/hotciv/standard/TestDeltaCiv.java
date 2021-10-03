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

    }

    @Test
    public void shouldHaveBlueCityat4_5(){

    }

}
