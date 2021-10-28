package hotciv.standard;
import hotciv.framework.*;

public class EpsilonBattleStrategy implements BattleStrategy {
    private DieRollStrategy attackingProbability;
    private DieRollStrategy defendingProbability;

    public EpsilonBattleStrategy(DieRollStrategy attackingProbability, DieRollStrategy defendingProbability){
        this.attackingProbability = attackingProbability;
        this.defendingProbability = defendingProbability;
    }


    public int calculateAttackingStrength(Game game, Position from){
        Unit attackingUnit = game.getUnitAt(from);
        int attackingStrength = attackingUnit.getAttackingStrength();
        attackingStrength += Utility2.getFriendlySupport(game, from, attackingUnit.getOwner());
        attackingStrength *= Utility2.getTerrainFactor(game, from);
        attackingStrength *= attackingProbability.getDieRoll();
        return attackingStrength;
    }

    public int calculateDefendingStrength(Game game, Position to){
        Unit defendingUnit = game.getUnitAt(to);
        int defendingStrength = defendingUnit.getDefensiveStrength();
        defendingStrength += Utility2.getFriendlySupport(game, to, defendingUnit.getOwner());
        defendingStrength *= Utility2.getTerrainFactor(game, to);
        defendingStrength *= defendingProbability.getDieRoll();
        return defendingStrength;
    }

    public boolean getBattleOutcome(Game game, Position from, Position to){
        //O calculation from the book
        if (calculateAttackingStrength(game,from) > calculateDefendingStrength(game, to)) {
            return true;
        } else {
            return false;
        }
    }

}
