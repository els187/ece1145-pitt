package hotciv.framework;
import hotciv.standard.*;

public interface ActionStrategy {
    public void unitAction(Position pos, GameImpl game);
}
