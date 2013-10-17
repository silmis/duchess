/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;
import duchess.logic.Game;
import duchess.logic.Piece;
import duchess.logic.Square;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author thitkone
 */
public class EndGameTest {
    
    Game gm;
    
    public EndGameTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        gm = new Game(0);
    }
    
    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    @Test
    public void CheckMateTest() {
        Piece bKing = gm.addPiece("King", 1, new Square(2,8), false);
        Piece wKing = gm.addPiece("King", 2, new Square(5,1), true);
        Piece wQueen = gm.addPiece("Queen", 3, new Square(3,5), true);
        Piece wRook = gm.addPiece("Rook", 4, new Square(3,6), true);
        gm.move(wRook, new Square(2,6)); // check
        gm.move(bKing, new Square(1,8));
        gm.move(wQueen, new Square(1,5)); // check & mate
        
        assertEquals((gm.isCheck() && gm.isMate()), true);
    }
    @Test
    public void StaleMateTest() {
        Piece bKing = gm.addPiece("King", 1, new Square(8,8), false);
        Piece wKing = gm.addPiece("King", 2, new Square(6,7), true);
        Piece wQueen = gm.addPiece("Queen", 3, new Square(7,5), true);
        gm.move(wQueen, new Square(7,6)); // stalemate
        
        boolean end = gm.areEndConditionsMet();
        assertEquals((end && !(gm.isMate())), true);
    }
}