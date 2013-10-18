/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duchess;
import java.util.Arrays;
import duchess.logic.*;
import duchess.GUI.Window;
/**
 * Entry point.
 * @author thitkone
 */
public class Duchess {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Window w = new Window();
                w.setVisible(true);
            }
        });
    }
}
