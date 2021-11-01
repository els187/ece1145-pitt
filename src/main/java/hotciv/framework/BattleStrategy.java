package hotciv.framework;

public interface BattleStrategy {
    int calculateAttackingStrength(Game game, Position from);
    int calculateDefendingStrength(Game game, Position to);
    boolean getBattleOutcome(Game game, Position from, Position to);
}
