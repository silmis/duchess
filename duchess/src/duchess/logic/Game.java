package duchess.logic;
import java.util.ArrayList;
import java.lang.reflect.Constructor;

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
        ///this.history = new String[10];
        this.positionPieces();
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
    }
    
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
        this.addPiece("Queen", new Square(5,8), false);
        // kings
        this.addPiece("King", new Square(5,1), true);
        this.addPiece("King", new Square(4,8), false);
    }
    // there might be a better way to do this
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
    public boolean addPiece(Piece p) {
        return this.pieces.add(p);
    }
    public boolean isValidSquare(Square s) {
        return s.isValid();
    }
    public Piece whoIsAt(Square s) {
        if (s.isValid() == false) {
            return null;
        }
        for(Piece p : this.pieces) {
            if ((p.file == s.fl()) && (p.rank == s.rk())) {
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
        Square[] possibleMoves = p.possibleMoves();
        if(possibleMoves.length < 1) { return false; }
        for(Square move : possibleMoves) {
            if ((square.fl() == move.fl()) && (square.rk() == move.rk())) {
                // if occupied, capture the piece
                Piece toBeCaptured = this.whoIsAt(move); 
                if (toBeCaptured != null) {
                    // note: moving to a friendly square 
                    // is be illegal in possibleMoves()
                    pieces.remove(toBeCaptured);
                }
                p.changePos(move.fl(), move.rk());
                // if king is threatened, game is in check
                Square[] nextTurnMoves = p.possibleMoves();
                for (Square nextMove : nextTurnMoves) {
                    if (this.whoIsAt(nextMove) instanceof King) {
                        this.isCheck = true;
                    }
                }
                // change turn and see if the player has lost
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
    public Piece[] whoCanMoveHere(Square s) {
        ArrayList<Piece> list = new ArrayList();
        for (Piece p : this.pieces) {
            Square[] possibleMoves = p.possibleMoves();
            for (Square move : possibleMoves) {
                if((s.fl() == move.fl()) && (s.rk() == move.rk()) ) {
                    list.add(p);
                }
            }
        }
        Piece[] result = list.toArray(new Piece[list.size()]);
        return result;
    }
    public Piece[] listPieces() {
        return new Piece[1];
    }
    public Square[] possibleMoves(int pieceIndex) {
        return pieces.get(pieceIndex).possibleMoves();
    }
    public Square[] possibleMoves(Piece p) {
        return p.possibleMoves();
    }
}
