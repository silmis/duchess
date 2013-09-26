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
public class Bishop extends Piece {
    public Bishop(int file, int rank, boolean color, Game myGame) {
        super(file, rank, color, myGame);
    }
    public Square[] possibleMoves() {
        if(checkIfMovingPossible() == false) return new Square[0];
        ArrayList<Square> moves = this.findDiagonalSquares(8);
        if (myGame.isCheck() == true) {
            moves = this.squaresToResolveCheck(moves);
        }
        Square[] result = moves.toArray(new Square[moves.size()]);
        return result;
    }
    
}
