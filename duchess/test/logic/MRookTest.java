package logic;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import duchess.logic.*;
import java.util.ArrayList;

/**
 *
 * @author thitkone
 */
public class MRookTest {
    
    Game gm;
    ArrayList<Piece> pieces;
    
    public MRookTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    // defeats the purpose of testing, but checked this manually to be true
    public void EmptyBoardListAllMoves() {
        gm = new Game(0); // no other pieces
        Square target = new Square(4,5);
        gm.addPiece("Rook", 1, target, true);
        /*System.out.println("EmptyBoardListAllMoves");
        for (Square move : gm.possibleMoves(gm.whoIsAt(target))) {
            System.out.println(move);
        }*/
    }
    @Test
    // same as above, but this time two directions are blocked. pawn at (4,6) 
    // is white, so that's not a valid move. pawn in (5,5) is black and can 
    // be captured.
    public void PathBlockedListAllMoves() {
        gm = new Game(0);
        Square target = new Square(4,5);
        gm.addPiece("Rook", 1, target, true);
        gm.addPiece("Pawn", 2, new Square(4,6), true);
        gm.addPiece("Pawn", 3, new Square(5,5), false);
        /*System.out.println("PathBlockedListAllMoves");
        for (Square move : gm.possibleMoves(gm.whoIsAt(target))) {
            System.out.println(move);
        }*/
    }
    @Test
    // 
    public void NewGameRookCantMove() {
        gm = new Game();
        Square[] moves = gm.whoIsAt(new Square(1,1)).possibleMoves();
        boolean isEmpty = (moves.length == 0);
        assertEquals(isEmpty, true);
    }
   
}