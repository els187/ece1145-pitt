package hotciv.standard;

import hotciv.framework.*;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestEtaCiv {
    private Game game;

    /**
     * Fixture for EtaCiv testing.
     */

    @Before
    public void setUp() {
        game = new GameImpl(new EtaCivFactory());
    }

    private void numberOfRounds(int rounds) {
        for (int i = 0; i < rounds; i++) {
            game.endOfTurn();
            game.endOfTurn();
        }
    }

    @Test
    public void citiesCanChangeWorkFocus() {
        CityImpl city = (CityImpl) game.getCityAt(new Position(1, 1));
        assertThat(city.getWorkforceFocus(), is(GameConstants.productionFocus));
        city.setWorkforceFocus(GameConstants.foodFocus);
        assertThat(city.getWorkforceFocus(), is(GameConstants.foodFocus));
    }

    @Test
    public void citiesStartWith0Food() {
        CityImpl redCity = (CityImpl) game.getCityAt(new Position(1, 1));
        CityImpl blueCity = (CityImpl) game.getCityAt(new Position(4, 1));
        assertThat(redCity.getFoodAmount(), is(0));
        assertThat(blueCity.getFoodAmount(), is(0));
    }

    @Test
    public void citiesStartWith0Production() {
        CityImpl redCity = (CityImpl) game.getCityAt(new Position(1, 1));
        CityImpl blueCity = (CityImpl) game.getCityAt(new Position(4, 1));
        assertThat(redCity.getTreasury(), is(0));
        assertThat(blueCity.getTreasury(), is(0));
    }

    @Test
    public void citiesIncreaseWith1FoodAnd1Production() {
        CityImpl city = (CityImpl) game.getCityAt(new Position(1, 1));
        numberOfRounds(1);
        assertThat(city.getFoodAmount(), is(1));
        assertThat(city.getTreasury(), is(1));
        numberOfRounds(2);
        assertThat(city.getFoodAmount(), is(3));
        assertThat(city.getTreasury(), is(3));
        numberOfRounds(1);
        assertThat(city.getFoodAmount(), is(4));
        assertThat(city.getTreasury(), is(4));
    }

    @Test
    public void cityIncreasesPopulationFrom1To2At8FoodTotalAndLosesAllFood() {
        CityImpl city = (CityImpl) game.getCityAt(new Position(1, 1));
        numberOfRounds(7);
        assertThat(city.getFoodAmount(), is(7));
        assertThat(city.getSize(), is(1));
        numberOfRounds(1);
        assertThat(city.getFoodAmount(), is(0));
        assertThat(city.getSize(), is(2));
        assertThat(city.getFoodAmount(), is(0));
    }

    @Test
    public void cityShouldHave4FoodWithPlainAdjacent() {
        // 1 food + 3 food = 4 food
        CityImpl city = (CityImpl) game.getCityAt(new Position(1, 1));
        city.setWorkforceFocus(GameConstants.foodFocus);
        numberOfRounds(8);
        assertThat(city.getSize(), is(2));
        assertThat(city.getFoodAmount(), is(0));
        numberOfRounds(1);
        assertThat(city.getFoodAmount(), is(4));
    }

    @Test
    public void cityShouldHave11TreasuryWithHillsAdjacent() {
        // 1 production + 2 production = 3
        CityImpl city = (CityImpl) game.getCityAt(new Position(1, 1));
        city.setWorkforceFocus(GameConstants.productionFocus);
        city.setProduction(GameConstants.LEGION);
        numberOfRounds(8);
        assertThat(city.getSize(), is(2));
        assertThat(city.getTreasury(), is(8));
        numberOfRounds(1);
        assertThat(city.getTreasury(), is(11));
    }

    @Test
    public void citiesCannotExceedSize9() {
        CityImpl city = (CityImpl) game.getCityAt(new Position(4, 1));
        city.setWorkforceFocus(GameConstants.foodFocus);
        city.incrementSize(8);
        assertThat(city.getSize(), is(9));
        city.setFoodAmount(5 + city.getSize() * 3);
        assertThat(city.getFoodAmount(), is(32));
        assertThat(city.getSize(), is(9));
    }

    @Test
    public void ShouldGiveCorrectCityProduction() {
        CityImpl city = (CityImpl) game.getCityAt(new Position(4, 1));
        assertThat(0, is(city.getFoodAmount()));
        assertThat(0, is(city.getTreasury()));
        numberOfRounds(1);
        assertThat(1, is(city.getFoodAmount()));
        assertThat(1, is(city.getTreasury()));
    }

    @Test
    public void shouldAddPopulationCorrectly() {
        City city = game.getCityAt(new Position(4, 1));
        CityImpl c = (CityImpl) city;
        c.incrementSize(4);
        assertThat(city.getSize(), is(5));

        c.setFoodAmount(5 + city.getSize() * 3);
        assertThat(c.getFoodAmount(), is(20));
        numberOfRounds(1);
        assertThat(city.getSize(), is(6));
        assertThat(c.getFoodAmount(), is(0));
    }
}