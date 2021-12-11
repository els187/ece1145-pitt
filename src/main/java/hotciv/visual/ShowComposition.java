package hotciv.visual;

import minidraw.standard.*;
import minidraw.framework.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import hotciv.framework.*;
import hotciv.view.*;
import hotciv.stub.*;

/** Template code for exercise FRS 36.44.

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
public class ShowComposition {

  public static void main(String[] args) {
    Game game = new StubGame2();

    DrawingEditor editor =
            new MiniDrawApplication( "Click and/or drag any item to see all game actions",
                    new HotCivFactory4(game) );
    editor.open();
    editor.showStatus("Click and drag any item to see Game's proper response.");

    // TODO: Replace the setting of the tool with your CompositionTool implementation.
    editor.setTool( new CompositionTool(editor, game) );
  }
}

class CompositionTool extends NullTool {
  DrawingEditor drawingEditor;
  Game game;
  private Tool toolState;
  Position unitFrom;

  public CompositionTool(DrawingEditor drawingEditor, Game game) {
    this.drawingEditor = drawingEditor;
    this.game = game;
    this.toolState = new NullTool();
  }

  @Override
  public void mouseDown(MouseEvent e, int x, int y) {
    unitFrom = GfxConstants.getPositionFromXY(x, y);
    if(game.getUnitAt(unitFrom) != null){
      toolState = new SetFocusTool(drawingEditor, game);
      if(e.isShiftDown() && game.getUnitAt(unitFrom).getOwner().equals(game.getPlayerInTurn())) {
        toolState = new ActionTool(drawingEditor, game);
      }
    }
    toolState.mouseDown(e, x, y);
  }

  @Override
  public void mouseDrag(MouseEvent e, int x, int y) {
    if(!(toolState instanceof UnitMoveTool)) {
      toolState = new UnitMoveTool(drawingEditor, game);
      toolState.mouseDown(e, x, y);
    }
    toolState.mouseDrag(e,x,y);
  }

  @Override
  public void mouseMove(MouseEvent e, int x, int y) {
    if(GfxConstants.getPositionFromXY(x,y).equals(GfxConstants.getPositionFromXY(GfxConstants.TURN_SHIELD_X, GfxConstants.TURN_SHIELD_Y))) {
      toolState = new EndOfTurnTool(drawingEditor, game);
    }

    if(game.getCityAt(GfxConstants.getPositionFromXY(x,y)) != null){
      toolState = new SetFocusTool(drawingEditor, game);
    }
  }

  @Override
  public void mouseUp(MouseEvent e, int x, int y) {
    toolState.mouseUp(e,x,y);
  }
}