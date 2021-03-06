package code.game;

import java.util.ArrayList;

import code.engine.*;
//import code.engine.audio.SoundClip;
import code.engine.gfx.*;

public class GameManager extends AbstractGame {

	// Tamaño de los pixeles
	public static final int pixelSize = 16;

	private Image levelImage = new Image("/resources/img/LvlMap.png");
	private Image skyImage = new Image("/resources/img/sky.png");

	private ArrayList<GameObject> objects = new ArrayList<GameObject>();
	private Camera camera;

	private boolean[] collision;
	private int levelW;
	private int levelH;

	public int numEnemigos = 0;

	// private SoundClip backSong = new SoundClip("/resources/audio/test.wav");

	public GameManager() {
		objects.add(new Player(3, 34));

		objects.add(new Enemy(38, 27, 46, 0));
		objects.add(new Enemy(12, 13, 17, 1));
		objects.add(new Enemy(76, 13, 91, 2));
		objects.add(new Enemy(73, 6, 88, 3));
		objects.add(new Enemy(86, 37, 93, 4));
		objects.add(new Enemy(46, 7, 54, 5));
		objects.add(new Enemy(27, 3, 31, 6));
		numEnemigos = 7;

		loadLevel("/resources/img/ConceptMap.png");
		camera = new Camera("player");
		// 	levelImag.setAlpha(true);
		
		levelImage.setLightBlock((int)Light.NONE);
	}

	@Override
	public void init(GameEngine ge) {
		ge.getRenderer().setAmbientColor(0xff201d40);

		// backSong.setVolume(-10);
		// backSong.play();
	}

	@Override
	public void update(GameEngine ge, float dt) {
		for (int i = 0; i < objects.size(); i++) {
			objects.get(i).update(ge, this, dt);
			if (objects.get(i).isDead()) {
				objects.remove(i);
				i--;
			}
		}
		camera.update(ge, this, dt);
	}

	@Override
	public void render(GameEngine ge, Renderer r) {
		
		// puesta de luces hecha en Player
		// r.setzDepth(1);
		// r.drawLight(light, (int) objects.get(0).getPosX(), (int) objects.get(0).getPosY());

		// r.drawText(String.format("%d", ge.getFps()), 0, 0, 0xff00ffff);

		camera.render(r);
		r.setzDepth(0);
		r.drawImage(skyImage, 0, 0);
		r.drawImage(levelImage, 0, 0);

		for (GameObject obj : objects) {
			obj.render(ge, r);
		}
	}

	public void loadLevel(String path) {
		Image levelImage = new Image(path);

		levelW = levelImage.getW();
		levelH = levelImage.getH();
		collision = new boolean[levelW * levelH];

		for (int y = 0; y < levelImage.getH(); y++) {
			for (int x = 0; x < levelImage.getW(); x++) {
				if (levelImage.getP()[x + y * levelImage.getW()] == 0xff000000) {
					collision[x + y * levelImage.getW()] = true;
				} else {
					collision[x + y * levelImage.getW()] = false;
				}
			}

		}
	}

	public void addObject(GameObject object) {
		objects.add(object);
	}

	public GameObject getObject(String tag) {
		for (int i = 0; i < objects.size(); i++) {
			if (objects.get(i).getTag().equals(tag)) {
				return objects.get(i);
			}
		}
		return null;
	}

	public boolean getCollision(int x, int y) {
		if (x < 0 || x >= levelW || y < 0 || y >= levelH) {
			return true;
		}
		return collision[x + y * levelW];
	}

	public static void main(String[] args) {
		GameEngine ge = new GameEngine(new GameManager());
		ge.setWidth(320);
		ge.setHeight(240);
		ge.setScale(3f);
		ge.start();
	}

}
