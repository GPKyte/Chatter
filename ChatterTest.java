

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class ChatterTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class ChatterTest
{
    /**
     * Default constructor for test class ChatterTest
     */
    public ChatterTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }

    @Test
    public void generalTest()
    {
        Chatter chatter1 = new Chatter();
        chatter1.start();
    }
}


