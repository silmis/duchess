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
public class firstMovesTest {
    
    Game gm;
    ArrayList<Piece> pieces;
    
    public firstMovesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        gm = new Game();
    }
    
    @After
    public void tearDown() {
    }
    @Test
    public void whitePawnMove1stepsCorrect() {
        // for now, we "know" that 0 is a white pawn
        boolean success = gm.move(0, new int[] {1,3});
        assertEquals(success, true);
    }
    @Test
    public void whitePawnMove2stepsCorrect() {
        boolean success = gm.move(0, new int[] {1,4});
        assertEquals(success, true);
    }
    @Test
    public void whitePawnMove3stepsIncorrect() {
        boolean success = gm.move(0, new int[] {1,5});
        assertEquals(success, false);
    }
    @Test
    public void whitePawnMoveBackWardsIncorrect() {
        boolean success = gm.move(0, new int[] {1,1});
        assertEquals(success, false);
    }
    @Test
    public void whitePawnMoveDiagonallyToUnoccupiedSquare() {
        boolean success = gm.move(0, new int[] {2,3});
        assertEquals(success, false);
    }
    // same for black, white needs to move first
    @Test
    public void blackPawnMove1stepsCorrect() {
        // for now, we "know" that 4 is white and 1 is a black pawn
        boolean white = gm.move(4, new int[] {3,4});
        boolean black = gm.move(1, new int[] {1,6});
        assertEquals(black, true);
    }
    @Test
    public void blackPawnMove2stepsCorrect() {
        boolean white = gm.move(4, new int[] {3,4});
        boolean black = gm.move(1, new int[] {1,5});
        assertEquals(black, true);
    }
    @Test
    public void blackPawnMove3stepsIncorrect() {
        boolean white = gm.move(4, new int[] {3,4});
        boolean black = gm.move(1, new int[] {1,4});
        assertEquals(black, false);
    }
    @Test
    public void blackPawnMoveBackWardsIncorrect() {
        boolean white = gm.move(4, new int[] {3,4});
        boolean black = gm.move(1, new int[] {1,8});
        assertEquals(black, false);
    }
    @Test
    public void blackPawnMoveDiagonallyToUnoccupiedSquare() {
        boolean white = gm.move(4, new int[] {3,4});
        boolean black = gm.move(1, new int[] {2,6});
        assertEquals(black, false);
    }
    
    // try to move black before white
    @Test
    public void blackPawnMoveFirstIncorrect() {
        boolean black = gm.move(1, new int[] {1,6});
        assertEquals(black, false);
    }
}