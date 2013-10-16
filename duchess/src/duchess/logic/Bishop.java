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
    public Bishop(int pieceID, int file, int rank, boolean color, Game myGame) {
        super(pieceID, file, rank, color, myGame);
    }
    public Bishop(Bishop p) {
        super(p.pieceID, p.file, p.rank, p.color, p.myGame);
    }
    public Square[] possibleMoves() {
        if(isItMyTurn() == false) return new Square[0];
        ArrayList<Square> moves = this.findDiagonalSquares(8);
        /*if (myGame.nextMoveCheckGuard()) {
            myGame.setNextMoveCheckGuard(false);
            moves = this.willResultInCheck(moves);
            myGame.setNextMoveCheckGuard(true);
        }*/
        if (myGame.isCheck() && myGame.resolveCheckGuard() &&
                (this.color == myGame.isWhitesTurn())) {
            myGame.setResolveCheckGuard(false);
            moves = this.squaresToResolveCheck(moves);
            myGame.setResolveCheckGuard(true);
        }
        Square[] result = moves.toArray(new Square[moves.size()]);
        return result;
    }
    
}
