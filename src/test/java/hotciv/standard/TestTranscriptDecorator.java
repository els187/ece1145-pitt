package hotciv.standard;

import hotciv.framework.*;
import hotciv.standard.*;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestTranscriptDecorator {
    private TranscriptDecorator transcriptDecorator;

    /**
     * Fixture for TranscriptDecorator testing.
     */

    @Before
    public void setUp() {
        transcriptDecorator = new TranscriptDecorator(new GameImpl(new AlphaCivFactory()));
    }

    private void numberOfRounds(int rounds) {
        for (int i = 0; i < rounds; i++) {
            transcriptDecorator.endOfTurn();
            transcriptDecorator.endOfTurn();
        }
    }

    @Test
    public void shouldBeRedAsStartingPlayer() {
        assertThat(transcriptDecorator, is(notNullValue()));
        assertThat(transcriptDecorator.getPlayerInTurn(), is(Player.RED));
    }

    @Test
    public void shouldBeBluesTurnAfterRed() {
        transcriptDecorator.endOfTurn();
        assertThat(transcriptDecorator.getPlayerInTurn(), is(Player.BLUE));
    }
    @Test
    public void startsAtYear4000BC() {
        assertThat(transcriptDecorator.getAge(), is(-4000));
    }

    @Test
    public void ageIncreasesAfterEveryRound() {
        numberOfRounds(1);
        assertThat(transcriptDecorator.getAge(), is(-3900));
    }

    @Test
    public void redWinsIn3000BC() {
        for (int i = 0; i < 10; i++) {
            transcriptDecorator.endOfTurn();
            transcriptDecorator.endOfTurn();
        }
        assertThat(transcriptDecorator.getWinner(), is(Player.RED));
    }

    @Test
    public void shouldHavePlainsEverywhereElse() {
        assertThat(transcriptDecorator.getTileAt(new Position(0, 0)).getTypeString(), is(GameConstants.PLAINS));
        assertThat(transcriptDecorator.getTileAt(new Position(1, 1)).getTypeString(), is(GameConstants.PLAINS));
        assertThat(transcriptDecorator.getTileAt(new Position(3, 3)).getTypeString(), is(GameConstants.PLAINS));
        assertThat(transcriptDecorator.getTileAt(new Position(15, 15)).getTypeString(), is(GameConstants.PLAINS));
    }

    @Test
    public void shouldHaveOceanAt1_0() {
        assertThat(transcriptDecorator.getTileAt(new Position(1, 0)).getTypeString(), is(GameConstants.OCEANS));
    }

    @Test
    public void shouldHaveHillsAt0_1() {
        assertThat(transcriptDecorator.getTileAt(new Position(0, 1)).getTypeString(), is(GameConstants.HILLS));
    }

    @Test
    public void shouldHaveMountainsAt2_2() {
        assertThat(transcriptDecorator.getTileAt(new Position(2, 2)).getTypeString(), is(GameConstants.MOUNTAINS));
    }

    @Test
    public void shouldHaveRedArcherAt2_0(){
        assertThat(transcriptDecorator.getUnitAt(new Position(2,0)).getTypeString(), is(GameConstants.ARCHER));
        assertThat(transcriptDecorator.getUnitAt(new Position(2, 0)).getOwner(), is(Player.RED));
    }

    @Test
    public void shouldHaveBlueLegionAt3_2(){
        assertThat(transcriptDecorator.getUnitAt(new Position(3,2)).getTypeString(), is(GameConstants.LEGION));
        assertThat(transcriptDecorator.getUnitAt(new Position(3, 2)).getOwner(), is(Player.BLUE));
    }

    @Test
    public void shouldHaveRedSettlerAt4_3(){
        assertThat(transcriptDecorator.getUnitAt(new Position(4,3)).getTypeString(), is(GameConstants.SETTLER));
        assertThat(transcriptDecorator.getUnitAt(new Position(4, 3)).getOwner(), is(Player.RED));
    }

    @Test
    public void shouldHaveRedCityAt1_1() {
        assertThat(transcriptDecorator.getCityAt(new Position(1, 1)).getOwner(), is(Player.RED));
    }

    @Test
    public void shouldHaveBlueCityAt4_1() {
        assertThat(transcriptDecorator.getCityAt(new Position(4, 1)).getOwner(), is(Player.BLUE));
    }

    @Test
    public void populationSizeShouldBe1() {
        assertThat(transcriptDecorator.getCityAt(new Position(1, 1)).getSize(), is(1));
        assertThat(transcriptDecorator.getCityAt(new Position(4, 1)).getSize(), is(1));
    }


    @Test
    public void citiesProduce6PerRound() {
        assertThat(transcriptDecorator.getCityAt(new Position(4, 1)), is(notNullValue()));
        numberOfRounds(1);
        assertThat(transcriptDecorator.getCityAt(new Position(4, 1)).getTreasury(), is(6));
    }

    @Test
    public void citiesCanProduceArchers(){
        CityImpl redCity = (CityImpl) transcriptDecorator.getCityAt(new Position(1,1));
        redCity.setProduction(GameConstants.ARCHER);
        assertThat(redCity.getProduction(), is(GameConstants.ARCHER));
    }

    @Test
    public void citiesCanProduceLegions(){
        CityImpl redCity = (CityImpl) transcriptDecorator.getCityAt(new Position(1,1));
        redCity.setProduction(GameConstants.LEGION);
        assertThat(redCity.getProduction(), is(GameConstants.LEGION));
    }

    @Test
    public void citiesCanProduceSettlers(){
        CityImpl redCity = (CityImpl) transcriptDecorator.getCityAt(new Position(1,1));
        redCity.setProduction(GameConstants.SETTLER);
        assertThat(redCity.getProduction(), is(GameConstants.SETTLER));
    }


    @Test
    public void unitsCannotMoveOverMountains(){
        assertThat(transcriptDecorator.moveUnit(new Position(3,2), new Position(2,2)), is(Boolean.FALSE));
    }

    @Test
    public void unitsCannotMoveOverOceans(){
        assertThat(transcriptDecorator.moveUnit(new Position(2,0), new Position(1,0)), is(Boolean.FALSE));
    }

    @Test
    public void archerMovesFrom2_0To2_1(){
        Unit oldArcherUnit = transcriptDecorator.getUnitAt(new Position(2,0));

        assertThat(oldArcherUnit.getTypeString(),is(GameConstants.ARCHER));
        assertThat(transcriptDecorator.moveUnit(new Position(2,0), new Position(2,1)), is(true));
        assertThat(transcriptDecorator.getUnitAt(new Position(2,0)), is(nullValue()));

        Unit newArcherUnit = transcriptDecorator.getUnitAt(new Position(2,1));
        assertThat(newArcherUnit.getTypeString(), is(GameConstants.ARCHER));
    }

    @Test
    public void legionMovesFrom3_2To3_3() {
        transcriptDecorator.endOfTurn();

        Unit oldLegion = transcriptDecorator.getUnitAt((new Position(3, 2)));
        assertThat(oldLegion.getTypeString(),is(GameConstants.LEGION));
        assertThat(transcriptDecorator.moveUnit(new Position(3,2),new Position(3,3)),is(true));
        assertThat(transcriptDecorator.getUnitAt(new Position(3,2)),is(nullValue()));

        Unit newLegion = transcriptDecorator.getUnitAt(new Position(3,3));
        assertThat(newLegion.getTypeString(),is(GameConstants.LEGION));
    }

    @Test
    public void settlerMovesFrom4_3To4_4() {
        Unit oldLegion = transcriptDecorator.getUnitAt((new Position(4, 3)));
        assertThat(oldLegion.getTypeString(),is(GameConstants.SETTLER));
        assertThat(transcriptDecorator.moveUnit(new Position(4,3),new Position(4,4)),is(true));
        assertThat(transcriptDecorator.getUnitAt(new Position(4,3)),is(nullValue()));

        Unit newLegion = transcriptDecorator.getUnitAt(new Position(4,4));
        assertThat(newLegion.getTypeString(),is(GameConstants.SETTLER));
    }


    @Test
    public void totalTreasuryIsDeducted() {
        CityImpl blueCity = (CityImpl) transcriptDecorator.getCityAt(new Position(4,1));
        assertThat(transcriptDecorator.getCityAt(new Position(4, 1)), is(notNullValue()));
        assertThat(transcriptDecorator.getUnitAt(new Position(4, 1)), is(nullValue()));
        blueCity.setProduction(GameConstants.LEGION);
        numberOfRounds(1);
        assertThat(transcriptDecorator.getCityAt(new Position(4, 1)).getTreasury(), is(6));
        numberOfRounds(1);
        assertThat(transcriptDecorator.getCityAt(new Position(4, 1)).getTreasury(), is(12));
        assertThat(transcriptDecorator.getUnitAt(new Position(4, 1)), is(nullValue()));
        numberOfRounds(1);
        assertThat(transcriptDecorator.getUnitAt(new Position(4, 1)).getTypeString(), is(GameConstants.LEGION));
        assertThat(transcriptDecorator.getCityAt(new Position(4, 1)).getTreasury(), is(3));
    }

    @Test
    public void shouldNotBeAbleToMoveMoreThan1Tile() {
        assertThat(transcriptDecorator.moveUnit(new Position(2, 0), new Position(2, 6)),is(Boolean.FALSE));
        assertThat(transcriptDecorator.moveUnit(new Position(2,0), new Position(0,0)), is(Boolean.FALSE));
    }

    @Test
    public void redCantMoveBlueUnits(){
        assertThat(transcriptDecorator.getPlayerInTurn(), is(Player.RED));
        Unit legion = transcriptDecorator.getUnitAt(new Position(3,2));
        assertThat(legion.getOwner(), is(Player.BLUE));
        assertThat(transcriptDecorator.moveUnit(new Position(3,2), new Position(3,1)), is(false));
    }


    @Test
    public void attackShouldAlwaysBeSuccessful() {
        assertThat(transcriptDecorator.moveUnit(new Position(2, 0), new Position(3,0)), is(true));
        numberOfRounds(2);
        assertThat(transcriptDecorator.moveUnit(new Position(3, 0), new Position(3,1)), is(true));
        Unit oldArcherUnit = transcriptDecorator.getUnitAt(new Position(3,1));
        assertThat(oldArcherUnit.getTypeString(),is(GameConstants.ARCHER));
        Unit oldLegion = transcriptDecorator.getUnitAt((new Position(3, 2)));
        assertThat(oldLegion.getTypeString(),is(GameConstants.LEGION));
        numberOfRounds(2);
        assertThat(transcriptDecorator.moveUnit(new Position(3, 1), new Position(3,2)), is(true));
        Unit newArcherUnit = transcriptDecorator.getUnitAt(new Position(3,2));
        assertThat(newArcherUnit.getTypeString(),is(GameConstants.ARCHER));
        assertThat(transcriptDecorator.getUnitAt(new Position(3,1)),is(nullValue()));
    }

    @Test
    public void attackingUnitShouldWin(){
        Unit settler = transcriptDecorator.getUnitAt(new Position(4,3));
        assertThat(settler.getTypeString(), is(GameConstants.SETTLER));
        Unit legion = transcriptDecorator.getUnitAt(new Position(3,2));
        assertThat(legion.getTypeString(), is(GameConstants.LEGION));
        assertThat(transcriptDecorator.moveUnit(new Position(4,3), new Position(3,2)), is(true));
        settler = transcriptDecorator.getUnitAt(new Position(3,2));
        assertThat(settler.getTypeString(),is(GameConstants.SETTLER));
        assertThat(transcriptDecorator.getUnitAt(new Position(4,3)), is(nullValue()));
    }

    @Test
    public void canChangeWorkForceFocusInCityAt(){
        Position p = new Position(1,1);
        transcriptDecorator.changeWorkForceFocusInCityAt(p,GameConstants.foodFocus);
    }

    @Test
    public void canChangeProductionInCityAt(){
        Position p = new Position(1,1);
        transcriptDecorator.changeProductionInCityAt(p, GameConstants.SETTLER);
        assertThat(transcriptDecorator.getCityAt(p).getProduction(), is(GameConstants.SETTLER));
    }

    @Test
    public void canPerformUnitActionAt(){
        Position p = new Position(2,0);
        transcriptDecorator.performUnitActionAt(p);
    }
}
