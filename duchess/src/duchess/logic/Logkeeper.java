/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duchess.logic;

import java.util.ArrayList;

/**
 * Keeps a log of the moves made during the game.
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
    public ArrayList<Move> allMoves() { return this.log; }
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
    /**
     * Returns whether a piece has moved or not.
     * @param p Piece
     * @return true/false
     */
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
    /**
     * Represents a single move.
     */
    public class Move {
        private int pieceID;
        private boolean color;
        private Square start;
        private Square target;
        private String cls;
        private Move(Piece p, Square start, Square target) {
            this.pieceID = p.pieceID;
            this.color = p.getColor();
            this.start = start;
            this.target = target;
            String[] stra = p.getClass().getName().split("\\.");
            this.cls = stra[stra.length-1];
        }
        public Square getStart() { return this.start; }
        public Square getTarget() { return this.target; }
        public boolean getColor() { return this.color; }
        public String toString() {
            String colorstr = color ? "white" : "black";
            return colorstr + " " + cls + " " + start + "->" + target;
        }
    }
}
