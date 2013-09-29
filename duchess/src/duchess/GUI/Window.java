/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duchess.GUI;
import duchess.logic.Square;
import duchess.logic.Game;
import duchess.logic.Piece;

import java.awt.*;
import javax.swing.*;
/**
 *
 * @author thitkone
 */
public class Window extends javax.swing.JFrame {
    public static final int boardSize = 640;
    public static final int windowWidth = 900;
    public static final int windowHeight = 700;
    private Game game;
    
    public Window() {
        initComponents();
        initNewGame();
        initLeftPane();
        this.setSize(windowWidth, windowHeight);
        
    }
    private void initLeftPane() {
        JPanel leftPane = new JPanel();
        leftPane.setSize(200, 640);
        leftPane.setLocation(680, 20);
        leftPane.setBorder(BorderFactory.createLineBorder(Color.black));
        this.add(leftPane);
        JButton queryButton = new JButton("Query Mode OFF");
        leftPane.add(queryButton);
    }
    private void initNewGame() {
        this.game = new Game();
        Board board = new Board(this.game, boardSize);
        for (int r=1; r<=8; r++) {
            for (int f=1; f<=8; f++) {
                VSquare s = new VSquare(new Square(f, r));
                board.add(s);
            }
        }
        // Note: index of Board.getComponents() now corresponds 
        // to Square.toIndex()
        for (Piece p : this.game.pieces()) {
            Square sq = p.getSquare();
            int position = sq.toIndex();
            VSquare vsq = (VSquare) board.getComponent(position);
            vsq.setPiece(p);
            
        }
        this.add(board);
    }
    public Game getGame() { return this.game; }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 692, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 485, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    /*public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        /*try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}