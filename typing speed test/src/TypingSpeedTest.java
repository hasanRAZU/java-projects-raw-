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
            "I've learned that people will forget what you said, people will forget what you did, but people will never forget how you made them feel. -Maya Angelou,",


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
            // Set a random sentence
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

            int wordCount = testSentence.split(" ").length;
            double wpm = (wordCount / timeTaken) * 60;

            int correctChars = 0;
            int minLen = Math.min(testSentence.length(), typed.length());

            for (int i = 0; i < minLen; i++) {
                if (testSentence.charAt(i) == typed.charAt(i)) {
                    correctChars++;
                }
            }

            double accuracy = ((double) correctChars / testSentence.length()) * 100;

            // Results
            System.out.println("\nResult of Round " + r + ":");
            System.out.printf("Time Taken: %.2f seconds\n", timeTaken);
            System.out.printf("WPM: %.2f\n", wpm);
            System.out.printf("Accuracy: %.2f%%\n", accuracy);

            // Store totals
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
