import java.util.ArrayList;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The responder class represents a response generator object. It is used
 * to generate an automatic response. This is the second version of this 
 * class. This time, we generate some random behavior by randomly selecting 
 * a phrase from a predefined list of responses.
 * 
 * @author     Michael Kölling David J. Barnes, and Gavin Kyte
 * @version    0.3 (2017.2.12)
 */
public class Responder
{
    private Random randomGenerator;
    private HashMap<String, String> responses;
    private ArrayList<String> defaultResponses;
    private String endingString;
    private String name;

    /**
     * Construct a Responder
     */
    public Responder() {
        randomGenerator = new Random();
        responses = new HashMap<>();
        defaultResponses = new ArrayList<>();
        endingString = "bye";
        name = "Name";
        fillResponses();
    }

    /**
     * Generate a response.
     * 
     * @return  A string that should be displayed as the response
     */
    public String generateResponse(String input) {
        ArrayList<String> text = formatText(input);
        // Get default response randomly
        String response = defaultResponses.get(randomGenerator.nextInt(defaultResponses.size()));
        
        // Getting list of matched responses
        ArrayList<String> matches = new ArrayList<>();
        for (String keyword : text){
            if (responses.get(keyword) != null) {
                matches.add(responses.get(keyword));
            }
        }
        
        if (matches.size() > 0) {
            // Pick a response from the matched list, or return default if no matches
            int pickRandom = randomGenerator.nextInt(matches.size());
            response = matches.get(pickRandom);
        }
        
        String uniqueResponse = addName(response);
        return uniqueResponse;
    }

    /**
     * Returns the ending string
     */
    public String getEndingString() {
        return endingString;
    }
    
    /**
     * Prints welcome message
     */
    public void printWelcome() {
        System.out.println("Welcome to Chatter!");
        System.out.println("");
        System.out.println("This system is designed to talk about music.");
        System.out.println("Talk as much as you want and have fun!");
        System.out.println("Type 'bye' to stop the chat.");
        System.out.println("Please type your name below to begin.");        
    }
    
    /**
     * Prints a good-bye message
     */
    public void printGoodbye() {
        System.out.println("Nice talking to you! Bye =]");
    }
    
    /**
     * Build up a list of responses
     */
    private void fillResponses() {
        responses.put("how", "I am doing well. How are you?");
        responses.put("bad", "Oh no! What can we do to make you feel better?");
        responses.put("good", "That's great! I feel that way when I listen to music.");
        responses.put("no", "Oh. Well, it's true.");
        
        responses.put("music", "What kind of music do you like?");
        responses.put("rock", "I love AC/DC. How about you?");
        responses.put("pop", "Nice, my buddy listens to JPop. He's a bit weird though. ");
        responses.put("country", "Ah, my friend Nick loves country music. Was your hometown rural, or more urban?");
        responses.put("rural", "Nice, Nick also grew up in the country, correlation maybe?");
        responses.put("urban", "How strange. Everyone I know in the city hates country. You're pretty special!");
        responses.put("jazz", "Jazz?! We need to go downtown sometime, I know a great place.");
        responses.put("love", "Do you really? I support that for sure.");
        responses.put("classic", "Ooh, classic rock or more like Beethoven classic?");
        responses.put("beethoven", "");
        responses.put("too", "You do too? Small world.");
        responses.put("nothing", "Nothing at all? So you're the quiet type I see.");
        responses.put("edm", "That's definitely the best for homework and gaming in my opinion.");
        responses.put("dubstep", "That's definitely the best for homework and gaming in my opinion.");
        
        defaultResponses.add("Not sure what you mean, could you say that more simply, or in a different way?");
        defaultResponses.add("Oh, that's how my brother died. Can we change the topic to jazz?");
        defaultResponses.add("Did you know that aliens smell purple and love breadsticks?");
        
        //"favorite, EDM, dubstep, singing, vocals, drums, band, instrument, earbuds, headphones, iphone7, audio"
        //"Oh, I'm sorry but I think we need to change topics."       
    }
    
    /**
     * Strips text of punctuation, then splits it into an ArrayList
     * 
     * @return An ArrayList containing words from input without punctuation
     */
    public ArrayList<String> formatText(String text) {
        // First step is to scrub out any punctuation
        String[] punctuation = {",",".","?","(",")",":",";","<",">","#","$"};
        for (String symbol : punctuation) {
            text = text.replace(symbol, "");
        }
        
        // Second, split the sentence at every space
        // "\\s+" will remove whitespace, even if multiple spaces are used
        String[] wordList = text.split("\\s+");       

        // Finally, put words into ArrayList
        ArrayList<String> formattedText = new ArrayList<>();
        for (String word : wordList){
            formattedText.add(word);
        }
        
        return formattedText;
    }
    
    /**
     * Workaround to avoid repeat code for Input
     * This will set the name needed to personalize phrases
     */
    public void setName(String name) {
        this.name = name;
        System.out.println("Nice to meet you, " + name + "!");
    }
    
    /**
     * Designed to add the user's name to the front or back of a sentence.
     * This will account for capitalization and punctuation dynamically.
     * 
     * @return A string that should be returned to the user as a unique response.
     */
    public String addName(String phrase) {
        /*
         * Goal is to correctly add "<name>, " or ", <name><. or ? or !>" to phrase
         * Will need to change capitalization, and end with correct punctuation
         * We can have multiple problems here:
         * - First word is I and must stay capitalized
         * - Sentence logic error. I.e. "Name, oh! I also do that." 
         * - Phrase empty
         */
        
        // Deciding front or back
        if (randomGenerator.nextInt(2) == 1) {
            // front
            // lower case start of string if not "I "
            if (! phrase.startsWith("I ")) {
                phrase = phrase.substring(0,1).toLowerCase() + phrase.substring(1);
            }
            phrase = name + ", " + phrase;
            
        } else {
            // back
            // need to splice in name before ending punctuation
            phrase = phrase.substring(0, phrase.length()-1) + ", " + name + phrase.charAt(phrase.length()-1);
        }
        
        return phrase;
    }
}

