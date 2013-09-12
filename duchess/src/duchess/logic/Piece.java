/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duchess.logic;

/**
 *
 * @author thitkone
 */
public abstract class Piece {
    private boolean color;
    private int file;
    private int rank;

    public Piece(int file, int rank, boolean color) {
        this.file = file;
        this.rank = rank;
        this.color = color;
    }
    
    public boolean getColor() { return this.color; }
    public int[] getPos() { return new int[]{ this.file, this.rank }; }
    
    public void changePos(int file, int rank) {
        this.file = file;
        this.rank = rank;    
    }
    // think of better format for representing moves
    public int[][] possibleMoves() {
        //history = board.getHistory();
        //arr = new String[]
        return new int[1][1];
    }
}
