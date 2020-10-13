package lab4;

class LongestChain {
	private Queue q; // kö som anänds i breddenförstsökningen
	private String goalWord; // slutord i breddenförstsökningen
	private int wordLength;
	private final char[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
			'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'å', 'ä', 'ö', 'é' };
	private int alphabetLength = alphabet.length;

	private String test = "^^^^";
	
	public LongestChain(int wordLength) {
		this.wordLength = wordLength;
		q = new Queue();
	}

	// IsGoal kollar om w är slutordet.
	private boolean isGoal(String w) {
		return w.equals(goalWord);
	}

	// makeChildren skapar alla ord som skiljer på en bokstav från x.
	// Om det är första gången i sökningen som ordet skapas så läggs det
	// in i kön q.
	private WordRec makeChildren(WordRec x) {
		for (int i = 0; i < wordLength; i++) {
			for (int c = 0; c < alphabetLength; c++) {
				if (alphabet[c] != x.getWord().charAt(i)) {
					String res = WordList
							.contains(x.getWord().substring(0, i) + alphabet[c] + x.getWord().substring(i + 1));
					if (res != null && WordList.markAsUsedIfUnused(res)) {
						WordRec wr = new WordRec(res, x);
						y = wr;
						if (isGoal(res)) {
							return wr;
						}
						q.put(wr);
					}
				}
			}
		}
		return null;
	}

	// BreadthFirst utför en breddenförstsökning från startWord för att
	// hitta kortaste ägen till endWord. Den kortaste ägen returneras
	// som en kedja av ordposter (WordRec).
	// Om det inte finns något sätt att komma till endWord returneras null.
	public WordRec breadthFirst(String startWord, String endWord) {
		if(startWord.equals(endWord)) return null;
		WordRec start = new WordRec(startWord, null);
		WordList.markAsUsedIfUnused(startWord);
		goalWord = endWord;
		q.empty();
		q.put(start);		
		try {
			while (true) {
				WordRec wr = makeChildren((WordRec) q.get());
				if (wr != null) {
					return wr;
				}
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	private WordRec y = null;
	
	// CheckAllStartWords hittar den längsta kortaste ägen från något ord
	// till endWord. Den längsta ägen skrivs ut.
	public void checkAllStartWords(String endWord) {
		y = new WordRec(endWord, null);
		int maxChainLength = 0;
		breadthFirst(endWord, test);
		maxChainLength = y.chainLength();
		System.out.println(endWord + ": " + maxChainLength + " ord");
		if (y != null) {
			y.printChain();
		}
	}
}
