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
    public boolean isValidSquare(Square s) {
        // square is not within board limits
        if (((s.fl() > 8) || (s.rk() > 8)) ||
            ((s.fl() < 1) || (s.rk() < 1)) ) { 
            return false; 
        }
        return true;
    }
    // make sure there can only be one piece at a square (FIX moving!!)
    public Piece whoIsAt(Square s) {
        if (this.isValidSquare(s) == false) {
            return null;
        }
        for(Piece p : this.pieces) {
            if ((p.file == s.fl()) && (p.rank == s.rk())) {
                System.out.println("There's a " + p);
                return p;
            }
        }
        return null;
    }
    public boolean move(int pieceIndex, Square square) {
        return this.move(this.pieces.get(pieceIndex), square);
    }
    public boolean move(Piece p, Square square) {
        if (p == null) return false;
        Square[] possibleMoves = this.possibleMoves(p);
        if(possibleMoves.length < 1) { return false; }
        for(Square move : possibleMoves) {
            if ((square.fl() == move.fl()) && (square.rk() == move.rk())) {
                // if occupied, take the piece (this is still wrong)
                Piece toBeTaken = this.whoIsAt(move); 
                if ((toBeTaken != null) && 
                    (toBeTaken.getColor() != p.getColor())) {
                    pieces.remove(toBeTaken);
                }
                // move own piece
                System.out.println("moving " + p + " to " + move);
                p.changePos(move.fl(), move.rk());
                this.whitesTurn = !(this.whitesTurn);
                return true;
            }
        }
        return false;
    }
    public Piece[] whoCanMoveHere(Square square) {
        return new Piece[1];
    }
    public Piece[] listPieces() {
        return new Piece[1];
    }
    public Square[] possibleMoves(int pieceIndex) {
        return pieces.get(pieceIndex).possibleMoves();
    }
    // TO-DO
    public Square[] possibleMoves(Piece p) {
        try { 
            return ((Pawn) p).possibleMoves();
        } catch(ClassCastException e) {}
        try { 
            return ((Knight) p).possibleMoves();
        } catch(ClassCastException e) {}
        try { 
            return ((Bishop) p).possibleMoves();
        } catch(ClassCastException e) {}
        try { 
            return ((Rook) p).possibleMoves();
        } catch(ClassCastException e) {}
        try { 
            return ((Queen) p).possibleMoves();
        } catch(ClassCastException e) {}
        try { 
            return ((King) p).possibleMoves();
        } catch(ClassCastException e) {}
        
        return null;
    }
}
