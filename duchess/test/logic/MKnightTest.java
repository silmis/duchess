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
public class MKnightTest {
    
    Game gm;
    ArrayList<Piece> pieces;
    
    public MKnightTest() {
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
    
    // knights
    @Test
    public void LegalKnightMoves() { 
        boolean w1 = gm.move(gm.whoIsAt(new Square(2,1)), new Square(3,3));
        boolean b1 = gm.move(gm.whoIsAt(new Square(2,8)), new Square(3,6));
        boolean w2 = gm.move(gm.whoIsAt(new Square(7,1)), new Square(6,3));
        assertEquals((w1 && b1 && w2), true);
    }
    
    @Test
    public void IllegalKnightMoves() { 
        boolean w1 = gm.move(gm.whoIsAt(new Square(2,1)), new Square(2,3));
        boolean w1again = gm.move(gm.whoIsAt(new Square(2,1)), new Square(3,3));
        boolean b1 = gm.move(gm.whoIsAt(new Square(2,8)), new Square(3,7));
        boolean b1again = gm.move(gm.whoIsAt(new Square(2,8)), new Square(3,6));
        assertEquals((w1 || b1), false);
    }
}