package hotciv.standard;
import hotciv.framework.*;

public class ThetaActionStrategy implements ActionStrategy {
    private ActionStrategy gammaActionStrategy = new GammaActionStrategy();

    @Override
    public void unitAction(Position pos, Game game) {
        Unit unitPos = game.getUnitAt(pos);
        String unitType = unitPos.getTypeString();

        if (unitType.equals(GameConstants.UFO)) {
            boolean tileTypeIsForest = game.getTileAt(pos).getTypeString().equals(GameConstants.FOREST);

            if (tileTypeIsForest) {
                ((TileImpl)game.getTileAt(pos)).setTypeString(GameConstants.PLAINS);
            }

            boolean isCityAtPosition = game.getCityAt(pos) != null;
            if (isCityAtPosition) {
                boolean isOwnCity = game.getCityAt(pos).getOwner().equals(game.getPlayerInTurn());
                if (!isOwnCity) {
                    ((CityImpl)game.getCityAt(pos)).incrementSize(-1);
                    if (game.getCityAt(pos).getSize() == 0) {
                        ((GameImpl)game).removeCity(pos);
                    }
                }
            }
        }
        gammaActionStrategy.unitAction(pos, game);
    }
}