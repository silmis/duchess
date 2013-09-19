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
    protected boolean color;
    protected int file;
    protected int rank;
    protected Game myGame;

    public Piece(int file, int rank, boolean color, Game myGame) {
        this.file = file;
        this.rank = rank;
        this.color = color;
        this.myGame = myGame;
    }
    
    public boolean getColor() { return this.color; }
    public int getFile() { return this.file; }
    public int getRank() { return this.rank; }
    
    public void changePos(int file, int rank) {
        this.file = file;
        this.rank = rank;    
    }
    // think of better format for representing moves?
    public Square[] possibleMoves() {
        return new Square[1];
    }
    protected boolean checkIfMovingPossible() {
        // check if my turn
        // check if movement possible (isCheck)
        // ^ checking needs more thought, this is wrong
        if( (myGame.isWhitesTurn() == this.color) &&
            (myGame.isCheck() == false)) {
            return true;
        }
        return false;
    }
    public String toString() {
        String s = "piece { white: " + this.color + " at: " + 
                    this.file + "," + this.rank + " }";
        return s;
    }
}
