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
public class Pawn extends Piece {
    public Pawn(int file, int rank, boolean color, Game myGame) {
        super(file, rank, color, myGame);
    }
    public int[][] possibleMoves() {
        int[][] initialMoves;
        ArrayList<int[]> legalMoves = new ArrayList();
        
        if(checkIfMovingPossible() == false) {
            return new int[][] {};
        }
        
        // for white
        if (color == true) {
            initialMoves = new int[][] { 
                new int[] { this.file, this.rank+1 },
                new int[] { this.file, this.rank+2 },
                new int[] { this.file+1, this.rank+1 },
                new int[] { this.file-1, this.rank+1 }            
            };
        // for black
        } else {
            initialMoves = new int[][] { 
                new int[] { this.file, this.rank-1 },
                new int[] { this.file, this.rank-2 },
                new int[] { this.file+1, this.rank-1 },
                new int[] { this.file-1, this.rank-1 }
            };
        }
        // can't move to square if ...
        for (int[] square : initialMoves) {
            // move outside the board
            if (myGame.isValidSquare(square) == false) {
                continue;
            // move is the same as piece position
            } else if( (square[0] == this.file) &&
                       (square[1] == this.rank)) {
                continue;
            // the square is occupied and ahead
            } else if((myGame.isOccupied(square) == true) && 
                      (square[0] == this.file)) {
                continue;
            // diagonal squares are not occupied
            } else if((myGame.isOccupied(square) == false) &&
                      (square[0] != this.file)) {
                continue;
            // square is two paces ahead unless at rank 2 for white
            } else if((this.color == true) && 
                      (square[1] == this.rank+2) && 
                      (this.rank != 2)) {
                continue;
            // same but rank 7 for black
            } else if((this.color == false) && 
                      (square[1] == this.rank-2) && 
                      (this.rank != 7)) {
                continue;
            } else {
                // move is legal
                legalMoves.add(square);
            }   
        }
        int[][] result = legalMoves.toArray(new int[legalMoves.size()][2]);
        return result;
    }
    
}
