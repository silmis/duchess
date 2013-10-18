/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duchess.logic;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class for Kings.
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
        // moves when pawn is nearby
        int direction = this.color ? 1 : -1;
        ArrayList<Square> pawnBox = new ArrayList<Square>(8);
        for (int i=1; i<3; i++) {
            pawnBox.add(new Square(this.file-2, this.rank+i*direction));
            pawnBox.add(new Square(this.file-1, this.rank+i*direction));
            pawnBox.add(new Square(this.file, this.rank+i*direction));
            pawnBox.add(new Square(this.file+1, this.rank+i*direction));
            pawnBox.add(new Square(this.file+2, this.rank+i*direction));
        }
        for (Square sq : pawnBox) {
            if (sq.isValid() == false) continue;
            Piece pieceInSq = myGame.whoIsAt(sq);
            if ((pieceInSq instanceof Pawn) && 
                    (pieceInSq.getColor() != this.color)) {
                ArrayList<Square> toRemove = new ArrayList<Square>();
                toRemove.add(new Square(sq.fl()-1, sq.rk()-1*direction));
                toRemove.add(new Square(sq.fl()+1, sq.rk()-1*direction));
                Square legal = new Square(sq.fl(), sq.rk()-1*direction);
                for (Square r : toRemove) {
                    Iterator<Square> iter = moves.iterator();
                    while(iter.hasNext()) {
                        Square m = iter.next();
                        if (r.equals(m)) iter.remove();
                    }
                }
                if (myGame.whoIsAt(legal) == null) 
                    moves.add(legal);
            }
        }
        //System.out.println();
        //System.out.println(moves);
        /*Iterator<Square> iter = moves.iterator();
        while(iter.hasNext()) {
            Square m = iter.next();
            Piece[] pcs = myGame.canXsMoveHere("King", m, this, true);
            if (pcs.length > 0) iter.remove();
        }*/
        Square[] result = moves.toArray(new Square[moves.size()]);
        return result;
    }
    
}
