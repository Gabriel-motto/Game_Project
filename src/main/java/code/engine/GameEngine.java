package code.engine;

public class GameEngine implements Runnable {

	private Thread thread;
	private Window window;
	private Renderer renderer;
	private Input inp;
	private AbstractGame game;

	// true - esta ejecutando
	private boolean flag = false;

	// Limita fps a 60
	private final double fps_cap = 1.0 / 60.0;

	private int width = 320;
	private int height = 240;
	private float scale = 3f;
	private String title = "Elysium V0.1";
	private int fps = 0;

	//#region setters y getters
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Window getWindow() {
		return window;
	}

	public Input getInput() {
		return inp;
	}

	public Renderer getRenderer() {
		return renderer;
	}

	public int getFps() {
		return fps;
	}
	//#endregion setters y getters

	public GameEngine(AbstractGame game) {
		this.game = game;
	}

	public void start() {
		window = new Window(this);
		renderer = new Renderer(this);
		inp = new Input(this);
		thread = new Thread(this);
		thread.run();
	}

	public GameEngine() {
	}

	public void stop() {
	}

	public void run() {
		flag = true;

		boolean render = false;

		double firstTime = 0;
		double lastTime = System.nanoTime() / 1000000000.0;
		double passedTime = 0;
		double unprocessedTime = 0;

		double frameTime = 0;
		int frames = 0;

		game.init(this);

		while (flag) {
			// true - fps limite OFF
			// flase - fps limite ON
			render = true;

			firstTime = System.nanoTime() / 1000000000.0;
			passedTime = firstTime - lastTime;
			frameTime += passedTime;

			while (unprocessedTime >= fps_cap) {
				unprocessedTime -= fps_cap;
				render = true;

				game.update(this, (float) fps_cap);

				inp.update();

				if (frameTime >= 1.0) {
					frameTime = 0;
					fps = frames;
					frames = 0;
				}
			}

			if (render) {
				renderer.clear();
				game.render(this, renderer);
				renderer.process();
				renderer.setCamY(0);
				renderer.setCamX(0);
				renderer.drawText("" + fps, 0, 0, 0xff00ffff);
				window.update();
				frames++;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		dispose();
	}

	private void dispose() {
	}
}
