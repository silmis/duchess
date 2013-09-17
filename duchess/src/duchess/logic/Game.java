package duchess.logic;
import java.util.Arrays;
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
            Pawn w = new Pawn(i, 2, true, this);
            Pawn b = new Pawn(i, 7, false, this);
            this.pieces.add(w);
            this.pieces.add(b);
        }
        // knights
        this.pieces.add(new Knight(2,1,true, this));
        this.pieces.add(new Knight(2,8,false, this));
        this.pieces.add(new Knight(7,1,true, this));
        this.pieces.add(new Knight(7,8,false, this));
        // bishops
        this.pieces.add(new Bishop(3,1,true, this));
        this.pieces.add(new Bishop(3,8,false, this));
        this.pieces.add(new Bishop(6,1,true, this));
        this.pieces.add(new Bishop(6,8,false, this));
        // rooks
        this.pieces.add(new Rook(1,1,true, this));
        this.pieces.add(new Rook(1,8,false, this));
        this.pieces.add(new Rook(8,1,true, this));
        this.pieces.add(new Rook(8,8,false, this));
        // queens
        this.pieces.add(new Queen(4,1,true, this));
        this.pieces.add(new Queen(5,8,false, this));
        // kings
        this.pieces.add(new King(5,1,true, this));
        this.pieces.add(new King(4,8,false, this));
    }
    public boolean isValidSquare(int[] square) {
        // square is not within board limits
        if (((square[0] > 8) || (square[1] > 8)) ||
            ((square[0] < 1) || (square[1] < 1)) ) { 
            return false; 
        }
        return true;
    }
    // to-do: should return the piece OR I make another method that will
    public boolean isOccupied(int[] square) {
        if (this.isValidSquare(square) == false) { 
            return true; // if square is not valid, it is "occupied" (this sucks)
        }
        boolean fileCheck, rankCheck;
        for(Piece p : this.pieces) {
            fileCheck = false;
            rankCheck = false;
            if(p.file == square[0]) { fileCheck = true; };
            if(p.rank == square[1]) { rankCheck = true; };
            if (fileCheck && rankCheck) {
                return true;
            }
        }
        return false;
    }
    public boolean move(int pieceIndex, int[] square) {
        // list all possible moves
        int[][] possibleMoves = this.possibleMoves(pieceIndex);
        // none found : don't move
        if(possibleMoves.length < 1) { return false; }
        // if desired move is possible ..
        for(int[] move : possibleMoves) {
            if ((square[0] == move[0]) && (square[1] == move[1])) {
                // move the piece
                pieces.get(pieceIndex).changePos(move[0], move[1]);
                // if it takes another, remove it (doesn't work)
                if (this.isOccupied(move) == true) {
                    //pieces.remove(pieceIndex);
                }
                this.whitesTurn = !(this.whitesTurn);
                return true;
            }
        }
        return false;
    }
    public Piece[] whoCanMoveHere(int[] square) {
        return new Piece[1];
    }
    public int[][] possibleMoves(int pieceIndex) {
        return pieces.get(pieceIndex).possibleMoves();
    }
    public int[][] possibleMoves(Piece p) {
        return p.possibleMoves();
    }
}
