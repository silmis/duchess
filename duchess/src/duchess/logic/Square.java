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
    public Square(int file, int rank) {
        this.file = file;
        this.rank = rank;
    }
    public int fl() { return this.file; }
    public int rk() { return this.rank; } 
    public String toString() {
        return "Square (" + this.file + "," + this.rank+")";
    }
}
