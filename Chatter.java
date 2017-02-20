
/**
 * This class 
 * 
 * @author      Gavin Kyte
 * @version     0.1 (2017.2.12)
 */
public class Chatter
{
    // instance variables
    private InputReader reader;
    private Responder responder;
    
    /**
     * Creates a chatter
     */
    public Chatter() {
        reader = new InputReader();
        responder = new Responder();
    }
    
    /**
     * Starts the chatter bot. Prints out a welcome message and
     * begins a conversation with the user until they end it.
     */
    public void start() {
        boolean finished = false;
        
        printWelcome();        
        while (!finished) {
            String input = reader.getInput().trim().toLowerCase();
            
            if (input.startsWith(responder.getEndingString())) {
                finished = true;
            } else {
                String response = responder.generateResponse(input);
                System.out.println(response);
            }
        }
        printGoodbye();
    }
    
    /**
     * Prints welcome message
     */
    public void printWelcome() {
        responder.printWelcome();
        responder.setName(reader.getInput().trim());
    }
    
    /**
     * Prints a good-bye message
     */
    public void printGoodbye() {
        responder.printGoodbye();
    }
    
}