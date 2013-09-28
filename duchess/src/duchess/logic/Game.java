package duchess.logic;
import java.util.ArrayList;
import java.lang.reflect.Constructor;

public class Game {
    private boolean isCheck;
    private boolean isMate;
    private boolean whitesTurn;
    private ArrayList<Piece> pieces;
    private String[] history;
    private Piece pieceGivingCheck;

    public Game() {
        this.isCheck = false;
        this.isMate = false;
        this.whitesTurn = true;
        this.pieces = new ArrayList(32);
        ///this.history = new String[10];
        this.positionPieces();
        this.pieceGivingCheck = null;
    }
    // load previous game
    public Game(String fileName) {
    }
    // empty board for testing
    public Game(int any) {
        this.isCheck = false;
        this.isMate = false;
        this.whitesTurn = true;
        this.pieces = new ArrayList(32);
        this.pieceGivingCheck = null;
    }
    
    public ArrayList<Piece> pieces() { return this.pieces; }
    public boolean isCheck() { return this.isCheck; }
    public boolean isMate() { return this.isMate; }
    public boolean isWhitesTurn() { return this.whitesTurn; }
    public Piece whoGivesACheck() { return this.pieceGivingCheck; }
    
    /**
     * Initializes all pieces at opening position.
     */
    private void positionPieces() {
        // pawns
        for (int i=1; i<=8; i++) {
            Pawn w = new Pawn(i, 2, true, this);
            Pawn b = new Pawn(i, 7, false, this);
            this.addPiece(w);
            this.addPiece(b);
        }
        // knights
        this.addPiece("Knight", new Square(2,1), true);
        this.addPiece("Knight", new Square(2,8), false);
        this.addPiece("Knight", new Square(7,1), true);
        this.addPiece("Knight", new Square(7,8), false);
        // bishops
        this.addPiece("Bishop", new Square(3,1), true);
        this.addPiece("Bishop", new Square(3,8), false);
        this.addPiece("Bishop", new Square(6,1), true);
        this.addPiece("Bishop", new Square(6,8), false);
        // rooks
        this.addPiece("Rook", new Square(1,1), true);
        this.addPiece("Rook", new Square(1,8), false);
        this.addPiece("Rook", new Square(8,1), true);
        this.addPiece("Rook", new Square(8,8), false);
        // queens
        this.addPiece("Queen", new Square(4,1), true);
        this.addPiece("Queen", new Square(4,8), false);
        // kings
        this.addPiece("King", new Square(5,1), true);
        this.addPiece("King", new Square(5,8), false);
    }
    // there might be a better way to do this
    /**
     * Adds a piece to the chessboard. Makes sure that square is valid and
     * not occupied.
     * @param className Type of piece as String, e.g. "Pawn"
     * @param s Square in which to place piece
     * @param color True for white, false for black
     * @return true for success
     */
    public boolean addPiece(String className, Square s, 
                            boolean color) {
        Class c; 
        Constructor co; 
        Piece newPiece;
        try {
            c = Class.forName("duchess.logic." + className);
            co = c.getConstructor(int.class, int.class, 
                                  boolean.class, Game.class);
            if (this.whoIsAt(s) != null) {
                throw new Exception("Square occupied");
            }
            newPiece = (Piece)co.newInstance(s.fl(), s.rk(),
                                             color, this);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        this.pieces.add(newPiece);
        return true;
    }
    /**
     * Adds an existing Piece to the chessboard.
     * @param p Piece
     * @return true for success
     */
    public boolean addPiece(Piece p) {
        return this.pieces.add(p);
    }
    /**
     * Alternative for move(Piece p, Square square).
     * @param pieceIndex The index of the Piece in array Game.pieces
     * @param square Square to move to.
     * @return see move(Piece p, Square square)
     */
    public boolean move(int pieceIndex, Square square) {
        return this.move(this.pieces.get(pieceIndex), square);
    }
    /**
     * Moves a piece on the chessboard. If the input move is legal, the
     * piece is moved to target square. If the square is occupied by an 
     * opponent, the opponent's piece is captured. The move can result in a
     * check or mate. A move ends the players turn.
     * @param p Piece
     * @param square Square to move to.
     * @return Returns false if piece is null or piece has no possible moves.
     * Otherwise returns true.
     */
    public boolean move(Piece p, Square square) {
        if (p == null) return false;
        Square[] possibleMoves = p.possibleMoves();
        //if(possibleMoves.length < 1) { return false; }
        for(Square move : possibleMoves) {
            if (square.equals(move)) {
                Piece toBeCaptured = this.whoIsAt(move); 
                if (toBeCaptured != null) {
                    pieces.remove(toBeCaptured); // can't capture own - illegal move
                }
                p.changePos(move.fl(), move.rk());
                Square[] nextTurnMoves = p.possibleMoves();
                for (Square nextMove : nextTurnMoves) {
                    if (this.whoIsAt(nextMove) instanceof King) {
                        this.isCheck = true;
                        this.pieceGivingCheck = p;
                    }
                }
                this.whitesTurn = !(this.whitesTurn);
                if (this.isCheck == true) {
                    boolean victory = true;
                    for (Piece p2 : this.pieces) {
                        if (p2.possibleMoves().length != 0) {
                            victory = false;
                        }
                    }
                    if (victory) this.isMate = true;
                    System.out.println("Game over");
                }
                return true;
            }
        }
        return false;
    }
    /**
     * Return the piece occupying a Square.
     * @param s Square
     * @return Piece or null if Square not occupied
     */
    public Piece whoIsAt(Square s) {
        if (s.isValid() == false) {
            return null;
        }
        for(Piece p : this.pieces) {
            if (p.getSquare().equals(s)) {
                return p;
            }
        }
        return null;
    }
    /**
     * Returns which pieces are capable of moving to the input square. 
     * Note: only concerns the player who can move at the turn in question. 
     * @param s Square
     * @return Array of Pieces
     */
    public Piece[] whoCanMoveHere(Square s) {
        ArrayList<Piece> list = new ArrayList();
        for (Piece p : this.pieces) {
            Square[] possibleMoves = p.possibleMoves();
            for (Square move : possibleMoves) {
                if(move.equals(s)) {
                    list.add(p);
                }
            }
        }
        Piece[] result = list.toArray(new Piece[list.size()]);
        return result;
    }
    public Piece[] listPieces() {
        // TODO
        return new Piece[1];
    }
    public boolean isValidSquare(Square s) {
        return s.isValid();
    }
    public Square[] possibleMoves(int pieceIndex) {
        return pieces.get(pieceIndex).possibleMoves();
    }
    public Square[] possibleMoves(Piece p) {
        return p.possibleMoves();
    }
}
