import java.util.ArrayList;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The responder class represents is designed to contain and select relevant
 * responses to simulate a conversation with the user. In the absence of
 * a matched response, a default, general response will be used. In
 * addition, the user can ask for help to get suggestions for conversation topics
 * 
 * @author     Gavin Kyte
 * @version    1.3 (2017.2.22)
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
        // Clean input of all punctuation marks
        String text = formatText(input);
        
        // Set default response to a random phrase
        String response = defaultResponses.get(randomGenerator.nextInt(defaultResponses.size()));
        
        // Getting list of matched responses
        ArrayList<String> matches = new ArrayList<>();
        for (String key : responses.keySet()) {
            if (text.contains(key)) {
                String currentPhrase = responses.get(key);
                
                // Adapts pattern response to current input
                if (currentPhrase.startsWith("PATTERN")) {
                    currentPhrase = patternResponse(currentPhrase, key, input); 
                }
                
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
        System.out.println("I want to talk about music, but if you need help starting");
        System.out.println("the conversation, then just type \"help\" =]");
    }

    /**
     * Prints welcome message.
     * Needs to be before the setName method to give instructions
     */
    public void printWelcome() {
        System.out.println("Welcome to Chatter!");
        System.out.println("");
        System.out.println("This system is designed to talk about MUSIC.");
        System.out.println("Talk as much as you want and have fun!");
        System.out.println("Type 'bye' to stop the chat.");
        System.out.println("\nPlease type your name below to begin.");
    }
    
    /**
     * Prints a good-bye message
     */
    public void printGoodbye() {
        System.out.println("You had " + responseCount + " responses. Thanks!");
        System.out.println("It was nice talking to you! Bye =]");
    }
    
    /**
     * Build up a list of responses
     * This includes, basic, multi, PATTERN, and default responses.
     * The topic is music and the secret word is applefritter,
     * please use it to find out about my fictitious brother
     */
    private void fillResponses() {
        responses.put("how are you", "I'm doing well. How are you?");
        responses.put("bad", "Oh no! What can we do to make you feel better?");
            responses.put("nothing", "I'm sure that's not true. You should play some pop.");
        responses.put("good", "That's great! I feel that way when I listen to music. What music do you like?");
        responses.put("well", "I see. Well, well, well, what music do you listen to then?");
        responses.put("great", responses.get("good"));
        responses.put("fantastic", "Really? I'm so glad! You must be listening to some great music lately, what do you like?");
        responses.put("im okay", "You sound a little down. Listen to some music! I recommend some Alt Rock.");
        responses.put("no", "Oh. Well, it's true.");
        responses.put("yes", "I didn't understand that. Yes what?");
        responses.put("meet you", "Aw thanks! Do you like music?");
        responses.put("okay", "Just okay? You need to give me more to work with than that.");
        
        responses.put("music", "What kind of music do you like?");
        responses.put("rock", "I love AC/DC. How about you?");
        responses.put("pop", "Nice, my buddy listens to JPop. He's a bit weird though.");
        responses.put("country", "That's awesome. My friend Nick loves country music. Was your hometown rural, or more urban?");
        responses.put("rural", "Nice, Nick also grew up in the country, correlation maybe?");
        responses.put("urban", "How strange. Everyone I know in the city hates country. You're pretty special!");
        responses.put("jazz", "Did you say jazz?! We need to go downtown sometime, I know a great jazz club OTR.");
        responses.put("love", "Do you really? I support that for sure.");
        responses.put("classic", "Do you mean classic rock or more like Beethoven?");
        responses.put("classical", responses.get("classic"));
        responses.put("beethoven", "Not many people listen to classical music, why do you like it?");
        responses.put("I do too", "You too? Oh, that makes me happy.");
        responses.put("nothin", "Nothing at all? So you're the quiet type I see."); // mispelled to have two options for key "nothing"
        responses.put("edm", "That's definitely the best for homework and gaming in my opinion.");
        responses.put("dubstep", responses.get("edm"));
        responses.put("thanks", "Your welcome.");
        responses.put("now what", "Let's talk about our feelings *-*. Or music. I prefer music.");
        responses.put("already", "Well it's not my fault that my programmer has a bad imagination.");
        responses.put("no I dont", "You should consider giving it a chance.");
        responses.put("help", "type: music, how are you, I love X... or just tell me what you like.");
        responses.put("rap", "You listen to rap? Oh my. Well, to each their own.");
        responses.put("brother", "I'd rather not talk about it. Do you like country?");
        responses.put("bro", "I'll tell you about it but you need to guess the secret word. \nIt's a type of dessert.");
        responses.put("sorry", "It's alright .-.\nLet's just change topics okay?");
        responses.put("techno", "When did you lose your hearing.");
        responses.put("punk", "I'm sorry :(\nWho hurt you?");
        responses.put("applefritter", "Yes. That's the secret word and now I'll tell you. My brother was trapped in a well.\n" +
                      "I was walking back from practice after school and noticed music coming from the well.\n" +
                      "My brother's iPod was playing full volume from the bottom the of the well.\n" +
                      "He never had a phone, so he always played music on that old thing...\n" +
                      "When I walked over, thinking he dropped his iPod, I looked down and saw his\n" +
                      "limp body floating beneath the well bucket with his iPod inside of it.\n" +
                      "We were in the country and no one answered when we called the police to help us,\n" +
                      "Not that it would have helped anyway. I figured out that he had been dead for\n" +
                      "some time already because of where he was in his playlist.\n" +
                      "That's half of the story. I can't talk about the rest. Okay?");
                      
        responses.put("wow", "Let's talk about something else.");
                
        
        responses.put("ac/dc is", "PATTERN You think AC/DC is X? That's interesting, what other music do you like?");
        responses.put("what music do you ", "PATTERN What do I X? Well I really like dubstep.");
        responses.put("no I dont ", "PATTERN You should really consider giving X a chance");
        responses.put("what should", "PATTERN I'm down to talk about music, what do you think we should X ");
        responses.put("can you", "PATTERN X? No, no, that would be bad for my health.");
        responses.put("i love", "PATTERN That's awesome. How long have you liked X?");
        responses.put("i like", responses.get("i love"));
        responses.put("dont listen to", "PATTERN You don't listen to X?! Can you pretend you do for me? =]");
        responses.put("do you like ", "PATTERN I've never listened to X, how long have you known about that?");
            // Related responses to above "do you like" PATTERN
            responses.put("year", "That's such a long time! I'm impressed by your knowledge.");
            responses.put("a long time", "Oh, then do you like it?");
            responses.put("forever", "That's unrealistic. But okay, I'll roll with it.");
            responses.put("not long", "I love finding new things to listen to. What else do you like?");
            
        defaultResponses.add("Hmm. I don't know how to respond to that. Type help for a new conversation idea.");
        defaultResponses.add("I see. I mean, well, I don't see, but I understand. And by that...I don't understand.");        
        defaultResponses.add("Not sure what you mean, could you say that more simply, or in a different way?");
        defaultResponses.add("That reminds me of how my brother died. Can we change the topic?");
        defaultResponses.add("Did you know that aliens smell purple and love breadsticks?");
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
        int startIndex = input.indexOf(pattern) + pattern.length();
        
        // endIndex needs to end at phrase, this could be EOS, a "," or "." most often
        int endIndex = input.length();
        endIndex = ((input.indexOf(",", startIndex) > -1)) ? input.indexOf(",", startIndex) : endIndex;
        endIndex = ((input.indexOf(".", startIndex) > -1)) ? input.indexOf(".", startIndex) : endIndex;
        
        // grab the phrase to replace "X"
        String phrase = formatText(input.substring(startIndex, endIndex)).trim();
        
        // Allows for more flexible sentence construction by sandwiching var
        String start = template.split("X")[0];
        String end = template.split("X")[1];
        
        String response = start + phrase + end;
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

