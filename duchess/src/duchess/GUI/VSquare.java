/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duchess.GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import duchess.logic.*;
import java.util.ArrayList;

/**
 * GUI counterpart of duchess.logic.Square
 * @author thitkone
 */
public class VSquare extends JPanel implements MouseListener {
    public static final int sideLength = 
            (int)Math.floor(Window.boardSize / 8.0);
    private Square sq;
    private Piece piece;
    private JLabel label;
    
    public VSquare(Square sq) {
        this.sq = sq;
        int xcoord = (sq.fl() - 1) * sideLength;
        int ycoord = Math.abs(sq.rk() - 8) * sideLength;
        this.setLocation(xcoord, ycoord);
        this.setSize(sideLength, sideLength);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setOpaque(true);
        if ( (sq.fl() + sq.rk()) % 2 == 0) {
            this.setBackground(Color.pink);
        } else {
            this.setBackground(Color.lightGray);
        }
        addMouseListener(this);
        this.label = new JLabel();
        this.add(label);
    }
    public Square getSquare() { return this.sq; }
    public Piece getPiece() { 
        Window w = (Window) this.getTopLevelAncestor();
        Game gm = w.getGame();
        Piece ps = gm.refreshPieceReference(this.piece);
        return ps; 
    }
    
    public void setPiece(Piece p) {
        this.piece = p;
        String className = this.piece.getClass().toString();
        String[] splitted = className.split("\\.");
        className = splitted[splitted.length-1];
        Color col = p.getColor() ? Color.white : Color.black;
        this.label.setText(className);
        this.label.setForeground(col);
    }
    public void unSetPiece() {
        this.piece = null;
        this.label.setText("");
    }
    public void mousePressed(MouseEvent e) {
        Board board = (Board)this.getParent();
        
        if (board.getQueryMode() == true) {
            board.query(this);
        } else { 
            board.movePiece(this);
        }
    }
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public String toString() {
        return sq.toString();
    }
}
