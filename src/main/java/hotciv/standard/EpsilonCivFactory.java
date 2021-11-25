package hotciv.standard;
import hotciv.framework.*;

public class EpsilonCivFactory implements GameFactory{
    private DieRollStrategy dieRollStrategy;

    EpsilonCivFactory(DieRollStrategy rollStrategy) {
        this.dieRollStrategy = rollStrategy;
    }

    @Override
    public AgingStrategy agingStrategy() {
        return new AlphaAgingStrategy();
    }

    @Override
    public WinningStrategy winningStrategy() {
        return new EpsilonWinningStrategy();
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
        return new EpsilonBattleStrategy(dieRollStrategy);
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
