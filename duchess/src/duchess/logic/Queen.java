/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duchess.logic;

/**
 *
 * @author thitkone
 */
public class Queen extends Piece {
    public Queen(int file, int rank, boolean color) {
        super(file, rank, color);
    }
    public int[][] possibleMoves() {
        return new int[1][1];
    }
    
}