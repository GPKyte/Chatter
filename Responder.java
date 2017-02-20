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
 * @author     Gavin Kyte
 * @version    1.1 (2017.2.19)
 */
public class Responder
{
    private Random randomGenerator;
    private HashMap<String, String> responses;
    private ArrayList<String> defaultResponses;
    private String endingString;
    private String name;
    private int responseCount;
    
    /**
     * Construct a Responder
     */
    public Responder() {
        randomGenerator = new Random();
        responses = new HashMap<>();
        defaultResponses = new ArrayList<>();
        endingString = "bye";
        name = "Name";
        responseCount = 0;
        fillResponses();
    }

    /**
     * Generate a response.
     * 
     * @return  A string that should be displayed as the response
     */
    public String generateResponse(String input) {
        // Clean input
        String text = formatText(input);
        
        // Set default response to a random phrase
        String response = defaultResponses.get(randomGenerator.nextInt(defaultResponses.size()));
        
        // Getting list of matched responses
        ArrayList<String> matches = new ArrayList<>();
        for (String key : responses.keySet()) {
            if (text.contains(key)) {
                String currentPhrase = responses.get(key);
                
                // Adapts pattern response to current input
                if (currentPhrase.startsWith("PATTERN")) {currentPhrase = patternResponse(currentPhrase, key, input); }
                matches.add(currentPhrase);
            }  
        }
        
        // Pick a response from the matched list, or return default if no matches
        if (matches.size() > 0) {            
            int pickRandom = randomGenerator.nextInt(matches.size());
            response = matches.get(pickRandom);
        }
        
        // Override the basic or multi-response with a pattern response if any available
        for (String match : matches){
            // Removes "PATTERN" and selects that response when applicable
            if (match.startsWith("PATTERN")) { response = match.substring(8); }
        }
        
        // Personalize final response and increment responseCount
        responseCount++;
        String personalResponse = addName(response);
        return personalResponse;
    }

    /**
     * Returns the ending string
     */
    public String getEndingString() {
        return endingString;
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
     * Prints welcome message.
     * Needs to be before the setName method to give instructions
     */
    public void printWelcome() {
        System.out.println("Welcome to Chatter!");
        System.out.println("");
        System.out.println("This system is designed to talk about music.");
        System.out.println("Talk as much as you want and have fun!");
        System.out.println("Type 'bye' to stop the chat.");
        System.out.println("\nPlease type your name below to begin.");
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
        responses.put("how are you", "I'm doing well. How are you?");
        responses.put("bad", "Oh no! What can we do to make you feel better?");
        responses.put("good", "That's great! I feel that way when I listen to music.");
        responses.put("no", "Oh. Well, it's true.");
        responses.put("yes", "I didn't understand that. Yes what?");
        responses.put("meet you", "Aw thanks! Do you like music?");
        
        responses.put("like music", "What kind of music do you like?");
        responses.put("rock", "I love AC/DC. How about you?");
        responses.put("pop", "Nice, my buddy listens to JPop. He's a bit weird though.");
        responses.put("country", "That's awesome. My friend Nick loves country music. Was your hometown rural, or more urban?");
        responses.put("rural", "Nice, Nick also grew up in the country, correlation maybe?");
        responses.put("urban", "How strange. Everyone I know in the city hates country. You're pretty special!");
        /** This may be a pattern */ responses.put("jazz", "Did you say jazz?! We need to go downtown sometime, I know a great jazz club OTR.");
        //responses.put("love", "Do you really? I support that for sure.");
        responses.put("classic", "Do you mean classic rock or more like Beethoven?");
        responses.put("classical", responses.get("classic"));
        responses.put("beethoven", "Not many people listen to classical music, why do you like it?");
        responses.put("too", "You too? Oh, that makes me happy.");
        responses.put("nothing", "Nothing at all? So you're the quiet type I see.");
        responses.put("edm", "That's definitely the best for homework and gaming in my opinion.");
        responses.put("dubstep", responses.get("edm"));
        
        responses.put("I love", "PATTERN I haven't heard of X before, how long have you liked that?");
        responses.put("I like", responses.get("I love"));
                
        defaultResponses.add("Not sure what you mean, could you say that more simply, or in a different way?");
        defaultResponses.add("That reminds me of how my brother died. Can we change the topic?");
        defaultResponses.add("Did you know that aliens smell purple and love breadsticks?");
        
        //"favorite, EDM, dubstep, singing, vocals, drums, band, instrument, earbuds, headphones, iphone7, audio"
        //"Oh, I'm sorry but I think we need to change topics."       
    }
    
    /**
     * Acknowledges patterns in user input to make unique responses based on
     * the phrase that stands as a variable in the pattern.
     * 
     * @return String used as unique, pattern-based response
     */
    public String patternResponse(String template, String pattern, String input) {
        // Need to cut out phrase from input and clean it
        // Get start index based on pattern, then get end based on punctuation or EOS
        int startOfPhrase = input.indexOf(pattern) + pattern.length() + 1;
        int endOfPhrase = input.indexOf(",", startOfPhrase);
        String phrase = formatText(input.substring(startOfPhrase, endOfPhrase)).trim();
        
        // Allows for more flexible sentence construction
        String firstHalf = template.split("X")[0];
        String lastHalf = template.split("X")[1];
        
        String response = firstHalf + phrase + lastHalf;
        return response;
    }
    
    /**
     * Strips text of common punctuation marks
     * 
     * @return String of words from input without punctuation
     */
    private String formatText(String text) {
        // Scrub out any punctuation
        String[] punctuation = {",",".","?","(",")",":",";","<",">","#","$","@"};
        for (String symbol : punctuation) {
            text = text.replace(symbol, "");
        }
              
        return text;
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
        boolean includeName = randomGenerator.nextBoolean();
        boolean canBeFront = !(phrase.startsWith("Oh") || phrase.startsWith("Nice") || phrase.startsWith("Aw"));
        
        // Deciding front or back
        if (includeName && (canBeFront && responseCount % 2 == 1)) {
            // front
            // lower case start of string if not "I "
            if (! phrase.startsWith("I ")) {
                phrase = phrase.substring(0,1).toLowerCase() + phrase.substring(1);
            }
            phrase = name + ", " + phrase;
            
        } else if (includeName) {
            // back
            // need to splice in name before ending punctuation
            phrase = phrase.substring(0, phrase.length()-1) + ", " + name + phrase.charAt(phrase.length()-1);
        }
        
        return phrase;
    }
}

