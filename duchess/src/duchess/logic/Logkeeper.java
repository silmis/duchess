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
    private ArrayList<Move> log;
    public Logkeeper() {
        this.log = new ArrayList<Move>();
    }
    public Logkeeper(Logkeeper old) {
        this.log = (ArrayList<Move>) old.log.clone();
    }
    public Move lastMove() { return this.log.get( this.log.size()-1 ); }
    public void add(Piece p, Square start, Square target) {
        Move m = new Move(p, start, target);
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
        int pid = p.pieceID;
        boolean moved = false;
        for (Move m : log) {
            if (m.pieceID == pid) {
                moved = true;
            }
        }
        return moved;
    }
    public void print() {
        for (Move m : log) {
            /*String className = m.piece.getClass().toString();
            String[] splitted = className.split("\\.");
            className = splitted[splitted.length-1];
            String color = m.piece.getColor() ? "White" : "Black";*/
            System.out.println(m.color + " " + m.pieceID + " " + m.getStart() + 
                    " -> " + m.getTarget());
        }
    }
   
    public class Move {
        private int pieceID;
        private boolean color;
        private Square start;
        private Square target;
        private Move(Piece p, Square start, Square target) {
            this.pieceID = p.pieceID;
            this.color = p.getColor();
            this.start = start;
            this.target = target;
        }
        public Square getStart() { return this.start; }
        public Square getTarget() { return this.target; }
    }
}
