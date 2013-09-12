/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duchess.logic;

/**
 *
 * @author thitkone
 */
public class King extends Piece {
    public King(int file, int rank, boolean color) {
        super(file, rank, color);
    }
    public int[][] possibleMoves() {
        int [][] r = new int[1][1];
        r[0][0] = 2;
        return r;
    }
    
}
