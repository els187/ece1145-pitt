package hotciv.standard;
import hotciv.framework.*;

public class GammaActionStrategy implements ActionStrategy {
    @Override
    public void unitAction(Position pos, Game game){
        if (game.getUnitAt(pos) != null) {
            String unit = game.getUnitAt(pos).getTypeString();
            if (unit.equals(GameConstants.ARCHER)) {
                Unit archer = game.getUnitAt(pos);
                if (((UnitImpl) game.getUnitAt(pos)).isFortified()) {
                    ((UnitImpl)archer).deFortify();
                } else {
                    ((UnitImpl)archer).fortify();
                }
            }
            else if (unit.equals(GameConstants.SETTLER)) {
                ((GameImpl)game).createCity(pos, new CityImpl(game.getUnitAt(pos).getOwner()));
                ((GameImpl)game).removeUnit(pos);
            }
        }
    }
}
