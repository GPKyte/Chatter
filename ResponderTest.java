

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class ResponderTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class ResponderTest
{
    /**
     * Default constructor for test class ResponderTest
     */
    public ResponderTest()
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
    public void testPattern()
    {
        Responder responde1 = new Responder();
        System.out.println("Should see assertion, then the personalized one below.");
        System.out.println("That's awesome. How long have you liked X?");
        System.out.println(responde1.generateResponse("i like apples"));
    }
}

