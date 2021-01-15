import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.Timer;
import java.util.TimerTask;

public class WordFind extends FlexiblePictureExplorer implements ImageObserver {
	private final String path = "./wordfind/";
	private Board board;
	private Picture disp;
	private int count; //# of seconds
	private Timer timer; //timer used for entire class
	private int infoCol;
	private int infoRow;
	private int buttonCol;
	private int buttonRow;
	private boolean menu1 = true;
	private boolean menu2 = false;
	private boolean game = false;
	private boolean end = false;
	private int maxLength;
	private double range; //higher range = worse computer
	private final static int height = 720;
	private final static int width = 1280;
	private int size;
	public WordFind() {
		super(new Picture(height, width));		
		displayMenu1();
	}
	private void displayMenu1() {
		count = 60; 
		end = false;
		game = false;
		menu1 = true;
		disp = new Picture(path + "background.jpg");
		Graphics2D graphics = disp.createGraphics();
		graphics.setColor(Color.BLACK);
		Picture title = new Picture(path + "title.png");
		graphics.drawImage(title.getBufferedImage(), 
		(width - 850) / 2, 50, this);
		graphics.setFont(new Font("Times", Font.BOLD, 30));
		Picture back = new Picture(path + "back_wide.png");
		graphics.drawImage(back.getBufferedImage(), 
		(width - 350) / 2, 170, this);
		graphics.drawString("Select Board Size", (width - 
		graphics.getFontMetrics().stringWidth("Select Board Size")) / 2, 200);
		Picture button = new Picture(path + "small_button.png");
		graphics.drawImage(button.getBufferedImage(), 
		(width - 200) / 2, 250, this);
		graphics.drawImage(button.getBufferedImage(), 
		(width - 200) / 2, 350, this);
		graphics.drawImage(button.getBufferedImage(), 
		(width - 200) / 2, 450, this);
		graphics.setFont(new Font("Times", Font.BOLD, 30));
		graphics.drawString("4 x 4", (width - 
		graphics.getFontMetrics().stringWidth("4 x 4")) / 2, 285);
		graphics.drawString("5 x 5", (width - 
		graphics.getFontMetrics().stringWidth("5 x 5")) / 2, 385);
		graphics.drawString("6 x 6", (width - 
		graphics.getFontMetrics().stringWidth("6 x 6")) / 2, 485);
		setImage(disp);
		setTitle("Word Find");
	}
	private void displayMenu2() {
		board = new Board(size);
		menu1 = false;
		menu2 = true;
		disp = new Picture(path + "background.jpg");
		Graphics2D graphics = disp.createGraphics();
		setImage(disp);
		graphics.setColor(Color.BLACK);
		Picture title = new Picture(path + "title.png");
		graphics.drawImage(title.getBufferedImage(), 
		(width - 850) / 2, 50, this);
		graphics.setFont(new Font("Times", Font.BOLD, 30));
		Picture back = new Picture(path + "back_wide.png");
		graphics.drawImage(back.getBufferedImage(), 
		(width - 350) / 2, 170, this);
		graphics.drawString("Select Difficulty", (width - 
		graphics.getFontMetrics().stringWidth("Select Difficulty")) / 2, 200);
		Picture button = new Picture(path + "small_button.png");
		graphics.drawImage(button.getBufferedImage(), 
		(width - 200) / 2, 250, this);
		graphics.drawImage(button.getBufferedImage(), 
		(width - 200) / 2, 350, this);
		graphics.drawImage(button.getBufferedImage(), 
		(width - 200) / 2, 450, this);
		graphics.setFont(new Font("Times", Font.BOLD, 30));
		graphics.drawString("Easy", (width - 
		graphics.getFontMetrics().stringWidth("Easy")) / 2, 285);
		graphics.drawString("Medium", (width - 
		graphics.getFontMetrics().stringWidth("Medium")) / 2, 385);
		graphics.drawString("Hard", (width - 
		graphics.getFontMetrics().stringWidth("Hard")) / 2, 485);
		setImage(disp);
		setTitle("Word Find");
	}
	private void displayMain() {
		disp = new Picture(path + "background.jpg");
		Graphics2D graphics = disp.createGraphics();
		reset();
		update();
		game = true;
		menu2 = false;
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				count--;
				update();
				if (count <= 0) {
					game = false;
					displayEnd();
					timer.cancel();
				}
			}
		}, 100, 1000);
		graphics.setColor(Color.black);
		graphics.drawString("0", infoCol, infoRow + 120);
		graphics.setColor(Color.white);
		graphics.setFont(new Font("Times", Font.BOLD, 26));
		graphics.drawString("Seconds Left", infoCol, 70);
		graphics.drawRect(infoCol, infoRow + 20, 250, 30);
		graphics.drawString("Current Word", infoCol, 150);
		graphics.drawRect(infoCol, infoRow + 100, 250, 30);
		graphics.drawString("Points", infoCol, 230);
		graphics.drawRect(infoCol, infoRow + 180, 250, 30);
		board.setCheck(buttonRow, buttonCol);
		Picture button = new Picture(path + "button.png");
		graphics.drawImage(button.getBufferedImage(), 
		buttonCol, buttonRow, this);
		graphics.setColor(Color.BLACK);
		graphics.drawString("Check", buttonCol + 60, buttonRow + 60);
		board.setClear(buttonRow + 200, buttonCol);
		graphics.drawImage(button.getBufferedImage(), 
		buttonCol, buttonRow + 200, this);
		graphics.setColor(Color.BLACK);
		graphics.drawString("Clear", buttonCol + 60, buttonRow + 260);
		Picture menu = new Picture(path + "small_button.png");
		graphics.drawImage(menu.getBufferedImage(), 
		buttonCol, buttonRow + 325, this);
		graphics.setColor(Color.BLACK);
		graphics.drawString("Home", buttonCol + 60, buttonRow + 355);
		graphics.setFont(new Font("Times", Font.PLAIN, 12));
		setImage(disp);
		setTitle("Word Find");
	}
	private void displayEnd() {
		game = false;
		end = true;
		disp = new Picture(path + "background.jpg");
		Graphics2D graphics = disp.createGraphics();
		setImage(disp);
		Computer computer = new Computer(board, maxLength, range);
		graphics.setColor(Color.white);
		graphics.setFont(new Font("Times", Font.BOLD, 75));
		if (board.getPoints() == computer.getPoints()) {
			graphics.drawString("You tied!", (width - 
			graphics.getFontMetrics().stringWidth("You tied!")) / 2, 200);
		} else if (board.getPoints() > computer.getPoints()) {
			graphics.drawString("You won!", (width - 
			graphics.getFontMetrics().stringWidth("You won!")) / 2, 200);
		} else {
			graphics.drawString("You lost!", (width - 
			graphics.getFontMetrics().stringWidth("You lost!")) / 2, 200);
		}
		graphics.setFont(new Font("Times", Font.BOLD, 30));
		graphics.drawString("Player Score", (width - 
		graphics.getFontMetrics().stringWidth("Player Score")) / 2 - 190, 275);
		graphics.drawString("Computer Score", (width - 
		graphics.getFontMetrics().stringWidth("Computer Score")) / 2 + 190, 
		275);
		graphics.drawString(Integer.toString(board.getPoints()), (width - 
		graphics.getFontMetrics().stringWidth(Integer.toString(
		board.getPoints()))) / 2 - 190, 350);
		graphics.drawString(Integer.toString(computer.getPoints()), (width - 
		graphics.getFontMetrics().stringWidth(Integer.toString(
		computer.getPoints()))) / 2 + 190, 350);
		Picture button = new Picture(path + "small_button.png");
		graphics.drawImage(button.getBufferedImage(), 
		(width - 200) / 2, 400, this);
		graphics.setColor(Color.BLACK);
		graphics.drawString("Play Again", (width - 
		graphics.getFontMetrics().stringWidth("Play Again")) / 2, 435);
		setImage(disp);
		setTitle("Word Find");
	}
	private void reset() {
		Graphics2D graphics = disp.createGraphics();
		int row = 70;
		int col = 44;
		for (int i = 0; i < board.getSize(); i++) {
			for (int j = 0; j < board.getSize(); j++) {
				Picture pict;
				pict = new Picture(path + board.getLetter(i, j).toUpperCase() + ".jpg");
				graphics.drawImage(pict.getBufferedImage(), 
				row, col, this);
				row += 100;
			}
			row = 70;
			col += 100;
		}
		buttonCol = col + 60;
		buttonRow = row;
		infoCol = buttonCol + 230;
		infoRow = row;
	}
	public void drawConnectingLine(String in) {
		Graphics2D graphics = disp.createGraphics();
		int row = Character.getNumericValue(in.charAt(0));
		int col = Character.getNumericValue(in.charAt(1));
		String file = in.substring(2);
		int pixRow, pixCol;
		if (file.equals("horizontal")) {
			pixRow = row * 100 + 44;
			pixCol = col * 100 + 70 + 50;
		} else if (file.equals("vertical")) {
			pixRow = row * 100 + 44 + 50;
			pixCol = col * 100 + 70;
		} else if (file.equals("diag_right")) {
			pixRow = row * 100 + 44 + 50;
			pixCol = col * 100 + 70 - 50;
		} else if (file.equals("dot")) {
			pixRow = row * 100 + 44;
			pixCol = col * 100 + 70;
		} else {
			pixRow = row * 100 + 44 + 50;
			pixCol = col * 100 + 70 + 50;
		}
		Picture pict1 = new Picture(path + file + ".png");
		graphics.drawImage(pict1.getBufferedImage(), pixCol, pixRow, this);
	}
	private void drawBox(Color color) {
		Graphics2D graphics = disp.createGraphics();
		graphics.setColor(color);
		graphics.fillRect(buttonCol, buttonRow + 120, 200, 50);
	}
	private void correctBox(String in) {
		Graphics2D graphics = disp.createGraphics();
		drawBox(Color.GREEN);
		graphics.setColor(Color.BLACK);
		String word = in.substring(1);
		String message = "+" + Integer.toString(word.length()) + " " + word;
		graphics.setFont(new Font("Times", Font.BOLD, 16));
		graphics.drawString(message, buttonCol + 20, buttonRow + 150);
		graphics.setFont(new Font("Times", Font.PLAIN, 12));
		reset();
	}
	private void incorrectBox(String in) {
		Graphics2D graphics = disp.createGraphics();
		drawBox(Color.RED);
		graphics.setColor(Color.BLACK);
		graphics.setFont(new Font("Times", Font.BOLD, 16));
		graphics.drawString(in, buttonCol + 20, buttonRow + 150);
		graphics.setFont(new Font("Times", Font.PLAIN, 12));
		reset();
	}
	private void gameClick(Pixel pix) {
		if (pix.getX() >= buttonCol && pix.getX() <= buttonCol + 200 &&
			pix.getY() >= buttonRow + 325 && pix.getY() <= buttonRow + 375) {
			timer.cancel();
			displayMenu1();
		} else {
			String ret = board.click(pix.getX(), pix.getY());
			if (ret.equals("reset")) {
				reset();
			} else if (ret.startsWith("c")) {
				correctBox(ret);
			} else if (ret.startsWith("i")) {
				incorrectBox("Invalid"); 
			} else if (ret.startsWith("a")) {
				incorrectBox("Duplicate");
			} else if (!ret.isEmpty()) {
				drawConnectingLine(ret);
			}
			update();
		}
	}
	private void menu1Click(Pixel pix) {
		if (pix.getX() >= (width - 250) / 2 && 
			pix.getX() <= (width - 250) / 2 + 250) {
			if (pix.getY() >= 250 && pix.getY() <= 300) {
				size = 4;
				displayMenu2();
			} else if (pix.getY() >= 350 && pix.getY() <= 400) {
				size = 5;
				displayMenu2();
			} else if (pix.getY() >= 450 && pix.getY() <= 500) {
				size = 6;
				displayMenu2();
			}
		}
	}
	
	private void menu2Click(Pixel pix) {
		if (pix.getX() >= (width - 250) / 2 && 
				pix.getX() <= (width - 250) / 2 + 250) {
				if (pix.getY() >= 250 && pix.getY() <= 300) {
					maxLength = 4;
					range = 3;
					displayMain();
				} else if (pix.getY() >= 350 && pix.getY() <= 400) {
					maxLength = 6;
					range = 2;
					displayMain();
				} else if (pix.getY() >= 450 && pix.getY() <= 500) {
					maxLength = 10;
					range = 1.25;
					displayMain();
				}
			}
	}
	private void endClick(Pixel pix) {
		if (pix.getX() >= (width - 200) / 2 && 
			pix.getX() <= (width - 250) / 2 + 200 && 
			pix.getY() >= 400 && pix.getY() <= 450) {
			displayMenu1();
		}
	}
	@Override
	public void mouseClickedAction(DigitalPicture pict, Pixel pix) {
		if (game) {
			gameClick(pix);
		} else if (menu1) {
			menu1Click(pix);
		} else if (menu2) {
			menu2Click(pix);
		} else if (end) {
			endClick(pix);
		}
		
	}
	private void update() {
		Graphics2D graphics = disp.createGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(infoCol + 1, infoRow + 21, 248, 28);
		graphics.fillRect(infoCol + 1, infoRow + 101, 248, 28);
		graphics.fillRect(infoCol + 1, infoRow + 181, 248, 28);
		graphics.setColor(Color.BLACK);
		graphics.drawString(String.valueOf(count), 
		infoCol + 10, infoRow + 40);
		graphics.drawString(board.getCurrentWord(), 
		infoCol + 10, infoRow + 120);
		graphics.drawString(Integer.toString(board.getPoints()), 
		infoCol + 10, infoRow + 200);
		setImage(disp);
		setTitle("Word Find");
	}
	@Override
	public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3, 
		int arg4, int arg5) {
		return false;
	}
	public static void main(String[] args) {
		new WordFind();
	}
}
