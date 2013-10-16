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
    private Game game;
    private boolean queryMode = false;
    private Piece selectedForMovement;
    public int boardSize;
    
    public Board(Game game, int boardSize) {
        this.game = game;
        this.boardSize = boardSize;
        this.setSize(boardSize, boardSize);
        this.setLocation(20, 20);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
    }
    public boolean getQueryMode() { return this.queryMode; }
    public boolean toggleQuery() { 
        this.queryMode = !(this.queryMode);
        return this.queryMode;
     }
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
        this.clearHighlight();
        if (vsq.getPiece() != null) {
                Square[] moves = vsq.getPiece().possibleMoves();
                this.highlight(moves, new Color(255,255,255));
        } else {
            Piece[] pieces = game.whoCanMoveHere(vsq.getSquare());
            ArrayList<Square> sqs = new ArrayList();
            for (Piece p : pieces) {
                sqs.add(p.getSquare());
            }
            Square[] moves = sqs.toArray(new Square[sqs.size()]);
            this.highlight(moves, new Color(0,255,0));
            this.highlight(new Square[] {vsq.getSquare()}, new Color(255,0,0));
        }
    }
    public void movePiece(VSquare vsq) {
        Piece previous = this.getSelectedPiece();
        previous = game.refreshPieceReference(previous);
        Piece clicked = vsq.getPiece();
        // there's a piece in the clicked square and there's no selected piece
        if ((clicked != null) && (previous == null)) {
             this.selectPiece(clicked);
             this.highlight(clicked.possibleMoves(), new Color(0,0,255));
        // there's a piece in the clicked square and it's the same color as the selected piece
        } else if (((clicked != null) && 
                    (clicked.getColor() == previous.getColor())) && 
                    (previous!= null)) {
             this.unSelectPiece();
             this.clearHighlight();
        // there is no piece in the clicked square and something is selected OR
        // there is a piece in the clicked square and it's of opposite color
        } else if (((clicked == null) && (previous != null)) || 
                   ((clicked != null) && 
                    (clicked.getColor() != previous.getColor()))) {

            VSquare prevSq = (VSquare) this.getComponent(
                    previous.getSquare().toIndex());
            
            boolean success = 
                    game.move(previous, vsq.getSquare());
            if (success == true) {
                this.updatePiecePositions();
                this.unSelectPiece();
                if (game.areVictoryConditionsMet()) {
                    this.victory();
                }
            } else {
                 this.unSelectPiece();
                 this.clearHighlight();
            }
        }
    }
    public void updatePiecePositions() {
        this.clearHighlight();
        Component[] vsqs = this.getComponents();
        for (Component vsq : vsqs) {
            ((VSquare)vsq).unSetPiece();
        }
        // index of Board.getComponents() corresponds 
        // to Square.toIndex()
        for (Piece p : this.game.getPieces()) {
            Square sq = p.getSquare();
            int position = sq.toIndex();
            VSquare vsq = (VSquare) this.getComponent(position);
            vsq.setPiece(p);
            
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
    private void victory() {
        if (game.isMate()) {
            String winner = !(game.isWhitesTurn()) ? 
                    "white" : "black";
            JOptionPane.showMessageDialog(null, "Game over, winner is: " + winner);
        } else {
            JOptionPane.showMessageDialog(null, "Game over, it's a draw!");
        }
    }
}
