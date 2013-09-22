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
    public String toString() {
        return "Square (" + this.file + "," + this.rank+")";
    }
}
