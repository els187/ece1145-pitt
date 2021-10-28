package hotciv.framework;

public interface BattleStrategy {
    public int calculateAttackingStrength(Game game, Position from);
    public int calculateDefendingStrength(Game game, Position to);
    public boolean getBattleOutcome(Game game, Position from, Position to);
}
