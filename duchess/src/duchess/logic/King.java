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
    public King(int pieceID, int file, int rank, boolean color, Game myGame) {
        super(pieceID, file, rank, color, myGame);
    }
    public King(King p) {
        super(p.pieceID, p.file, p.rank, p.color, p.myGame);
    }
    public Square[] possibleMoves() {
        if(isItMyTurn() == false) return new Square[0];
        ArrayList<Square> diagonal = this.findDiagonalSquares(1);
        ArrayList<Square> orthogonal = this.findOrthogonalSquares(1);
        ArrayList<Square> moves = new ArrayList<Square>(diagonal);
        moves.addAll(orthogonal);
        // king can't move to an threatened square
        ArrayList<Square> moveCopy = new ArrayList<Square>(moves);
        for (Square move : moveCopy) {
            myGame.setResolveCheckGuard(false);
            if (myGame.whoCanMoveHere(move, true, true).length > 0) {
                moves.remove(move);
            }
            myGame.setResolveCheckGuard(true);
        }
        Square[] result = moves.toArray(new Square[moves.size()]);
        return result;
    }
    
}
