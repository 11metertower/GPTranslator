package TranslatingWordProcessor;


public class CountWord {
	
	public int getWordCount(String text)
	{
		String[] words = text.split(" ");
		return words.length;
	}
}
