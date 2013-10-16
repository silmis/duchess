/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duchess.logic;
import java.util.ArrayList;
import java.util.Iterator;
/**
 *
 * @author thitkone
 */
public abstract class Piece {
    public final int pieceID;
    protected boolean color;
    protected int file;
    protected int rank;
    protected Game myGame;

    public Piece(int pieceID, int file, int rank, boolean color, Game myGame) {
        this.pieceID = pieceID;
        this.file = file;
        this.rank = rank;
        this.color = color;
        this.myGame = myGame;
    }
    
    public boolean getColor() { return this.color; }
    public int getFile() { return this.file; }
    public int getRank() { return this.rank; }
    public Square getSquare() { return new Square(this.file, this.rank); }
    
    /**
     * Changes the pieces position. Does not guarantee legality of move, 
     * use move() to play!
     * @param file
     * @param rank 
     */
    public void changePos(int file, int rank) {
        this.file = file;
        this.rank = rank;    
    }

    public Square[] possibleMoves() { return null; }
    
    protected boolean isItMyTurn() {
        if( (myGame.isWhitesTurn() == this.color)) {
            return true;
        }
        return false;
    }
    /**
     * Finds all legal diagonal or orthogonal moves (Bishop, Rook, Queen & 
     * King). Modifiers is an array of four (x,y) pairs of "weights" 
     * which enable the same loop to be run for all four directions 
     * on the board. By choosing the modifiers accordingly, we get 
     * either diagonal or orthogonal consecutive squares. Length 
     * determines how many squares are explored at any direction (1 for 
     * king, 8 to others). If edge of board is encountered, loop 
     * breaks. If an occupied square is encountered, move is only legal if
     * the piece occupying it is of opposite color.
     * Example of modifiers: see findDiagonalMoves() and 
     * findOrthogonalMoves()
     * 
     * @param modifiers integer array of length [4][2]
     * @param lenght depth of exploration in squares 
     * @return ArrayList of squares containing legal moves
     */
    protected ArrayList<Square> findConsecutiveSquares(int[][] modifiers, 
            int length) {
        ArrayList<Square> moves = new ArrayList();
        for (int c=0; c<4; c++) {
            for (int i=1; i<=length; i++) {
                int f = this.file + modifiers[c][0]*i;
                int r = this.rank + modifiers[c][1]*i;
                Square sq = new Square(f, r);
                if (sq.isValid() == false) break;
                Piece pieceInTargetSq = myGame.whoIsAt(sq);
                if ((pieceInTargetSq != null) && 
                    (pieceInTargetSq.getColor() != this.color)) {
                    moves.add(sq);
                    break;
                } else if (pieceInTargetSq != null) {
                    break;
                }
                moves.add(sq);
            }
        }
        return moves;
    }
    /**
     * Finds the diagonally aligned legal squares.
     * @param length depth of exploration in squares 
     * @return see findConsecutiveSquares()
     */
    protected ArrayList<Square> findDiagonalSquares(int length) {
        int[][] modifiers = {{1,1},{1,-1},{-1,1},{-1,-1}};
        return this.findConsecutiveSquares(modifiers, length);
    }
/**
     * Finds the orthogonally aligned legal squares.
     * @param length depth of exploration in squares 
     * @return see findConsecutiveSquares()
     */
    protected ArrayList<Square> findOrthogonalSquares(int length) {
        int[][] modifiers = {{0,1},{1,0},{0,-1},{-1,0}};
        return this.findConsecutiveSquares(modifiers, length);
    }
    /**
     * Shorthand for finding position of the king.
     * @return Square the king is in.
     */
    private Square kingSquare() {
        for(Piece p : myGame.getPieces()) {
            if ((p instanceof King) && 
                    (p.getColor() == myGame.isWhitesTurn())) {
                return p.getSquare();
            } 
        }
        return null;
    }
    /**
     * Find squares which resolve a check. When the game is in check, the
     * only legal moves for pieces other than the king are those that
     * are able to block the piece that is giving check.
     * @param moves List of normally legal moves from which to search from
     * @return List of moves (Squares) that resolve the check
     */
    protected ArrayList<Square> squaresToResolveCheck(
            ArrayList<Square> moves) {
        
        Square kingSquare = this.kingSquare();
        ArrayList<Square> resolvingSquares = new ArrayList();
        
        for (Square myMove : moves) {
 
            // pretend it's opponent's turn
            myGame.changeTurn();
            Square[] enemyMoves = myGame.lastMovedPiece().possibleMoves();
            myGame.changeTurn();
             
            for (Square enemyMove : enemyMoves) {
                if (myMove.equals(enemyMove)) {
                    Piece pieceGivingCheck = myGame.lastMovedPiece();
                    myGame.move(this, myMove);
                    Square[] tentative = pieceGivingCheck.possibleMoves();
                    myGame.undo();
                    boolean squareResolvesCheck = true;
                    for (Square tt : tentative) {
                        if (tt.equals(kingSquare)) {
                            squareResolvesCheck = false;
                        }
                    }
                    if (squareResolvesCheck) { 
                        resolvingSquares.add(myMove);
                    }
                }
            }
            Square enemyPosition = myGame.lastMovedPiece().getSquare();
            if (myMove.equals(enemyPosition)) { 
                resolvingSquares.add(enemyPosition);
            }
        }
        return resolvingSquares;
    }
    /**
     * Returns moves which will not result in check.
     * @param moves
     * @return 
     */
    protected ArrayList<Square> willResultInCheck(ArrayList <Square> moves) {
        Iterator<Square> iter = moves.iterator();
        Square kingSquare = this.kingSquare();
        while(iter.hasNext()) {
            Square sq = iter.next();
            boolean success = myGame.move(this, sq);
            if (success) {
                Piece[] enemies = myGame.whoCanMoveHere(kingSquare, false, true);
                myGame.undo();
                if (enemies.length > 0) iter.remove();
            } else {
                System.out.println("can't move to " + sq);
            }
            
        }
        return moves;
    }
    public String toString() {
        String className = this.getClass().toString();
        String[] splitted = className.split("\\.");
        className = splitted[splitted.length-1];
        String color = this.color ? "white" : "black";
        String s = color + " " + className  + 
                   " at " + this.file + "," + this.rank;
        return s;
    }
}
