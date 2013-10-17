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
public class GamesTest {
    
    Game gm;
    
    public GamesTest() {
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
    public void LongerGame1Test() {
        boolean w1 = gm.move(gm.whoIsAt(new Square(5,2)), new Square(5,4));
        boolean b1 = gm.move(gm.whoIsAt(new Square(5,7)), new Square(5,5));
        boolean w2 = gm.move(gm.whoIsAt(new Square(6,1)), new Square(2,5));
        boolean b2 = gm.move(gm.whoIsAt(new Square(3,7)), new Square(3,6));
        boolean w3 = gm.move(gm.whoIsAt(new Square(2,5)), new Square(1,4));
        boolean b3 = gm.move(gm.whoIsAt(new Square(2,7)), new Square(2,5));
        boolean w4 = gm.move(gm.whoIsAt(new Square(1,4)), new Square(2,3));
        boolean b4 = gm.move(gm.whoIsAt(new Square(1,7)), new Square(1,6));
        
        boolean w5 = gm.move(gm.whoIsAt(new Square(4,2)), new Square(4,3));
        
        // queen checks
        boolean b5 = gm.move(gm.whoIsAt(new Square(4,8)), new Square(1,5));

        assertEquals(gm.isCheck(), true);
    }
   
    
}