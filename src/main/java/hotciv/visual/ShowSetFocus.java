package hotciv.visual;

import minidraw.standard.*;
import minidraw.framework.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import hotciv.framework.*;
import hotciv.view.*;
import hotciv.stub.*;

/** Template code for exercise FRS 36.40.

 This source code is from the book
 "Flexible, Reliable Software:
 Using Patterns and Agile Development"
 published 2010 by CRC Press.
 Author:
 Henrik B Christensen
 Computer Science Department
 Aarhus University

 This source code is provided WITHOUT ANY WARRANTY either
 expressed or implied. You may study, use, modify, and
 distribute it for non-commercial purposes. For any
 commercial use, see http://www.baerbak.com/
 */
public class ShowSetFocus {

  public static void main(String[] args) {
    Game game = new StubGame2();

    DrawingEditor editor =
            new MiniDrawApplication( "Click any tile to set focus",
                    new HotCivFactory4(game) );
    editor.open();
    editor.showStatus("Click a tile to see Game's setFocus method being called.");

    // TODO: Replace the setting of the tool with your SetFocusTool implementation.
    editor.setTool( new SetFocusTool(editor, game) );
  }
}

class SetFocusTool extends NullTool {
  DrawingEditor drawingEditor;
  Game game;

  public SetFocusTool(DrawingEditor drawingEditor, Game game) {
    this.drawingEditor = drawingEditor;
    this.game = game;
  }

  @Override
  public void mouseDown(MouseEvent e, int x, int y) {
    Position position = GfxConstants.getPositionFromXY(x, y);
    if (game.getUnitAt(position) != null || game.getCityAt(position) != null) {
      if (position.getRow() < GameConstants.WORLDSIZE && position.getColumn() < GameConstants.WORLDSIZE) {
        game.setTileFocus(GfxConstants.getPositionFromXY(x, y));
        drawingEditor.showStatus("Set Tile Focus at " + position);
      }
    }
  }
}