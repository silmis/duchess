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
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MainWindow w = new MainWindow();
                w.setSize(800, 700);
                w.setVisible(true);
            }
        });
    }
}
