package code.engine;

import java.awt.event.*;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener{

	private GameEngine ge;

	private final int num_keys = 256;
	private boolean[] keys = new boolean[num_keys];
	private boolean[] keysLast = new boolean[num_keys];

	private final int num_buttons = 5;
	private boolean[] buttons = new boolean[num_buttons];
	private boolean[] buttonsLast = new boolean[num_buttons];

	private int mouseX;
	private int mouseY;
	private int scroll;

	//#region setters y getters
	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public int getScroll() {
		return scroll;
	}
	//#endregion setters y getters

	public Input(GameEngine ge) {
		this.ge = ge;
		mouseX = 0;
		mouseY = 0;
		scroll = 0;

		ge.getWindow().getCanvas().addKeyListener(this);
		ge.getWindow().getCanvas().addMouseListener(this);
		ge.getWindow().getCanvas().addMouseMotionListener(this);
		ge.getWindow().getCanvas().addMouseWheelListener(this);
	}

	public void update() {
		scroll = 0;

		for (int i = 0; i < num_keys; i++) {
			keysLast[i] = keys[i];
		}

		for (int i = 0; i < num_buttons; i++) {
			buttonsLast[i] = buttons[i];
		}
	}

	// Keys
	public boolean key(int keyCode) {
		return keys[keyCode];
	}
	public boolean keyUp(int keyCode) {
		return !keys[keyCode] && keysLast[keyCode];
	}
	public boolean keyDown(int keyCode) {
		return keys[keyCode] && !keysLast[keyCode];
	}

	// Buttons
	public boolean button(int button) {
		return buttons[button];
	}
	public boolean buttonUp(int button) {
		return !buttons[button] && buttonsLast[button];
	}
	public boolean buttonDown(int button) {
		return buttons[button] && !buttonsLast[button];
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = (int) (e.getX() / ge.getScale());
		mouseY = (int) (e.getY() / ge.getScale());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = (int) (e.getX() / ge.getScale());
		mouseY = (int) (e.getY() / ge.getScale());
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		buttons[e.getButton()] = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		buttons[e.getButton()] = true;
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		scroll = e.getWheelRotation();
	}
}
