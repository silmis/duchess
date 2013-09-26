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
public class King extends Piece {
    public King(int file, int rank, boolean color, Game myGame) {
        super(file, rank, color, myGame);
    }
    public Square[] possibleMoves() {
        if(isItMyTurn() == false) return new Square[0];
        ArrayList<Square> diagonal = this.findDiagonalSquares(1);
        ArrayList<Square> orthogonal = this.findOrthogonalSquares(1);
        ArrayList<Square> moves = new ArrayList<Square>(diagonal);
        moves.addAll(orthogonal);
        // king can't move to an threatened square TO-DO
        /*for (Square move : moves) {
            if (myGame.whoCanMoveHere(move).length != 0) {
                moves.remove(move);
            }
        }*/
        Square[] result = moves.toArray(new Square[moves.size()]);
        return result;
    }
    
}
