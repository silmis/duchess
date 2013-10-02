/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duchess.logic;

import java.util.ArrayList;

/**
 *
 * @author thitkone
 */
public class Logkeeper {
    ArrayList<Move> log = new ArrayList(); 
    
    public void add(Piece piece, Square start, Square target) {
        Move m = new Move(piece, start, target);
        log.add(m);
    }
    public boolean remove(int index) {
        try {
            log.remove(index);
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }
    public boolean hasMoved(Piece p) {
        boolean moved = false;
        for (Move m : log) {
            if (m.piece == p) {
                moved = true;
            }
        }
        return moved;
    }
    public void print() {
        for (Move m : log) {
            String className = m.piece.getClass().toString();
            String[] splitted = className.split("\\.");
            className = splitted[splitted.length-1];
            String color = m.piece.getColor() ? "White" : "Black";
            System.out.println(color + " " + className + " " + m.getStart() + 
                    " -> " + m.getTarget());
        }
    }
   
    private class Move {
        private Piece piece;
        private Square start;
        private Square target;
        private Move(Piece piece, Square start, Square target) {
            this.piece = piece;
            this.start = start;
            this.target = target;
        }
        public Square getStart() { return this.start; }
        public Square getTarget() { return this.target; }
    }
}
