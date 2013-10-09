package duchess.logic;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.lang.reflect.Constructor;

public class Game {
    private boolean isCheck;
    private boolean resolveCheck;
    private boolean isMate;
    private boolean whitesTurn;
    private ArrayList<Piece> pieces;
    private Piece lastMovedPiece;
    private ArrayDeque<GameState> pastStates;
    public Logkeeper log;

    public Game() {
        this.isCheck = false;
        this.resolveCheck = true;
        this.isMate = false;
        this.whitesTurn = true;
        this.pieces = new ArrayList(32);
        this.lastMovedPiece = null;
        this.log = new Logkeeper();
        this.pastStates = new ArrayDeque();
        this.positionPieces();
    }
    // load previous game
    public Game(GameState gs) {
        this.setState(gs);
        this.pastStates = new ArrayDeque(); // not saved!
    }
    // empty board for testing
    public Game(int any) {
        this.isCheck = false;
        this.resolveCheck = true;
        this.isMate = false;
        this.whitesTurn = true;
        this.pieces = new ArrayList(32);
        this.lastMovedPiece = null;
        this.log = new Logkeeper();
    }
    
    public ArrayList<Piece> getPieces() { return this.pieces; }
    public boolean isCheck() { return this.isCheck; }
    public boolean resolveCheck() { return this.resolveCheck; }
    public boolean isMate() { return this.isMate; }
    public boolean isWhitesTurn() { return this.whitesTurn; }
    public Piece lastMovedPiece() { return this.lastMovedPiece; }
    protected void changeTurn() { this.whitesTurn = !(this.whitesTurn); }
    public void setResolveCheck(boolean set) { this.resolveCheck = set; }
    /**
     * Initializes all pieces at opening position.
     */
    private void positionPieces() {
        // pawns
        for (int i=1; i<=8; i++) {
            Pawn w = new Pawn(i, i, 2, true, this);
            Pawn b = new Pawn(i+8, i, 7, false, this);
            this.addPiece(w);
            this.addPiece(b);
        }
        // knights
        this.addPiece("Knight", 17, new Square(2,1), true);
        this.addPiece("Knight", 18, new Square(2,8), false);
        this.addPiece("Knight", 19, new Square(7,1), true);
        this.addPiece("Knight", 20, new Square(7,8), false);
        // bishops
        this.addPiece("Bishop", 21, new Square(3,1), true);
        this.addPiece("Bishop", 22, new Square(3,8), false);
        this.addPiece("Bishop", 23, new Square(6,1), true);
        this.addPiece("Bishop", 24, new Square(6,8), false);
        // rooks
        this.addPiece("Rook", 25, new Square(1,1), true);
        this.addPiece("Rook", 26, new Square(1,8), false);
        this.addPiece("Rook", 27, new Square(8,1), true);
        this.addPiece("Rook", 28, new Square(8,8), false);
        // queens
        this.addPiece("Queen", 29, new Square(4,1), true);
        this.addPiece("Queen", 30, new Square(4,8), false);
        // kings
        this.addPiece("King", 31, new Square(5,1), true);
        this.addPiece("King", 32, new Square(5,8), false);
    }
    /**
     * Check if opponent is in check
     */
    private void updateCheck() {
        Square kingSquare = null;
        for(Piece p : this.getPieces()) {
            if ((p instanceof King) && 
                    (p.getColor() == !(this.isWhitesTurn()))) {
                kingSquare = p.getSquare();
            } 
        }
        Piece[] temp = this.whoCanMoveHere(kingSquare);
        if (this.whoCanMoveHere(kingSquare).length > 0) {
            this.isCheck = true;
            System.out.println("Game checked");
        } else {
            this.isCheck = false;
        }
    }
    private boolean areVictoryConditionsMet() {
        if (this.isCheck == true) {
            boolean victory = true;
            for (Piece p : this.pieces) {
                if (p.possibleMoves().length != 0) {
                    victory = false;
                    System.out.println("no victory");
                }
            }
            if (victory == true) this.isMate = true;
            return victory;
        }
        return false;
    }
    
    private void gameOver() {
        String winner = this.lastMovedPiece.getColor() ? 
                "white" : "black";
        System.out.println("Game over! Winner: " +winner);
    }
    private void takeSnapshot() {
        this.pastStates.addFirst(new GameState());
    }
    public void undo() {
        if (this.pastStates.peek() != null) {
            System.out.println("undo");
            this.setState(this.pastStates.poll());
        }
    }
    private void setState(GameState gs) {
        this.isCheck = gs.isCheck;
        this.isMate = gs.isMate;
        this.whitesTurn = gs.whitesTurn;
        this.pieces = gs.pieces;
        this.lastMovedPiece = gs.lastMovedPiece;
        this.log = gs.logger;
    }
    /**
     * Promotes a pawn if on the right rank at the opposing side.
     * @param p Pawn to promote
     * @return Queen piece
     */
    private Piece promotePawn(Piece p) {
        if (!(p instanceof Pawn)) return p;
        boolean color = p.getColor();
        Square sq = p.getSquare();
        if ((sq.rk() == 0) && (color == false) ||
            (sq.rk() == 8) && (color == true)) {
            
            int idNum = 0;
            for (Piece k : this.pieces) {
                idNum = Math.max(idNum, k.pieceID);
            }
            idNum += 1;
            
            this.pieces.remove(p);
            p = this.addPiece("Queen", idNum, sq, color);
        }
        return p;
    }
    /**
     * Adds a piece to the chessboard. Makes sure that square is valid and
     * not occupied.
     * @param className Type of piece as String, e.g. "Pawn"
     * @param s Square in which to place piece
     * @param color True for white, false for black
     * @return true for success
     */
    public Piece addPiece(String className, int pieceID, Square s, 
                            boolean color) {
        Class c; 
        Constructor co; 
        Piece newPiece;
        try {
            c = Class.forName("duchess.logic." + className);
            co = c.getConstructor(int.class, int.class, int.class, 
                                  boolean.class, Game.class);
            if (this.whoIsAt(s) != null) {
                throw new Exception("Square occupied");
            }
            newPiece = (Piece)co.newInstance(pieceID, s.fl(), s.rk(),
                                             color, this);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        this.pieces.add(newPiece);
        return newPiece;
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
        p = this.refreshPieceReference(p);
        for(Square move : possibleMoves) {
            if (square.equals(move)) {
                this.takeSnapshot();
                Piece toBeCaptured = this.whoIsAt(move); 
                if (toBeCaptured != null) {
                    pieces.remove(toBeCaptured);
                }
                this.log.add(p, p.getSquare(), move);
                Square prevSq = p.getSquare();
                p.changePos(move.fl(), move.rk());
                //p = this.promotePawn(p);
                this.lastMovedPiece = p;
                this.updateCheck();
                this.changeTurn();
                boolean victory = this.areVictoryConditionsMet();
                if (victory==true) gameOver();
                System.out.println("Move " + prevSq + "->" +move);
                return true;
            }
        }
        return false;
    }
    public boolean move(int pieceIndex, Square square) {
        return this.move(this.pieces.get(pieceIndex), square);
    }
    public Piece refreshPieceReference(Piece p) {
        if (p == null) return null;
        int ID = p.pieceID;
        return this.hasID(ID);
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
     * @param opponent if true, return returns opponent's pieces
     * @param excludeKings leave kings out of this (needed for resolving check)
     * @return Array of Pieces
     */
    public Piece[] whoCanMoveHere(Square s, boolean opponent, 
            boolean excludeKings) {
        // pretend it's opponent's turn
        if (opponent==true) {
            this.changeTurn();
        }
        ArrayList<Piece> piecesCopy = (ArrayList<Piece>) this.pieces.clone();
        
        if (excludeKings == true) {
            for(Piece p : piecesCopy) {
                if (p instanceof King) piecesCopy.remove(p);
            }
        }
        ArrayList<Piece> list = new ArrayList();
        for (Piece p : piecesCopy) {
            Square[] possibleMoves = p.possibleMoves();
            for (Square move : possibleMoves) {
                if(move.equals(s)) {
                    list.add(p);
                    System.out.println("Who can move to " + s + "called, " + p);
                }
            }
        }
        // stop pretending
        if (opponent==true) {
            this.changeTurn();
        }
        Piece[] result = list.toArray(new Piece[list.size()]);
        return result;
    }
    public Piece[] whoCanMoveHere(Square s) {
        return whoCanMoveHere(s, false, false);
    }
    public Piece hasID(int i) {
        Piece result = null;
        for (Piece p : this.pieces) {
            if (p.pieceID == i) result = p;
        }
        return result;
    }
    public void listPieces() {
        for (Piece p : this.pieces) {
            System.out.println(p);
        }
    }
    public void loadGame() {
    }
    public boolean saveGame(String filePath) {
        SaveManager sm = new SaveManager();
        sm.setPath(filePath);
        sm.write();
        return true;
    }
    /**
     * Fully describes the state of the game.
     */
    public class GameState {
        public ArrayList<Piece> pieces;
        public Logkeeper logger;
        public boolean isCheck;
        public boolean resolveCheck;
        public boolean isMate;
        public boolean whitesTurn;
        public Piece lastMovedPiece;
        /**
         * Copies the current state of the game.
         */
        public GameState() {
            this.pieces = new ArrayList<Piece>();
            for (Piece p : getPieces()) {
                Piece n = this.copyPiece(p);
                this.pieces.add(n);
            }
            this.logger = log;
            this.isCheck = isCheck();
            this.resolveCheck = resolveCheck();
            this.isMate = isMate();
            this.whitesTurn = isWhitesTurn();
          
            Piece p = lastMovedPiece();
            Piece n = this.copyPiece(p);
            this.lastMovedPiece = n;
        }
        public Piece copyPiece(Piece p) {
            Piece n = null;
            if (p != null) {
                Class c = p.getClass();
                try {
                    Constructor co = c.getConstructor(c);
                    n = (Piece) co.newInstance(p);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            return n;
        }
    }
}
