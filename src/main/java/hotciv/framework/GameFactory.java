package hotciv.framework;

public interface GameFactory {
    AgingStrategy agingStrategy();
    WinningStrategy winningStrategy();
    MapStrategy mapStrategy();
    ActionStrategy actionStrategy();
    BattleStrategy battleStrategy();
}
