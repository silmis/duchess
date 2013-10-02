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
        pieces = gm.getPieces();
    }
    
    @After
    public void tearDown() {
    }
    @Test
    public void kingsAreInPlace() {
        for(Piece p : pieces) {
            if(p instanceof King) {
                if (p.getColor() == true) {
                    assertEquals(p.getFile(), 5);
                    assertEquals(p.getRank(), 1);
                } else {
                    assertEquals(p.getFile(), 5);
                    assertEquals(p.getRank(), 8);
                }
            }
        }
    }
    @Test
    public void isCenterBoardOccupied() {
       // some files on all ranks, should be false
       Piece center33 = gm.whoIsAt(new Square(3,3));
       Piece center64 = gm.whoIsAt(new Square(6,4));
       Piece center75 = gm.whoIsAt(new Square(7,5));
       Piece center16 = gm.whoIsAt(new Square(1,6));
       boolean unoccupied = false;
       if (center33 == null && 
           center64 == null && 
           center75 == null && 
           center16 == null) { unoccupied = true; }
       assertEquals(unoccupied, true);
    }
    @Test
    public void areStartingRanksOccupied() {
        // some values on ranks 1-2 and 7-8, should all be true
        Piece start31 = gm.whoIsAt(new Square(3,1));
        Piece start62 = gm.whoIsAt(new Square(6,2));
        Piece start77 = gm.whoIsAt(new Square(7,7));
        Piece start18 = gm.whoIsAt(new Square(1,8));
        boolean unoccupied = false;
        if (start31 == null || 
            start62 == null || 
            start77 == null || 
            start18 == null) { unoccupied = true; }
        assertEquals(unoccupied, false);
    }
    @Test
    public void whoIsAtOffBoard() {
        // should be null if not valid
        Piece offb1 = gm.whoIsAt(new Square(1,9));
        Piece offb2 = gm.whoIsAt(new Square(16,20));
        boolean someoneThere = true;
        if (offb1 == null && 
            offb2 == null ) { someoneThere = false; }
        assertEquals(someoneThere, false);
    }
    @Test
    public void whoCanMoveToC3() {
        Piece[] pieces = gm.whoCanMoveHere(new Square(3,3));
        for (Piece p : pieces) {
            //System.out.println(p);
        }
        boolean correctLength = (pieces.length == 2);
        assertEquals(correctLength, true);
    }
}