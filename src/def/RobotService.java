package def;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import static java.awt.event.KeyEvent.*;

public class RobotService
		extends Robot {

	int x, y;

	public RobotService(String filename) throws AWTException {
		GraphicsDevice monitor = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		x = monitor.getDisplayMode().getWidth();
		y = monitor.getDisplayMode().getHeight();

	}

	public RobotService(GraphicsDevice screen) throws AWTException {
		super(screen);


	}

	public RobotService() throws AWTException {
		super();
	}

	@Override
	public synchronized void mouseMove(int x, int y) {

		Point cursorPos;
		do {

			cursorPos = MouseInfo.getPointerInfo().getLocation();
			super.mouseMove(x, y);

		} while (cursorPos.x != x || cursorPos.y != y);
	}

	public synchronized void leftClick() {
		super.mousePress(InputEvent.BUTTON1_MASK);
		super.mouseRelease(InputEvent.BUTTON1_MASK);
	}

	public synchronized void doubleLeftClick() {
		leftClick();
		leftClick();
	}

//    public synchronized void mouseMove(String where) {
//
//        FloatPoint point = destinations.get(where);
//        this.mouseMove((int) (point.getX() * x), (int) (point.getY() * y));
//
//    }

//    public synchronized void mouseMoveAndSelect(String where) {
//        mouseMove(where);
//        leftClick();
//    }

	public synchronized void mouseMoveAndDoubleClick(int x,int y) {
		mouseMove(x,y);
		doubleLeftClick();
	}

	public synchronized void type(String toType) {
		String tmp = toType.toUpperCase();
		for (int op = 0; op < toType.length(); ++op) {
			if (toType.charAt(op) >= 'A' && toType.charAt(op) <= ('Z')) {
				this.typeUpperCaseLetter(tmp.charAt(op));
			} else
				this.letterClick(tmp.charAt(op));
		}
	}

	public synchronized BufferedImage createScreenCapture(){

		DisplayMode screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
		Rectangle rectangle = new Rectangle(screen.getWidth(),screen.getHeight());
		return this.createScreenCapture(rectangle);

	}

	public synchronized void keyClick(char key){
		this.keyPress(key);
		this.keyRelease(key);
	}


	public synchronized void letterClick(char letter) {
try {
	char verifiedLetter = getKeyCode(letter);

	if (verifiedLetter != letter) {
		letter = verifiedLetter;
		typeUpperCaseLetter(letter);
	} else {

		this.keyPress(letter);
		this.keyRelease(letter);
	}
	}catch (IllegalArgumentException e){
		e.printStackTrace();
		}
	}
	public synchronized void openNewDesktop(){
		this.keyPress(VK_CONTROL);
		this.keyPress(VK_WINDOWS);
		this.letterClick('D');
		this.keyRelease(VK_WINDOWS);
		this.keyRelease(VK_CONTROL);
	}
	public synchronized void closeCurrentDesktop() {
		this.keyPress(VK_CONTROL);
		this.keyPress(VK_WINDOWS);
		this.letterClick((char)(VK_F4));
		this.keyRelease(VK_WINDOWS);
		this.keyRelease(VK_CONTROL);
	}

	public synchronized void moveDesktop(char direction){
		this.keyPress(VK_CONTROL);
		this.keyPress(VK_WINDOWS);
		this.keyClick(direction);
		this.keyRelease(VK_WINDOWS);
		this.keyRelease(VK_CONTROL);
	}
	public synchronized void moveDesktopTimes(char direction,int times){
		for(;times > 0;times--)
			moveDesktop(direction);
	}

	public synchronized void centerDesktop(int toCenter){
		System.out.println();
		for(;toCenter > 0; --toCenter)
			this.moveDesktop((char)37);

		for(;toCenter < 0; ++toCenter)
			this.moveDesktop((char)39);
	}

	public synchronized void typeUpperCaseLetter(char letter) {
		this.keyPress(VK_SHIFT);
		this.letterClick(letter);
		this.keyRelease(VK_SHIFT);
	}

	public synchronized void clearTextField(int x,int y) {
		this.mouseMoveAndDoubleClick(x,y);
		this.keyClick((char) VK_BACK_SPACE);
	}

	public synchronized void fillTextField(int x,int y, String toType) {
		this.mouseMoveAndDoubleClick(x,y);
		this.type(toType);
	}

	public synchronized void clickAltKey(char val) {

		this.keyPress(VK_ALT);
		this.letterClick(getKeyCode(val));
		this.keyRelease(VK_ALT);

	}



	public char getKeyCode(char val) {
		switch (val) {
			case 126:
				return 96;
			case 33:

				return 49;
			case 64:

				return 50;
			case 35:

				return 51;
			case 36:

				return 52;
			case 37:

				return 53;
			case 94:

				return 54;
			case 38:

				return 55;
			case 42:

				return 56;
			case 40:

				return 57;
			case 41:

				return 48;
			case 95:

				return 45;
			case 43:

				return 61;
			case 123:

				return 91;
			case 125:

				return 93;
			case 58:

				return 59;
			case 34:

				return 39;
			case 60:

				return 44;
			case 62:

				return 46;
			case 63:

				return 47;
			default:
				return val;
		}

	}
}