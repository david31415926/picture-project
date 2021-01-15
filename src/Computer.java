import java.util.HashSet;

public class Computer {
	private Board board;
	private final Dictionary dictionary = 
	new Dictionary("./wordfind/common_words.txt");
	private final int maxLength;
	private final double range;
	private int points = 0;
	private HashSet<String> words = new HashSet<String>();
	Computer(Board b, int length, double range) {
		this.board = b;
		this.maxLength = length;
		this.range = range;
		for (int i = 0; i < board.getSize(); i++) {
			for (int j = 0; j < board.getSize(); j++) {
				board.resetChosen();
				recurse(i, j, "");
			}
		}
	}
	private void recurse(int row, int col, String currentWord) {
		if (currentWord.length() < maxLength &&
			!words.contains(currentWord + board.getLetter(row, col)) &&
			(int) (Math.random() * range) == 0) {
			String newWord = currentWord + board.getLetter(row, col);
			if (dictionary.contains(newWord)) {
				words.add(newWord);
				points += newWord.length();
			}
			for (int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j++) {
					if (!(i == 0 && j == 0)) {
						if (row + i >= 0 && 
							row + i < board.getSize() &&
							col + j >= 0 && 
							col + j < board.getSize()  &&
							board.isChosen(row + i, col + j) 
							== false) {
							board.setChosen(row + i, col + j);
							recurse(row + i, col + j, newWord);
							board.setNotChosen(row + i, col + j);	
						}
					}
				}
			}
		}
		return;
	}
	public int getPoints() {
		return points;
	}
}
