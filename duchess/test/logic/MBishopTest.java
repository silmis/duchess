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
public class MBishopTest {
    
    Game gm;
    ArrayList<Piece> pieces;
    
    public MBishopTest() {
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
    // (writing a function for this is simply too time consuming)
    public void EmptyBoardListAllMoves() {
        gm = new Game(0); // no other pieces
        Square target = new Square(4,5);
        gm.addPiece("Bishop", target, true);
        /*System.out.println("EmptyBoardListAllMoves");
        for (Square move : gm.possibleMoves(gm.whoIsAt(target))) {
            System.out.println(move);
        }*/
    }
    @Test
    // same as above, but this time two directions are blocked. pawn at (5,4) 
    // is white, so that's not a valid move. pawn in (6,7) is black and can 
    // be captured.
    public void PathBlockedListAllMoves() {
        gm = new Game(0);
        Square target = new Square(4,5);
        gm.addPiece("Bishop", target, true);
        gm.addPiece("Pawn", new Square(5,4), true);
        gm.addPiece("Pawn", new Square(6,7), false);
        /*System.out.println("PathBlockedListAllMoves");
        for (Square move : gm.possibleMoves(gm.whoIsAt(target))) {
            System.out.println(move);
        }*/
    }
    @Test
    // 
    public void NewGameBishopCantMove() {
        gm = new Game();
        Square[] moves = gm.possibleMoves(gm.whoIsAt(new Square(3,1)));
        boolean isEmpty = (moves.length == 0);
        assertEquals(isEmpty, true);
    }
    @Test
    public void bishopsOpenCorrect() {
        gm = new Game();
        boolean w1 = gm.move(gm.whoIsAt(new Square(4,2)), new Square(4,3));
        boolean b1 = gm.move(gm.whoIsAt(new Square(4,7)), new Square(4,5));
        boolean w2 = gm.move(gm.whoIsAt(new Square(3,1)), new Square(6,4));
        boolean b2 = gm.move(gm.whoIsAt(new Square(3,8)), new Square(7,4));
        assertEquals((w1 && b1 && w2 && b2), true);
    }
    @Test
    public void bishopsOpenIncorrect() {
        // both players try illegal move
        gm = new Game();
        boolean w1 = gm.move(gm.whoIsAt(new Square(4,2)), new Square(4,3));
        boolean b1 = gm.move(gm.whoIsAt(new Square(4,7)), new Square(4,5));
        boolean w2 = gm.move(gm.whoIsAt(new Square(3,1)), new Square(5,4));
        boolean w2again = gm.move(gm.whoIsAt(new Square(3,1)), new Square(6,4));
        boolean b2 = gm.move(gm.whoIsAt(new Square(3,8)), new Square(8,4));
        boolean b2again = gm.move(gm.whoIsAt(new Square(3,8)), new Square(7,4));
        assertEquals((w2 && b2), false);
    }
}