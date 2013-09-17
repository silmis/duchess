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
                    assertEquals(p.getFile(), 5);
                    assertEquals(p.getRank(), 1);
                } else {
                    assertEquals(p.getFile(), 4);
                    assertEquals(p.getRank(), 8);
                }
            }
        }
    }
    @Test
    public void isCenterBoardOccupied() {
       // some files on all ranks, should be false
       boolean center33 = gm.isOccupied(new int[] {3,3});
       boolean center64 = gm.isOccupied(new int[] {6,4});
       boolean center75 = gm.isOccupied(new int[] {7,5});
       boolean center16 = gm.isOccupied(new int[] {1,6});
       assertEquals((center33 && center64 && center75 && center16), false);
    }
    @Test
    public void areStartingRanksOccupied() {
        // some values on ranks 1-2 and 7-8, should all be true
        boolean start31 = gm.isOccupied(new int[] {3,1});
        boolean start62 = gm.isOccupied(new int[] {6,2});
        boolean start77 = gm.isOccupied(new int[] {7,7});
        boolean start18 = gm.isOccupied(new int[] {1,8});
        assertEquals((start31 && start62 && start77 && start18), true);
    }
    @Test
    public void isOccupiedOffBoard() {
        // should be "occupied" if not valid
        boolean offb1 = gm.isOccupied(new int[] {1,9});
        boolean offb2 = gm.isOccupied(new int[] {16,20});
        assertEquals((offb1 && offb2), true);
    }
    /*@Test
    public void () {

    }*/
}