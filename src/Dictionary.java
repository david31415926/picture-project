import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class Dictionary {
	private HashSet<String> dict = new HashSet<String>();
	public Dictionary(String file) {
		File words = new File(file);
		Scanner scanner = null;
		try {
			scanner = new Scanner(new FileInputStream(words));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (scanner.hasNextLine()) {
			String word = scanner.nextLine();
			if (word.length() > 1 || word.equals("a") || word.equals("i")) {
				dict.add(word);
			}
		}
	}
	public boolean contains(String in) {
		return dict.contains(in);
	}
}
