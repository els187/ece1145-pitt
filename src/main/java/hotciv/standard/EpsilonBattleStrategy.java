package hotciv.standard;
import hotciv.framework.*;

public class EpsilonBattleStrategy implements BattleStrategy {
    public int calculateAttackingStrength(Game game, Position from){
        Unit attackingUnit = game.getUnitAt(from);
        int attackingStrength = attackingUnit.getAttackingStrength();
        attackingStrength += Utility2.getFriendlySupport(game, from, attackingUnit.getOwner());
        attackingStrength *= Utility2.getTerrainFactor(game, from);
        return attackingStrength;
    }

    public int calculateDefendingStrength(Game game, Position to){
        Unit defendingUnit = game.getUnitAt(to);
        int defendingStrength = defendingUnit.getDefensiveStrength();
        defendingStrength += Utility2.getFriendlySupport(game, to, defendingUnit.getOwner());
        defendingStrength *= Utility2.getTerrainFactor(game, to);
        return defendingStrength;
    }

    public boolean getBattleOutcome(Game game, Position from, Position to){
        //O calculation from the book
        if (calculateAttackingStrength(game,from) * getDieRoll() > calculateDefendingStrength(game, to) * getDieRoll()) {
            return true;
        } else {
            return false;
        }
    }

    public int getDieRoll() {
        int dice = (int) (Math.random()*6+1);
        return dice;
    }
}
