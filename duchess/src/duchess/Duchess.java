/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duchess;
import java.util.Arrays;
import duchess.logic.Game;
/**
 *
 * @author thitkone
 */
public class Duchess {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Game b = new Game();
        System.out.println("white's turn: " + b.isWhitesTurn());
        System.out.println(b.pieces().toString());
        /*Pawn p1 = new Pawn(1,2);
        System.out.println(p1.getPos());
        p1.changePos(5,6);
        System.out.println(p1.getPos());
        System.out.println(p1.possibleMoves()[0]);
        System.out.println("jejeje");*/
    }
}
