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

    public Square[] possibleMoves() { return null; }
    
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
    protected ArrayList<Square> findConsecutiveSquares(int[][] modifiers, 
                                                       int length) {
        // Finds all legal diagonal or orthogonal moves (Bishop, Rook, Queen & 
        // King). Modifiers is an array of four (x,y) pairs of "weights" 
        // which enable the same loop to be run for all four directions 
        // on the board. By choosing the modifiers accordingly, we get 
        // either diagonal or orthogonal consecutive squares. Length 
        // determines how many squares are explored at any direction (1 for 
        // king, 8 to others). If edge of board is encountered, loop 
        // breaks. If an occupied square is encountered, move is only legal if
        // the piece occupying it is of opposite color.
        // Example of modifiers: see findDiagonalMoves() and 
        // findOrthogonalMoves()
        ArrayList<Square> moves = new ArrayList();
        for (int c=0; c<4; c++) {
            for (int i=1; i<=length; i++) {
                int f = this.file + modifiers[c][0]*i;
                int r = this.rank + modifiers[c][1]*i;
                Square sq = new Square(f, r);
                if (sq.isValid() == false) break;
                Piece pieceInTargetSq = myGame.whoIsAt(sq);
                if ((pieceInTargetSq != null) && 
                    (pieceInTargetSq.getColor() != this.color)) {
                    moves.add(sq);
                    break;
                } else if (pieceInTargetSq != null) {
                    break;
                }
                moves.add(sq);
            }
        }
        return moves;
    }
    protected ArrayList<Square> findDiagonalSquares(int length) {
        int[][] modifiers = {{1,1},{1,-1},{-1,1},{-1,-1}};
        return this.findConsecutiveSquares(modifiers, length);
    }
    protected ArrayList<Square> findOrthogonalSquares(int length) {
        int[][] modifiers = {{0,1},{1,0},{0,-1},{-1,0}};
        return this.findConsecutiveSquares(modifiers, length);
    }
    public String toString() {
        Class myClass = this.getClass();
        String color = this.color ? "white" : "black";
        String s = "I am a " + color + " " + myClass  + 
                   " at (" + this.file + "," + this.rank + ")";
        return s;
    }
}
