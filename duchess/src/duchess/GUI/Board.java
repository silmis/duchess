/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duchess.GUI;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import duchess.logic.Square;
import duchess.logic.Piece;
import duchess.logic.Game;

/**
 *
 * @author thitkone
 */
public class Board extends JComponent {
    public static final int boardSize = 640;
    private boolean queryMode = false;
    private Piece selectedForMovement;
    private Game game;
    
    public Board(Game game) {
        this.game = game;
        this.setSize(boardSize, boardSize);
        this.setLocation(20, 20);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
    }
    public boolean isInQueryMode() { return this.queryMode; }
    public void selectPiece(Piece p) {
        this.selectedForMovement = p;
    }
    public void unSelectPiece() {
        this.selectedForMovement = null;
    }
    public Piece getSelectedPiece() { return this.selectedForMovement; }
    
    /**
     * Highlights squares on the board. If the square is occupied, it shows
     * possible moves. If not, it shows who can move there.
     * @param vsq VSquare
     */
    public void query(VSquare vsq) {
        if (vsq.getPiece() != null) {
                Square[] moves = vsq.getPiece().possibleMoves();
                this.highlight(moves, new Color(0,0,255));
        } else {
            Piece[] pieces = game.whoCanMoveHere(vsq.getSquare());
            ArrayList<Square> sqs = new ArrayList();
            for (Piece p : pieces) {
                sqs.add(p.getSquare());
            }
            Square[] moves = sqs.toArray(new Square[sqs.size()]);
            this.highlight(moves, new Color(0,0,255));
        }
    }
    public void movePiece(VSquare vsq) {
        Piece previous = this.getSelectedPiece();
        if ((vsq.getPiece() != null) && 
                 (previous == null)) {
             this.selectPiece(vsq.getPiece());
             this.highlight(vsq.getPiece().possibleMoves(), new Color(0,0,255));
         } else if (((vsq.getPiece() != null) && 
                     (vsq.getPiece().getColor() == previous.getColor())) && 
                    (previous!= null)) {
             this.unSelectPiece();
             this.clearHighlight();
         } else if (((vsq.getPiece() == null) && (previous != null)) || 
                    ((vsq.getPiece() != null) && 
                     (vsq.getPiece().getColor() != previous.getColor()))) {
             VSquare prevSq = (VSquare) this.getComponent(previous.getSquare().toIndex());
             boolean success = 
                     game.move(previous, vsq.getSquare());
             if (success == true) {
                 vsq.setPiece(previous);
                 prevSq.unSetPiece();
                 this.unSelectPiece();
                 this.clearHighlight();
             } else {
                 this.unSelectPiece();
                 this.clearHighlight();
             }
         }
    }
    public void highlight(Square[] moves, Color c) {
        for (Square sq : moves) {
            int i = sq.toIndex();
            VSquare vsq = (VSquare) this.getComponent(i);
            vsq.setBorder(BorderFactory
                    .createMatteBorder(3,3,3,3,c));

        }
    }
    public void clearHighlight() {
        Component[] comps = this.getComponents();
        for (Component c : comps) {
            VSquare vsq = (VSquare) c;
            vsq.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.black));
        }
    }
}
