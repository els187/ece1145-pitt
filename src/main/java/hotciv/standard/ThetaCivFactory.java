package hotciv.standard;
import hotciv.framework.*;

public class ThetaCivFactory implements GameFactory{

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
        return new AlphaMapStrategy();
    }

    @Override
    public ActionStrategy actionStrategy() {
        return new ThetaActionStrategy();
    }

    @Override
    public BattleStrategy battleStrategy() {
        return new AlphaBattleStrategy();
    }
}

