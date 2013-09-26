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
public class Knight extends Piece {
    public Knight(int file, int rank, boolean color, Game myGame) {
        super(file, rank, color, myGame);
    }
    public Square[] possibleMoves() {
        ArrayList<Square> initialMoves = new ArrayList();
        ArrayList<Square> legalMoves = new ArrayList();
        
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
                legalMoves.add(s);
            }
        }
        
        if (myGame.isCheck() == true) {
            legalMoves = this.squaresToResolveCheck(legalMoves);
        }
        Square[] result = legalMoves.toArray(new Square[legalMoves.size()]);
        return result;
    }
    
}
