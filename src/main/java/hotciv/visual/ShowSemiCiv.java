package hotciv.visual;
import hotciv.framework.*;
import hotciv.standard.*;
import minidraw.framework.*;
import minidraw.standard.*;

public class ShowSemiCiv {

    public static void main(String[] args) {
        Game game = new GameImpl(new SemiCivFactory(new DieRollStub()));

        DrawingEditor editor =
                new MiniDrawApplication("Click anywhere to see Drawing updates",
                        new HotCivFactory4(game));
        editor.open();
        editor.setTool(new CompositionTool(editor, game));

        editor.showStatus("Click anywhere to state changes reflected on the GUI");
    }
}