package hotciv.standard;

import hotciv.framework.*;

public class AlphaBattleStrategy implements BattleStrategy {
    public int calculateAttackingStrength(Game game, Position from){
        return 0;
    }
    public int calculateDefendingStrength(Game game, Position to){
        return 0;
    }
    public boolean getBattleOutcome(Game game, Position from, Position to){
        return true;
    }
}
