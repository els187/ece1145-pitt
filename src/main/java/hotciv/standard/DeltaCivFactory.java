package hotciv.standard;
import hotciv.framework.*;

public class DeltaCivFactory implements GameFactory{

    @Override
    public AgingStrategy agingStrategy() {
        return new AlphaAgingStrategy();
    }

    @Override
    public WinningStrategy winningStrategy() {
        return new AlphaWinningStrategy();
    }

    @Override
    public MapStrategy mapStrategy() {
        return new DeltaMapStrategy();
    }

    @Override
    public ActionStrategy actionStrategy() {
        return new AlphaActionStrategy();
    }

    @Override
    public BattleStrategy battleStrategy() {
        return new AlphaBattleStrategy();
    }
}

