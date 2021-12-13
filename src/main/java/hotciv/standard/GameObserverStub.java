package hotciv.standard;
import hotciv.framework.*;
import java.util.*;

public class GameObserverStub implements GameObserver{
    private List<String> worldChangedAtOutput, turnEndsOutput, tileFocusChangedAtOutput;

    GameObserverStub() {
        worldChangedAtOutput = new ArrayList<>();
        turnEndsOutput = new ArrayList<>();
        tileFocusChangedAtOutput = new ArrayList<>();
    }

    @Override
    public void worldChangedAt(Position pos) {
        worldChangedAtOutput.add("World changed at: " + pos + ".");
    }

    @Override
    public void turnEnds(Player nextPlayer, int age) {
        turnEndsOutput.add("Next player in turn is: " + nextPlayer + " and the age is: " + age + ".");
    }

    @Override
    public void tileFocusChangedAt(Position position) {
        tileFocusChangedAtOutput.add("Tile focus changed at: " + position + ".");
    }

    public String getWorldChangedAtOutput(int index) {
        return worldChangedAtOutput.get(index);
    }

    public String getTurnEndsOutput(int index) {
        return turnEndsOutput.get(index);
    }

    public String getTileFocusChangedAtOutput(int index){
        return tileFocusChangedAtOutput.get(index);
    }
}
