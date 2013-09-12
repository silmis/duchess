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

import java.util.ArrayList;
import duchess.logic.*;

/**
 *
 * @author thitkone
 */
public class NewGameTest {
    
    Game gm;
    ArrayList<Piece> pieces;
    
    public NewGameTest() {
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
        pieces = gm.pieces();
    }
    
    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void isWhitesTurn() {
        boolean whitesTurn = gm.isWhitesTurn();
        assertEquals(whitesTurn, true);
    }
    @Test
    public void isCheck() {
        boolean isCheck = gm.isCheck();
        assertEquals(isCheck, false);
    }
    @Test
    public void isMate() {
        boolean isMate = gm.isMate();
        assertEquals(isMate, false);
    }
    @Test
    public void kingsAreInPlace() {
        for(Piece p : pieces) {
            if(p instanceof King) {
                if (p.getColor() == true) {
                    assertEquals(p.getPos()[0], 5);
                    assertEquals(p.getPos()[1], 1);
                } else {
                    assertEquals(p.getPos()[0], 4);
                    assertEquals(p.getPos()[1], 8);
                }
            }
        }
    }
    /*@Test
    public void () {

    }*/
}