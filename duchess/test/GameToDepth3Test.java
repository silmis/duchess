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
        boolean w1 = gm.move(8, new int[] {5,4});
        boolean b1 = gm.move(7, new int[] {4,5});
        boolean w2 = gm.move(8, new int[] {4,5});
        assertEquals((w1 && b1 && w2), true);
    }
}