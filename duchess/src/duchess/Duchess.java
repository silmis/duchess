/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duchess;
import java.util.Arrays;
import duchess.logic.*;
import duchess.GUI.MainWindow;
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
        boolean white = b.move(b.whoIsAt(new Square(3,2)), new Square(3,4));
        boolean black = b.move(b.whoIsAt(new Square(1,7)), new Square(1,5));
        MainWindow window = new MainWindow();
    }
}
