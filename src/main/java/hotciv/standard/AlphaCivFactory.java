package hotciv.standard;
import hotciv.framework.*;

public class AlphaCivFactory implements GameFactory{

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
        return new AlphaActionStrategy();
    }

    @Override
    public BattleStrategy battleStrategy() {
        return new AlphaBattleStrategy();
    }

    @Override
    public PopulationStrategy populationStrategy() {
        return new AlphaPopulationStrategy();
    }

    @Override
    public WorkForceFocusStrategy workForceFocusStrategy() {
        return new AlphaWorkForceFocusStrategy();
    }
}
