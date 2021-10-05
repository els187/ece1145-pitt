package hotciv.standard;
import hotciv.framework.*;

public class GammaActionStrategy implements ActionStrategy {
    @Override
    public void unitAction(Position pos, GameImpl game){
        if (game.getUnitAt(pos) != null) {
            String unit = game.getUnitAt(pos).getTypeString();
            if (unit.equals(GameConstants.ARCHER)) {
                UnitImpl archer = game.getUnitAt(pos);
                if (archer.isFortified()) {
                    archer.deFortify();
                } else {
                    archer.fortify();
                }
            }
            else if (unit.equals(GameConstants.SETTLER)) {
                game.createCity(pos, new CityImpl(game.getUnitAt(pos).getOwner()));
                game.removeUnit(pos);
            }
        }
    }
}
