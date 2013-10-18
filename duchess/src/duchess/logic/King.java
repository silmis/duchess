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
        // castling
        boolean kingMoved = myGame.log.hasMoved(this);
        if ((kingMoved == false) && (myGame.isCheck() == false)) {
            ArrayList<Piece> rooks = new ArrayList<Piece>();
            for (Piece p : myGame.getPieces()) {
                if ((p instanceof Rook) && (p.getColor() == this.color))
                    rooks.add(p);
            }
            rooks:
            for (Piece rook : rooks) {
                if (myGame.log.hasMoved(rook) == false) {
                    int distance = Math.abs(rook.getFile()-this.getFile());
                    int direction = (rook.getFile()>this.getFile()) ? 1 : -1;
                    for (int i=1; i<distance; i++) {
                        Square sq = new Square(this.file + i * direction, 
                                this.rank);
                        Piece occupier = myGame.whoIsAt(sq);
                        if (occupier != null) continue rooks;
                        Piece[] threats = myGame.whoCanMoveHere(sq, true, true);
                        if (threats.length > 0) continue rooks;
                    }
                    if (direction == 1) {
                        moves.add(new Square(this.file+2, this.rank));
                    } else {
                        moves.add(new Square(this.file-2, this.rank));
                    }
                }
            }
        }
        Square[] result = moves.toArray(new Square[moves.size()]);
        return result;
    }
    
}
