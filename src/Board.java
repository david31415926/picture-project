import java.util.HashSet;

public class Board {
	private String[][] letters;
	private boolean[][] chosen;
	private HashSet<String> words = new HashSet<String>();
	private Dictionary dictionary = new Dictionary("./wordfind/words.txt");
	private int clearCol = 0;
	private int clearRow = 0;
	private int checkCol = 0;
	private int checkRow = 0;
	private int currentCol = -1;
	private int currentRow = -1;
	private int points = 0;
	private final int size;	
	private String currentWord = "";
	Board(int size) {
		this.size = size;
		generateBoard();
	}
	private void generateBoard() {
		letters = new String[size][size];
		chosen = new boolean[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				int random = (int) (Math.random() * 26 + 97);
				char letter = (char) random;
				letters[i][j] = Character.toString(letter);
			}
		}
	}
	boolean isAdjacent(int boxRow, int boxCol) {
		boolean ret = false;
		if ((currentCol == -1 && currentRow == -1) ||
			(Math.abs(currentCol - boxCol) <= 1 && 
			Math.abs(currentRow - boxRow) <= 1 &&
			!(boxRow == currentRow && boxCol == currentCol))) {
			ret = true;
		}
		return ret;
	}
	public void resetChosen() {
		for (int i = 0; i < chosen.length; i++) {
			for (int j = 0; j < chosen[0].length; j++) {
				chosen[i][j] = false;
			}
		}
		currentWord = "";
		currentCol = -1;
		currentRow = -1;
	}
	private String getDirection(int boxRow, int boxCol) {
		String ret = "";
		if (currentRow != -1 && currentCol != -1) {
			if (currentRow - 1 == boxRow && 
				currentCol == boxCol) {
				ret += Integer.toString(boxRow) + 
				Integer.toString(boxCol);
				ret += "vertical";
			} else if (currentRow - 1 == boxRow && 
				currentCol + 1 == boxCol) {
				ret += Integer.toString(boxRow) + 
				Integer.toString(boxCol);
				ret += "diag_right";
			} else if (currentRow == boxRow && 
				currentCol + 1 == boxCol) {
				ret += Integer.toString(currentRow) + 
				Integer.toString(currentCol);
				ret += "horizontal";
			} else if (currentRow + 1 == boxRow && 
				currentCol + 1 == boxCol) {
				ret += Integer.toString(currentRow) + 
				Integer.toString(currentCol);
				ret += "diag_left";
			} else if (currentRow + 1 == boxRow && 
				currentCol == boxCol) {
				ret += Integer.toString(currentRow) + 
				Integer.toString(currentCol);
				ret += "vertical";
			} else if (currentRow + 1 == boxRow && 
				currentCol - 1 == boxCol) {
				ret += Integer.toString(currentRow) + 
				Integer.toString(currentCol);
				ret += "diag_right";
			} else if (currentRow == boxRow && 
				currentCol - 1 == boxCol) {
				ret += Integer.toString(boxRow) + 
				Integer.toString(boxCol);
				ret += "horizontal";
			} else {
				ret += Integer.toString(boxRow) + 
				Integer.toString(boxCol);
				ret += "diag_left";
			}
		} else {
			ret = Integer.toString(boxRow) + 
			Integer.toString(boxCol);
			ret += "dot";
		}
		return ret;
	}
	public String click(int X, int Y) {
		int boxRow = (Y - 44) / 100;
		int boxCol = (X - 70) / 100;
		String ret = "";
		if (boxRow < size && boxCol < size &&
			(Y - 44) % 100 != 0 && (X - 70) % 100 != 0 &&
			this.isAdjacent(boxRow, boxCol) && 
			this.isChosen(boxRow, boxCol) == false) {
			currentWord += letters[boxRow][boxCol];
			chosen[boxRow][boxCol] = true;
			ret = getDirection(boxRow, boxCol);
			currentCol = boxCol;
			currentRow = boxRow;
		} else if (Y >= clearRow && Y <= clearRow + 100 &&
			X >= clearCol && X <= clearCol + 200) {
			resetChosen();
			ret = "reset";
		} else if (Y >= checkRow && Y <= checkRow + 100 &&
			X >= checkCol && X <= checkCol + 200 &&
			currentCol != -1) {
			if (dictionary.contains(currentWord) && 
				!(words.contains(currentWord))) {
				points += currentWord.length();
				words.add(currentWord);
				ret = "c" + currentWord;
				resetChosen();
			} else if (dictionary.contains(currentWord) && 
				words.contains(currentWord)) {
				resetChosen();
				ret = "a";
			} else {
				resetChosen();
				ret = "i";
			}
		}
		return ret;
	}
	public void setCheck(int row, int col) {
		checkRow = row;
		checkCol = col;
	}
	public void setClear(int row, int col) {
		clearRow = row;
		clearCol = col;
	}
	public void setChosen(int row, int col) {
		chosen[row][col] = true;
	}
	public void setNotChosen(int row, int col) {
		chosen[row][col] = false;
	}
	public int getSize() {
		return size;
	}
	public String getLetter(int row, int col) {
		return letters[row][col];
	}
	public boolean isChosen(int row, int col) {
		return chosen[row][col];
	}
	public String getCurrentWord() {
		return currentWord;
	}
	public int getPoints() {
		return points;
	}	
}
