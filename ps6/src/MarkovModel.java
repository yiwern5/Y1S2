import java.util.*;
/**
 * This is the main class for your Markov Model.
 *
 * Assume that the text will contain ASCII characters in the range [1,255].
 * ASCII character 0 (the NULL character) will be treated as a non-character.
 *
 * Any such NULL characters in the original text should be ignored.
 */
public class MarkovModel {
	// Use this to generate random numbers as needed
	private Random generator = new Random();
	// This is a special symbol to indicate no character
	public static final char NOCHARACTER = (char) 0;
	public int order;
	public Hashtable<String, int[]> charFreq;
	public Hashtable<String, Integer> stringFreq;
	/**
	 * Constructor for MarkovModel class.
	 *
	 * @param order the number of characters to identify for the Markov Model sequence
	 * @param seed the seed used by the random number generator
	 */
	public MarkovModel(int order, long seed) {
		// Initialize your class here
		this.order = order;
		this.charFreq = new Hashtable<>();
		this.stringFreq = new Hashtable<>();
		// Initialize the random number generator
		generator.setSeed(seed);
	}
	/**
	 * Builds the Markov Model based on the specified text string.
	 */
	public void initializeText(String text) {
		// Build the Markov model here
		// add keys, which is strings of length order
		// iterate through text
		for (int i = 0; i < text.length() - this.order; i++) {
			String kString = text.substring(i, this.order + i);
			if (this.charFreq.containsKey(kString)) {
				char c = text.charAt(this.order + i);
				int ascii = (int) c;
				int[] arr = this.charFreq.get(kString);
				arr[ascii]++;
				int freq = this.stringFreq.get(kString);
				freq++;
				this.stringFreq.replace(kString, freq);
			} else {
				this.charFreq.put(kString, new int[256]);
				char c = text.charAt(this.order + i);
				int ascii = (int) c;
				int[] arr = this.charFreq.get(kString);
				arr[ascii]++;
				this.stringFreq.put(kString, 1);
			}
		}
	}
	/**
	 * Returns the number of times the specified kgram appeared in the text.
	 */
	public int getFrequency(String kgram) {
		if (kgram.length() == this.order && this.stringFreq.containsKey(kgram)) {
			return this.stringFreq.get(kgram);
		}
		return 0;
	}
	/**
	 * Returns the number of times the character c appears immediately after the specified kgram.
	 */
	public int getFrequency(String kgram, char c) {
		if (kgram.length() == this.order && this.stringFreq.containsKey(kgram)) {
			int ascii = (int) c;
			int[] arr = this.charFreq.get(kgram);
			return arr[ascii];
		}
		return 0;
	}
	/**
	 * Generates the next character from the Markov Model.
	 * Return NOCHARACTER if the kgram is not in the table, or if there is no
	 * valid character following the kgram.
	 */
	public char nextCharacter(String kgram) {
		// See the problem set description for details
		// on how to make the random selection.
		if (this.charFreq.containsKey(kgram)) {
			int[] arr = this.charFreq.get(kgram);
			ArrayList<Character> freqArr = new ArrayList<>();
			for (int i = 0; i < arr.length; i++) {
				if (arr[i] != 0) {
					int freq = arr[i];
					for (int j = 0; j < freq; j++) {
						char c = (char) i;
						freqArr.add(c);
					}
				}
			}
			int randomNum = generator.nextInt(freqArr.size());
			return freqArr.get(randomNum);
		}
		return NOCHARACTER;
	}
}