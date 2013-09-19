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
        Square[] initialMoves;
        ArrayList<Square> legalMoves = new ArrayList();
        
        if(checkIfMovingPossible() == false) {
            return new Square[] {};
        }
        
        initialMoves = new Square[] {
            new Square(this.file+2, this.rank+1),
            new Square(this.file+1, this.rank+2),
            new Square(this.file-2, this.rank+1),
            new Square(this.file-1, this.rank+2),
            
            new Square(this.file-2, this.rank-1),
            new Square(this.file-1, this.rank-2),
            new Square(this.file+2, this.rank-1),
            new Square(this.file+1, this.rank-2)
        };
        
        for(Square s : initialMoves) {
            if (myGame.isValidSquare(s) == false) {
                continue;
            } else {
                legalMoves.add(s);
            }
        }
        
        Square[] result = legalMoves.toArray(new Square[legalMoves.size()]);
        return result;
    }
    
}
