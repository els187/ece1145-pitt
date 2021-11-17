package hotciv.standard;

import hotciv.framework.*;
import java.util.*;

/** Skeleton implementation of HotCiv.

 This source code is from the book
 "Flexible, Reliable Software:
 Using Patterns and Agile Development"
 published 2010 by CRC Press.
 Author:
 Henrik B Christensen
 Department of Computer Science
 Aarhus University

 Please visit http://www.baerbak.com/ for further information.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 */

public class TranscriptDecorator implements Game {
    private Game game;

    public TranscriptDecorator(Game game) {
        this.game = game;
    }

    @Override
    public Tile getTileAt(Position p) {
        return game.getTileAt(p);
    }

    @Override
    public Unit getUnitAt(Position p) {
        return game.getUnitAt(p);
    }

    @Override
    public City getCityAt(Position p) {
        return game.getCityAt(p);
    }

    @Override
    public Player getPlayerInTurn() {
        return game.getPlayerInTurn();
    }

    @Override
    public Player getWinner() {
        return game.getWinner();
    }

    @Override
    public int getAge() {
        return game.getAge();
    }

    @Override
    public boolean moveUnit(Position from, Position to) {
        if(game.moveUnit(from, to) == true){
            System.out.println(game.getPlayerInTurn() + " moves from " + from + " to " +  to + ".");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void endOfTurn() {
        game.endOfTurn();
        System.out.println(game.getPlayerInTurn() + " ends turn.");
    }

    @Override
    public void changeWorkForceFocusInCityAt(Position p, String balance) {
        City city = game.getCityAt(p);
        game.changeWorkForceFocusInCityAt(p, balance);
        System.out.println(city.getOwner() + " changes work force focus in city at (" + p.getRow() + "," + p.getColumn() + ") to " + balance + ".");
    }

    @Override
    public void changeProductionInCityAt(Position p, String unitType) {
        game.changeProductionInCityAt(p, unitType);
        System.out.println(game.getPlayerInTurn() + " changes production in city at (" + p.getRow() + "," + p.getColumn() + ") to " + unitType + ".");
    }

    @Override
    public void performUnitActionAt(Position p) {
        game.performUnitActionAt(p);
        System.out.println(game.getPlayerInTurn() + " performs unit action on " + game.getUnitAt(p).getTypeString() + " at position (" + p.getRow() + "," + p.getColumn() + ").");
    }
}