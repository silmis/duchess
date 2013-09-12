package duchess.logic;
import java.util.ArrayList;

public class Game {
    private boolean isCheck;
    private boolean isMate;
    private boolean whitesTurn;
    private ArrayList<Piece> pieces;
    private String[] history;

    public Game() {
        this.isCheck = false;
        this.isMate = false;
        this.whitesTurn = true;
        this.pieces = new ArrayList(32);
        this.history = new String[10];
        this.positionPieces();
    }
    public Game(String fileName) { }
    
    public ArrayList<Piece> pieces() { return this.pieces; }
    public boolean isCheck() { return this.isCheck; }
    public boolean isMate() { return this.isMate; }
    public boolean isWhitesTurn() { return this.whitesTurn; }
    
    private void positionPieces() {
        // initialize all pieces at starting positions
        // pawns
        for (int i=1; i<=8; i++) {
            Pawn w = new Pawn(i, 2, true);
            Pawn b = new Pawn(i, 7, false);
            this.pieces.add(b);
            this.pieces.add(w);
        }
        // knights
        this.pieces.add(new Knight(2,1,true));
        this.pieces.add(new Knight(2,8,false));
        this.pieces.add(new Knight(7,1,true));
        this.pieces.add(new Knight(7,8,false));
        // bishops
        this.pieces.add(new Bishop(3,1,true));
        this.pieces.add(new Bishop(3,8,false));
        this.pieces.add(new Bishop(6,1,true));
        this.pieces.add(new Bishop(6,8,false));
        // rooks
        this.pieces.add(new Rook(1,1,true));
        this.pieces.add(new Rook(1,8,false));
        this.pieces.add(new Rook(8,1,true));
        this.pieces.add(new Rook(8,8,false));
        // queens
        this.pieces.add(new Queen(4,1,true));
        this.pieces.add(new Queen(5,8,false));
        // kings
        this.pieces.add(new King(5,1,true));
        this.pieces.add(new King(4,8,false));
    }
    public Piece[] whoCanMoveHere(int[] square) {
        return new Piece[1];
        // herein lies magic
    }
    public int[][] possibleMoves(int pieceIndex) {
        return pieces.get(pieceIndex).possibleMoves();
        // shit just got hard, man
    }
    public int[][] possibleMoves(Piece p) {
        return p.possibleMoves();
    }
}
