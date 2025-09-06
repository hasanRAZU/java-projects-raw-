import java.util.Scanner;
import java.util.Random;

public class TypingSpeedTest {

    private static final String[] sentences = {

            "I'm selfish, impatient and a little insecure. I make mistakes, I am out of control and at times hard to handle. But if you can't handle me at my worst, then you sure as hell don't deserve me at my best. - Marilyn Monroe",
            "You've gotta dance like there's nobody watching, Love like you'll never be hurt, Sing like there's nobody listening, And live like it's heaven on earth. - William W. Purkey",
            "If you want to know what a man's like, take a good look at how he treats his inferiors, not his equals. - J.K. Rowling, Harry Potter and the Goblet of Fire",
            "Be who you are and say what you feel, because those who mind don't matter, and those who matter don't mind. - Bernard M. Baruch",
            "You've gotta dance like there's nobody watching, Love like you'll never be hurt, Sing like there's nobody listening, And live like it's heaven on earth. - William W. Purkey",
            "If you want to know what a man's like, take a good look at how he treats his inferiors, not his equals. -J.K. Rowling, Harry Potter and the Goblet of Fire",
            "Don’t walk in front of me, I may not follow. Don’t walk behind me, I may not lead. Walk beside me, just be my friend- Albert Camus",
            "I've learned that people will forget what you said, people will forget what you did, but people will never forget how you made them feel. -Maya Angelou",
            "Darkness cannot drive out darkness: only light can do that. Hate cannot drive out hate: only love can do that.-Martin Luther King Jr., A Testament of Hope: The Essential Writings",
            "I am so clever that sometimes I don't understand a single word of what I am saying.-Oscar Wilde, The Happy Prince and Other Stories",
            "Here's to the crazy ones. The misfits. The rebels. The troublemakers. The round pegs in the square holes. The ones who see things differently. They're not fond of rules. And they have no respect for the status quo. You can quote them, disagree with them, glorify or vilify them. About the only thing you can't do is ignore them. Because they change things. They push the human race forward. And while some may see them as the crazy ones, we see genius. Because the people who are crazy enough to think they can change the world, are the ones who do. -Steve Jobs",
            "I believe that everything happens for a reason. People change so that you can learn to let go, things go wrong so that you appreciate them when they're right, you believe lies so you eventually learn to trust no one but yourself, and sometimes good things fall apart so better things can fall together. -Marilyn Monroe",
            "Twenty years from now you will be more disappointed by the things that you didn't do than by the ones you did do. So throw off the bowlines. Sail away from the safe harbor. Catch the trade winds in your sails. Explore. Dream. Discover. -H. Jackson Brown Jr., P.S. I Love You",



    };

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        System.out.println("==== Test Your Typing Speed ====");
        System.out.println("How many rounds do you want to play? (example: 5)");
        int rounds = sc.nextInt();
        sc.nextLine(); // clear buffer

        double totalWPM = 0;
        double totalAccuracy = 0;

        for (int r = 1; r <= rounds; r++) {
            // Random sentence selection for testing
            String testSentence = sentences[rand.nextInt(sentences.length)];

            System.out.println("\nRound " + r + ":");
            System.out.println("Type the following sentence:\n");
            System.out.println("\"" + testSentence + "\"\n");
            System.out.println("Press Enter when you are ready...");
            sc.nextLine();

            long startTime = System.currentTimeMillis();

            String typed = sc.nextLine();

            long endTime = System.currentTimeMillis();

            double timeTaken = (endTime - startTime) / 1000.0;

            int wordCount = testSentence.split("\\s+").length;
            double wpm = (wordCount / timeTaken) * 60;

            // Word-based Accuracy
            String[] originalWords = testSentence.split("\\s+");
            String[] typedWords = typed.split("\\s+");

            int correctWords = 0;
            int minLen = Math.min(originalWords.length, typedWords.length);

            for (int i = 0; i < minLen; i++) {
                if (originalWords[i].equals(typedWords[i])) {
                    correctWords++;
                }
            }

            double accuracy = ((double) correctWords / originalWords.length) * 100;

            // Results
            System.out.println("\nResult of Round " + r + ":");
            System.out.printf("Time Taken: %.2f seconds\n", timeTaken);
            System.out.printf("WPM: %.2f\n", wpm);
            System.out.printf("Accuracy: %.2f%%\n", accuracy);

            // Totals
            totalWPM += wpm;
            totalAccuracy += accuracy;
        }

        // Final result
        System.out.println("\n==== Final Score ====");
        System.out.printf("Average WPM: %.2f\n", totalWPM / rounds);
        System.out.printf("Average Accuracy: %.2f%%\n", totalAccuracy / rounds);

        sc.close();
    }
}
