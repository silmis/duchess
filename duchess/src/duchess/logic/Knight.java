/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duchess.logic;

import java.util.ArrayList;

/**
 * Class for Knights.
 * @author thitkone
 */
public class Knight extends Piece {
    public Knight(int pieceID, int file, int rank, boolean color, Game myGame) {
        super(pieceID, file, rank, color, myGame);
    }
    public Knight(Knight p) {
        super(p.pieceID, p.file, p.rank, p.color, p.myGame);
    }
    public Square[] possibleMoves() {
        ArrayList<Square> initialMoves = new ArrayList();
        ArrayList<Square> moves = new ArrayList();
        
        if(isItMyTurn() == false) return new Square[0];
        
        initialMoves.add(new Square(this.file+2, this.rank+1));
        initialMoves.add(new Square(this.file+1, this.rank+2));
        initialMoves.add(new Square(this.file-2, this.rank+1));
        initialMoves.add(new Square(this.file-1, this.rank+2));

        initialMoves.add(new Square(this.file-2, this.rank-1));
        initialMoves.add(new Square(this.file-1, this.rank-2));
        initialMoves.add(new Square(this.file+2, this.rank-1));
        initialMoves.add(new Square(this.file+1, this.rank-2));
        
        for(Square s : initialMoves) {
            Piece occupier = myGame.whoIsAt(s);
            if (s.isValid() == false) {
                continue;
            } else if ((occupier != null) && 
                       (occupier.getColor() == this.color)) {
                continue;
            } else {
                moves.add(s);
            }
        }
        
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
