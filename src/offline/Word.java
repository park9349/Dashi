package offline;

public class Word implements Comparable<Word> {
	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public double getVal() {
		return val;
	}

	public void setVal(double val) {
		this.val = val;
	}

	String word;
	double val;

	Word(String word, double val) {
		this.word = word;
		this.val = val;
	}

	@Override
	public int compareTo(Word another) {
		if (this.val == another.val) {
			return 0;
		}
		return this.val < another.val ? 1 : -1;
	}
}
