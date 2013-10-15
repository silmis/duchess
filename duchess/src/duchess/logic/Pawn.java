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
    
    private Square enPassant = null;
    
    public Pawn(int pieceID, int file, int rank, boolean color, Game myGame) {
        super(pieceID, file, rank, color, myGame);
    }
    public Pawn(Pawn p) {
        super(p.pieceID, p.file, p.rank, p.color, p.myGame);
    }
    public Square getEnPassant() { return this.enPassant; }
    public void setEnPassant(Square s) { this.enPassant = s; }
    
    public Square[] possibleMoves() {
        Square[] initialMoves;
        ArrayList<Square> legalMoves = new ArrayList();
        
        if(isItMyTurn() == false) {
            return new Square[] {};
        }
        
        // for white
        if (color == true) {
            initialMoves = new Square[] { 
                new Square(this.file, this.rank+1),
                new Square(this.file, this.rank+2),
                new Square(this.file+1, this.rank+1),
                new Square(this.file-1, this.rank+1)            
            };
        // for black
        } else {
            initialMoves = new Square[] { 
                new Square(this.file, this.rank-1),
                new Square(this.file, this.rank-2),
                new Square(this.file+1, this.rank-1),
                new Square(this.file-1, this.rank-1)
            };
        }
        // can't move to square if ...
        for (Square s : initialMoves) {
            // move outside the board
            if (s.isValid() == false) {
                continue;
            // the square is occupied and ahead
            } else if((myGame.whoIsAt(s) != null) && 
                      (s.fl() == this.file)) {
                continue;
            // diagonal squares are not occupied
            } else if((myGame.whoIsAt(s) == null) &&
                      (s.fl() != this.file)) {
                continue;
            // square is two paces ahead unless at rank 2 for white
            } else if((this.color == true) && 
                      (s.rk() == this.rank+2) && 
                      (this.rank != 2)) {
                continue;
            // same but rank 7 for black
            } else if((this.color == false) && 
                      (s.rk() == this.rank-2) && 
                      (this.rank != 7)) {
                continue;
            } else {
                // move is legal
                legalMoves.add(s);
            }   
        }
        if (this.enPassant != null) legalMoves.add(enPassant);
         
        if ((myGame.isCheck() == true) && (myGame.resolveCheck() == true) &&
                (this.color == myGame.isWhitesTurn())) {
            myGame.setResolveCheck(false);
            legalMoves = this.squaresToResolveCheck(legalMoves);
            myGame.setResolveCheck(true);
        }
        Square[] result = legalMoves.toArray(new Square[legalMoves.size()]);
        return result;
    }
}
