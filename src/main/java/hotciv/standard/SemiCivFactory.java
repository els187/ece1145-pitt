package hotciv.standard;
import hotciv.framework.*;

public class SemiCivFactory implements GameFactory{
    private DieRollStrategy dieRollStrategy;

    public SemiCivFactory(DieRollStrategy rollStrategy) {
        this.dieRollStrategy = rollStrategy;
    }

    @Override
    public AgingStrategy agingStrategy() {
        return new BetaAgingStrategy();
    }

    @Override
    public WinningStrategy winningStrategy() {
        return new EpsilonWinningStrategy();
    }

    @Override
    public MapStrategy mapStrategy() {
        return new DeltaMapStrategy();
    }

    @Override
    public ActionStrategy actionStrategy() {
        return new GammaActionStrategy();
    }

    @Override
    public BattleStrategy battleStrategy() {
        return new EpsilonBattleStrategy(dieRollStrategy);
    }

    @Override
    public PopulationStrategy populationStrategy() {
        return new EtaPopulationStrategy();
    }

    @Override
    public WorkForceFocusStrategy workForceFocusStrategy() {
        return new EtaWorkForceFocusStrategy();
    }
}
