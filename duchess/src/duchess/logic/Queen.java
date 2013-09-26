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
public class Queen extends Piece {
    public Queen(int file, int rank, boolean color, Game myGame) {
        super(file, rank, color, myGame);
    }
    public Square[] possibleMoves() {
        if(checkIfMovingPossible() == false) return new Square[0];
        ArrayList<Square> diagonal = this.findDiagonalSquares(8);
        ArrayList<Square> orthogonal = this.findOrthogonalSquares(8);
        ArrayList<Square> moves = new ArrayList<Square>(diagonal);
        moves.addAll(orthogonal);
        if (myGame.isCheck() == true) {
            moves = this.squaresToResolveCheck(moves);
        }
        Square[] result = moves.toArray(new Square[moves.size()]);
        return result;
    }
    
}
