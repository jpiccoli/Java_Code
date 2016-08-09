package textgen;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		if(sourceText.isEmpty())
		{
			return;
		}
		
        String[] tokens = sourceText.split("[\\s]+");

		if( wordList.size() == 0 )
		{
			ListNode listNode = new ListNode(tokens[0]);
			listNode.addNextWord(tokens[1]);
			wordList.add(listNode);			
		}
		
		for( int i = 1; i < tokens.length - 1; ++i )
		{
			// Check if node exists in list
			boolean found = false;

			for( int j = 0; j < wordList.size(); ++j )
			{
				if(wordList.get(j).getWord().equals(tokens[i]))
				{
					// Update existing node
					int index = wordList.indexOf(wordList.get(j));
					ListNode listNode = wordList.get(j);
					listNode.addNextWord(tokens[i+1]);
					wordList.set(index, listNode);
					found = true;
					break;
				}
			}	
			
			if( !found )
			{
				// Add new item to list
				ListNode listNode = new ListNode(tokens[i]);
				listNode.addNextWord(tokens[i+1]);
				wordList.add(listNode);
			}
		}
		
		// Add node for last word with nothing following it
		ListNode listNode = new ListNode(tokens[tokens.length - 1]);
		listNode.addNextWord(tokens[0]);
		wordList.add(listNode);
	}
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
	    // TODO: Implement this method
		if(wordList.isEmpty())
		{
			throw new IndexOutOfBoundsException();
		}
		
		String currWord = wordList.get(0).getWord();
		String output = "";
		output += currWord;
		int wordCount = 0;
		while( wordCount < numWords )
		{
			ListNode currWordNode = null;		
			for( int j = 0; j < wordList.size(); ++j )
			{
				if(wordList.get(j).getWord().equals(currWord))
				{
					currWordNode = wordList.get(j);
					break;
				}
			}
			
			String nextWord = currWordNode.getRandomNextWord(rnGenerator);
			if( nextWord == null )
			{
				return output;
			}
			output += " " + nextWord;
			currWord = nextWord;
			wordCount++;
		}
		
		return output;
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		if( wordList.isEmpty() == false && sourceText.isEmpty() == false )
		{
			wordList.clear();
		}
		
		if( sourceText.isEmpty() == false )
		{
			train(sourceText);
		}
	}
	
	// TODO: Add any private helper methods you need here.
	private ArrayList<String> getTokens(String pattern, String text)
	{
		ArrayList<String> tokens = new ArrayList<String>();
		Pattern tokSplitter = Pattern.compile(pattern);
		Matcher m = tokSplitter.matcher(text);
		
		while (m.find()) {
			tokens.add(m.group());
		}
		
		return tokens;
	}	
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
		// TODO: Implement this method
	    // The random number generator should be passed from 
	    // the MarkovTextGeneratorLoL class
		//System.out.println( "Next words size = " +  nextWords.size());
		if( nextWords.size() == 0 )
		{
			return null;
		}
		int randomInt = generator.nextInt(nextWords.size());
		//System.out.println( "Random int = " + randomInt);
	    return nextWords.get(randomInt);
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}


