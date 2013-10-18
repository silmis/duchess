/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duchess.logic;

import java.util.ArrayList;

/**
 * Class for Rooks.
 * @author thitkone
 */
public class Rook extends Piece {
    public Rook(int pieceID, int file, int rank, boolean color, Game myGame) {
        super(pieceID, file, rank, color, myGame);
    }
    public Rook(Rook p) {
        super(p.pieceID, p.file, p.rank, p.color, p.myGame);
    }
    public Square[] possibleMoves() {
        if(isItMyTurn() == false) return new Square[0];
        ArrayList<Square> moves = this.findOrthogonalSquares(8);
        /*if (myGame.nextMoveCheckGuard() && !myGame.isCheck()) {
            myGame.setNextMoveCheckGuard(false);
            moves = this.willNotResultInCheck(moves);
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
