package hotciv.standard;

import hotciv.framework.*;
import hotciv.standard.*;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestThetaCiv {
    private GameImpl game;

    /**
     * Fixture for ThetaCiv testing.
     */

    @Before
    public void setUp() {
        game = new GameImpl(new ThetaCivFactory());
    }

    private void numberOfRounds(int rounds) {
        for (int i = 0; i < rounds; i++) {
            game.endOfTurn();
            game.endOfTurn();
        }
    }

    @Test
    public void canCreateUFO(){
        game.createUnit(new Position(8, 8), new UnitImpl(GameConstants.UFO, Player.RED));
        assertThat(game.getUnitAt(new Position(8, 8)).getTypeString(), is(GameConstants.UFO));
    }

    @Test
    public void UFOShouldHaveAttackStrength1() {
        game.createUnit(new Position(8, 8), new UnitImpl(GameConstants.UFO, Player.RED));
        assertThat(game.getUnitAt(new Position(8, 8)).getAttackingStrength(), is(1));
    }

    @Test
    public void UFOShouldHaveDefensiveStrength8() {
        game.createUnit(new Position(8, 8), new UnitImpl(GameConstants.UFO, Player.RED));
        assertThat(game.getUnitAt(new Position(8, 8)).getDefensiveStrength(), is(8));
    }

    @Test
    public void UFOShouldHave2MoveCountsInitially() {
        game.createUnit(new Position(8, 8), new UnitImpl(GameConstants.UFO, Player.RED));
        assertThat(game.getUnitAt(new Position(8, 8)).getMoveCount(), is(2));
    }

    @Test
    public void shouldBeAbleToChangeProductionToUFO(){
        ((CityImpl)game.getCityAt(new Position(1, 1))).setWorkforceFocus(GameConstants.productionFocus);
        game.changeProductionInCityAt(new Position(1, 1), GameConstants.UFO);

        assertThat(GameConstants.productionFocus, is(game.getCityAt(new Position(1, 1)).getWorkforceFocus()));
        assertThat(GameConstants.UFO, is(game.getCityAt(new Position(1, 1)).getProduction()));
    }

    @Test
    public void redCanProduceUFOFor60Production() {
        game.changeProductionInCityAt(new Position(1, 1), GameConstants.UFO);
        numberOfRounds(20);
        assertThat(game.getCityAt(new Position(1, 1)).getProduction(), is(GameConstants.UFO));
        assertThat(game.getCityAt(new Position(1, 1)).getTreasury(), is(0));
        assertThat(game.getUnitAt(new Position(1, 1)).getTypeString(), is(GameConstants.UFO));
    }

    @Test
    public void cannotProduceUFOWhenTreasuryIsNotEnough (){
        game.createCity(new Position(12,12), new CityImpl(Player.RED));
        ((CityImpl)game.getCityAt(new Position(12,12))).setProduction(GameConstants.UFO);
        assertThat(game.getCityAt(new Position(12,12)).getProduction(), is(GameConstants.UFO));
        ((CityImpl)game.getCityAt(new Position(12,12))).setTreasury(53);
        assertThat(game.getCityAt(new Position(12,12)).getTreasury(), is(53));
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getCityAt(new Position(12,12)).getTreasury(), is(59));
        assertNull(game.getUnitAt(new Position(12,12)));
    }

    @Test
    public void UFOShouldBeAbleToMoveOverMountains() {
        Position mountains = new Position(2, 2);
        game.createUnit(new Position(3, 3), new UnitImpl(GameConstants.UFO, Player.RED));
        game.moveUnit(new Position(3, 3), new Position(2,3));
        game.moveUnit(new Position(2,3), mountains);
        assertThat(game.getUnitAt(mountains).getTypeString(), is(GameConstants.UFO));
    }

    @Test
    public void UFOShouldBeAbleToMoveOverOceans() {
        Position ocean = new Position(1,0 );
        game.createUnit(new Position(0, 0), new UnitImpl(GameConstants.UFO, Player.RED));
        game.moveUnit(new Position(0, 0), ocean);
        assertThat(game.getUnitAt(ocean).getTypeString(), is(GameConstants.UFO));
        game.moveUnit(ocean,new Position(0, 0));
        assertThat(game.getUnitAt(ocean), is(nullValue()));
        assertThat(game.getUnitAt(new Position(0, 0)).getTypeString(), is(GameConstants.UFO));
    }

    @Test
    public void UFOShouldBeAbleToMoveOverHills() {
        Position hills = new Position(1,0 );
        game.createUnit(new Position(0, 0), new UnitImpl(GameConstants.UFO, Player.RED));
        game.moveUnit(new Position(0, 0), hills);
        assertThat(game.getUnitAt(hills).getTypeString(), is(GameConstants.UFO));
        game.moveUnit(hills,new Position(0, 0));
        assertThat(game.getUnitAt(hills), is(nullValue()));
        assertThat(game.getUnitAt(new Position(0, 0)).getTypeString(), is(GameConstants.UFO));
    }

    @Test
    public void legionCannotMoveOverMountains(){
        assertThat(game.moveUnit(new Position(3,2), new Position(2,2)), is(Boolean.FALSE));
    }

    @Test
    public void archerCannotMoveOverOceans(){
        assertThat(game.moveUnit(new Position(2,0), new Position(1,0)), is(Boolean.FALSE));
    }

    @Test
    public void archerCannotMoveMoreThanOnce() {
        game.createUnit(new Position(0, 6), new UnitImpl(GameConstants.ARCHER, Player.RED));
        assertThat(game.moveUnit(new Position(0, 6), new Position(0,7)), is(Boolean.TRUE));
        assertThat(game.moveUnit(new Position(0,7), new Position(0,8)), is(Boolean.FALSE));
    }

    @Test
    public void UFOCanFlyOverMountainsAndOceans (){
        game.createUnit(new Position(10,10), new UnitImpl(GameConstants.UFO, Player.RED));
        assertThat(game.getUnitAt(new Position(10,10)).getTypeString(), is(GameConstants.UFO));
        game.createTile(new Position(10,11), new TileImpl(GameConstants.MOUNTAINS));
        game.moveUnit(new Position(10,10), new Position(10,11));
        assertThat(game.getUnitAt(new Position(10,11)).getTypeString(), is(GameConstants.UFO));
        game.createTile(new Position(11,12), new TileImpl(GameConstants.OCEANS));
        game.moveUnit(new Position(10,11), new Position(11,12));
        assertThat(game.getUnitAt(new Position(11,12)).getTypeString(), is(GameConstants.UFO));
    }

    @Test
    public void ufoCanFlyOverEnemyCities (){
        game.createCity(new Position(14,14), new CityImpl(Player.BLUE));
        assertNull(game.getUnitAt(new Position(14,14)));
        assertThat(game.getCityAt(new Position(14,14)).getOwner(), is(Player.BLUE));
        game.createUnit(new Position(14,13), new UnitImpl(GameConstants.UFO, Player.RED));
        game.moveUnit(new Position(14,13), new Position (14,14));
        assertThat(game.getUnitAt(new Position(14,14)).getOwner(), is(Player.RED));
    }

    @Test
    public void ufoCanConquerEnemyCity (){
        game.createCity(new Position(14,14), new CityImpl(Player.BLUE));
        game.createUnit(new Position (14,14), new UnitImpl(GameConstants.ARCHER, Player.BLUE));
        assertThat(game.getUnitAt(new Position (14,14)).getTypeString(), is(GameConstants.ARCHER));
        game.createUnit(new Position(14,15), new UnitImpl(GameConstants.UFO, Player.RED));
        game.moveUnit(new Position(14,15), new Position(14,14));
        assertThat(game.getUnitAt(new Position(14,14)).getTypeString(), is(GameConstants.UFO));
        assertThat(game.getUnitAt(new Position(14,14)).getOwner(), is(Player.RED));
    }

    @Test
    public void UFOCannotConquerCityWithoutEnemyUnit() {
        game.createUnit(new Position(6, 1), new UnitImpl(GameConstants.UFO, Player.RED));
        game.moveUnit(new Position(6, 1), new Position(5,1));
        game.moveUnit(new Position(5,1), new Position(4, 1));
        assertThat(game.getCityAt(new Position(4, 1)).getOwner(), is(Player.BLUE));
    }

    @Test
    public void redUFOShouldAttackEnemyUnitWhenTheUnitOccupiesACity() {
        game.createUnit(new Position(4, 1), new UnitImpl(GameConstants.LEGION, Player.BLUE));
        game.createUnit(new Position(4, 2), new UnitImpl(GameConstants.UFO, Player.RED));
        game.moveUnit(new Position(4, 2), new Position(4, 1));

        assertThat(game.getUnitAt(new Position(4, 1)).getTypeString(), is(GameConstants.UFO));
        assertThat(game.getUnitAt(new Position(4, 2)), is(nullValue()));
    }

    @Test
    public void UFODecreasesPopulationBy1 (){
        game.createCity(new Position(12,12), new CityImpl(Player.BLUE));
        assertThat(game.getCityAt(new Position(12,12)).getOwner(), is(Player.BLUE));
        game.createUnit(new Position(12,12), new UnitImpl(GameConstants.UFO, Player.RED));
        assertThat(game.getUnitAt(new Position (12,12)).getOwner(), is(Player.RED));
        assertThat(game.getCityAt(new Position(12,12)).getSize(), is(1));
        ((CityImpl)game.getCityAt(new Position(12,12))).setSize(8);
        assertThat(game.getCityAt(new Position(12,12)).getSize(), is(8));
        game.performUnitActionAt(new Position(12,12));
        assertThat(game.getCityAt(new Position(12,12)).getSize(), is(7));
        game.performUnitActionAt(new Position(12,12));
        assertThat(game.getCityAt(new Position(12,12)).getSize(), is(6));
        game.performUnitActionAt(new Position(12,12));
        assertThat(game.getCityAt(new Position(12,12)).getSize(), is(5));
    }

    @Test
    public void UFOCannotRemoveFriendlyCity() {
        game.createCity(new Position(12,12), new CityImpl(Player.RED));
        assertThat(game.getCityAt(new Position(12,12)).getOwner(), is(Player.RED));
        assertThat(game.getCityAt(new Position(12,12)).getSize(), is(1));
        game.createUnit(new Position(12,12), new UnitImpl(GameConstants.UFO, Player.RED));
        assertThat(game.getUnitAt(new Position (12,12)).getOwner(), is(Player.RED));
        game.performUnitActionAt(new Position(12,12));
        assertThat(game.getCityAt(new Position(12,12)).getSize(), is(1));
    }

    @Test
    public void UFOCanTurnForestIntoPlains() {
        game.createTile(new Position(10,10), new TileImpl(GameConstants.FOREST));
        assertThat(game.getTileAt(new Position (10,10)).getTypeString(), is(GameConstants.FOREST));
        game.createUnit(new Position(10,10), new UnitImpl(GameConstants.UFO, Player.RED));
        game.performUnitActionAt(new Position (10,10));
        assertThat(game.getTileAt(new Position (10,10)).getTypeString(), is(GameConstants.PLAINS));
    }

    @Test
    public void UFOCanTurnOceansIntoPlains () {
        game.createTile(new Position(10,10), new TileImpl(GameConstants.OCEANS));
        assertThat(game.getTileAt(new Position (10,10)).getTypeString(), is(GameConstants.OCEANS));
        game.createUnit(new Position(10,10), new UnitImpl(GameConstants.UFO, Player.RED));
        game.performUnitActionAt(new Position (10,10));
        assertThat(game.getTileAt(new Position (10,10)).getTypeString(), is(GameConstants.OCEANS));
    }

    @Test
    public void UFOCanTurnHillsIntoPlains() {
        game.createTile(new Position(10,10), new TileImpl(GameConstants.HILLS));
        assertThat(game.getTileAt(new Position (10,10)).getTypeString(), is(GameConstants.HILLS));
        game.createUnit(new Position(10,10), new UnitImpl(GameConstants.UFO, Player.RED));
        game.performUnitActionAt(new Position (10,10));
        assertThat(game.getTileAt(new Position (10,10)).getTypeString(), is(GameConstants.HILLS));
    }

    @Test
    public void UFOCanFly2Distance(){
        game.createUnit(new Position(10,10), new UnitImpl(GameConstants.UFO, Player.RED));
        assertThat(game.getUnitAt(new Position(10,10)).getTypeString(), is(GameConstants.UFO));
        game.moveUnit(new Position(10,10), new Position(10,11));
        assertThat(game.getUnitAt(new Position(10,11)).getTypeString(), is(GameConstants.UFO));
        assertTrue(game.moveUnit(new Position(10,11), new Position(11,12)));
    }

    @Test
    public void UFOShouldHave0MovesLeftAfterMovingTwice() {
        game.createUnit(new Position(8, 8), new UnitImpl(GameConstants.UFO, Player.RED));
        assertThat(game.getUnitAt(new Position(8, 8)).getMoveCount(), is(2));
        game.moveUnit(new Position(8, 8), new Position(8,9));
        assertThat(game.getUnitAt(new Position(8,9)).getMoveCount(), is(1));
        game.moveUnit(new Position(8,9),new Position(9,9));
        assertThat(game.getUnitAt(new Position(9,9)).getMoveCount(), is(0));
    }

    @Test
    public void UFOShouldNotFlyDistanceOver2 (){
        game.createUnit(new Position(10,10), new UnitImpl(GameConstants.UFO, Player.RED));
        assertThat(game.getUnitAt(new Position(10,10)).getTypeString(), is(GameConstants.UFO));
        game.moveUnit(new Position(10,10), new Position(10,11));
        assertThat(game.getUnitAt(new Position(10,11)).getTypeString(), is(GameConstants.UFO));
        game.moveUnit(new Position(10,11), new Position(11,12));
        assertThat(game.getUnitAt(new Position(11,12)).getTypeString(), is(GameConstants.UFO));
        assertThat(game.moveUnit(new Position(11,12), new Position(11,13)), is(false));
    }

    @Test
    public void redUFOShouldRemoveBlueCity() {
        game.createUnit(new Position(6, 1), new UnitImpl(GameConstants.UFO, Player.RED));
        game.moveUnit(new Position(6, 1), new Position(5,1));
        game.moveUnit(new Position(5,1),new Position(4, 1));
        assertThat(game.getUnitAt(new Position(4, 1)).getTypeString(), is(GameConstants.UFO));
        game.performUnitActionAt(new Position(4, 1));
        assertThat(game.getCityAt(new Position(4, 1)), is(nullValue()));
    }

    @Test
    public void newCityHasSize1 () {
        assertThat(game.getUnitAt(new Position(4,3)), is (notNullValue()));
        game.performUnitActionAt(new Position(4,3));
        assertThat(game.getUnitAt(new Position(4,3)), is(nullValue()));
        assertThat(game.getCityAt(new Position(4,3)).getSize(), is(1));
    }

    @Test
    public void cityOwnerEqualsSettlerOwner () {
        assertThat(game.getUnitAt(new Position(4,3)).getOwner(), is (Player.RED));
        game.performUnitActionAt(new Position(4,3));
        assertThat(game.getUnitAt(new Position(4,3)), is(nullValue()));
        assertThat(game.getCityAt(new Position(4,3)).getOwner(), is(Player.RED));
    }

    @Test
    public void archerAt2_0defensiveStrengthIsDoubled () {
        assertThat(game.getUnitAt(new Position(2,0)).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getUnitAt(new Position(2,0 )).getDefensiveStrength(), is(3));
        game.performUnitActionAt(new Position(2,0 ));
        assertThat(game.getUnitAt(new Position(2,0 )).getDefensiveStrength(), is(3 * 2));
    }

    @Test
    public void settlerAt4_3IsReplacedWithACity (){
        assertThat(game.getUnitAt(new Position(4,3)), is (notNullValue()));
        game.performUnitActionAt(new Position(4,3));
        assertThat(game.getUnitAt(new Position(4,3)), is(nullValue()));
        assertThat(game.getCityAt(new Position(4,3)), is(notNullValue()));
    }
}