package hotciv.standard;
import hotciv.framework.*;

public class ZetaCivFactory implements GameFactory{

    @Override
    public AgingStrategy agingStrategy() {
        return new AlphaAgingStrategy();
    }

    @Override
    public WinningStrategy winningStrategy() {
        //Change this when implementation of ZetaCiv is finished
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
}
