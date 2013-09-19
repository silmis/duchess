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
public class GameToDepth3Test {
    
    Game gm;
    
    public GameToDepth3Test() {
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
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void WhiteTakesD5() {
        Square target = new Square(4,5); 
        boolean w1 = gm.move(gm.whoIsAt(new Square(5,2)), new Square(5,4));
        boolean b1 = gm.move(gm.whoIsAt(new Square(4,7)), target);
        boolean w2 = gm.move(gm.whoIsAt(new Square(5,4)), target);
        Piece p = gm.whoIsAt(target);
        assertEquals(p.getColor(), true);
    }
    @Test
    public void OpeningWithHorses() { 
        boolean w1 = gm.move(gm.whoIsAt(new Square(2,1)), new Square(3,3));
        boolean b1 = gm.move(gm.whoIsAt(new Square(2,8)), new Square(3,6));
        boolean w2 = gm.move(gm.whoIsAt(new Square(7,1)), new Square(6,3));
        assertEquals((w1 && b1 && w2), true);
    }
    @Test
    public void WhiteKnightTakesE5() {
        Square target = new Square(5,5); 
        boolean w1 = gm.move(gm.whoIsAt(new Square(7,1)), new Square(6,3));
        boolean b1 = gm.move(gm.whoIsAt(new Square(5,7)), target);
        boolean w2 = gm.move(gm.whoIsAt(new Square(6,3)), target);
        Piece p = gm.whoIsAt(target);
        assertEquals(p.getColor(), true);
    }
    
}