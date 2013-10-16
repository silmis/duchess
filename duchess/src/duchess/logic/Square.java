/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duchess.logic;

/**
 *
 * @author thitkone
 */
public class Square {
    private int file;
    private int rank;
    private boolean valid;
    public Square(int file, int rank) {
        if ((file > 8) || (rank > 8) ||
            (file < 1) || (rank < 1)) {
            this.file = -1;
            this.rank = -1;
            this.valid = false;
        } else {
            this.file = file;
            this.rank = rank;
            this.valid = true;
        }
    }
    public int fl() { return this.file; }
    public int rk() { return this.rank; } 
    public boolean isValid() { return this.valid; }
    /**
     * Compares whether two squares are the same.
     * @param s Square to compare
     * @return true if equal.
     */
    public boolean equals(Square s) {
        if ((this.file == s.file) && (this.rank == s.rank)) {
            return true;
        }
        return false;
    }
    /**
     * Returns the Square's position in a flattened 1-dimensional array 
     * where (1,1) is 0 and (8,8) is 63.
     * @return 
     */
    public int toIndex() {
        return ((this.rk()-1) * 8 + this.fl())-1;
    }
    public String toString() {
        return "(" + this.file + "," + this.rank+")";
    }
}
