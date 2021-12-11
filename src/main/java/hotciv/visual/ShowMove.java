package hotciv.visual;

import minidraw.standard.*;
import minidraw.framework.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import hotciv.framework.*;
import hotciv.view.*;
import hotciv.stub.*;

/** Template code for exercise FRS 36.39.

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
public class ShowMove {

  public static void main(String[] args) {
    Game game = new StubGame2();

    DrawingEditor editor =
            new MiniDrawApplication( "Move any unit using the mouse",
                    new HotCivFactory4(game) );
    editor.open();
    editor.showStatus("Move units to see Game's moveUnit method being called.");

    // TODO: Replace the setting of the tool with your UnitMoveTool implementation.
    editor.setTool( new UnitMoveTool(editor, game) );
  }
}

class UnitMoveTool extends NullTool {
  DrawingEditor drawingEditor;
  Game game;
  private Position unitFrom;
  private Position unitTo;

  public UnitMoveTool(DrawingEditor drawingEditor, Game game) {
    this.drawingEditor = drawingEditor;
    this.game = game;
  }

  @Override
  public void mouseDown(MouseEvent e, int x, int y) {
    unitFrom = GfxConstants.getPositionFromXY(x, y);
    if(game.getUnitAt(unitFrom) != null && game.getUnitAt(unitFrom).getOwner().equals(game.getPlayerInTurn())) {
      super.mouseDown(e, x, y);
    }
  }

  @Override
  public void mouseDrag(MouseEvent e, int x, int y) {
    super.mouseDrag(e, x, y);
  }

  @Override
  public void mouseUp(MouseEvent e, int x, int y) {
    unitTo = GfxConstants.getPositionFromXY(x,y);
    if(game.getUnitAt(unitFrom) != null) {
      super.mouseUp(e, x, y);
      if (game.getUnitAt(unitFrom).getOwner().equals(game.getPlayerInTurn())) {
        if (unitTo.getRow() < GameConstants.WORLDSIZE && unitTo.getColumn() < GameConstants.WORLDSIZE) {
          game.moveUnit(unitFrom, unitTo);
          drawingEditor.showStatus("Unit moved from " + unitFrom + " to " + unitTo);
        } else {
          game.moveUnit(unitFrom, unitFrom);
          drawingEditor.showStatus("Unit could not move, unit stays at " + unitFrom);
        }
      }
    }
  }
}