/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duchess.logic;

/**
 *
 * @author thitkone
 */
public class Rook extends Piece {
    public Rook(int file, int rank, boolean color, Game myGame) {
        super(file, rank, color, myGame);
    }
    public int[][] possibleMoves() {
        return new int[1][1];
    }
    
}
