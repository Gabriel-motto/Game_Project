package code.game;

import code.engine.GameEngine;
import code.engine.Renderer;
import code.engine.gfx.Light;

public class Bullet extends GameObject {

	private int tileX;
	private int tileY;

	private float offX;
	private float offY;

	private int direction;
	private float speed = 600;

	private Light light;
	// private Light light2;

	public Bullet(int tileX, int tileY, float offX, float offY, int direction) {

		this.direction = direction;
		this.tileX = tileX;
		this.tileY = tileY;
		this.offX = offX;
		this.offY = offY;
		posX = tileX * GameManager.TS + offX;
		posY = tileY * GameManager.TS + offY;

	}

	@Override
	public void update(GameEngine ge, GameManager gm, float dt) {

		switch (direction) {
			case 0:
				offY -= speed * dt;
				break;
			case 1:
				offX += speed * dt;
				break;
			case 2:
				offY += speed * dt;
				break;
			case 3:
				offX -= speed * dt;
				break;
		}

		// Final position

		if (offY > GameManager.TS) {

			tileY++;
			offY -= GameManager.TS;
		}

		if (offY < 0) {

			tileY--;
			offY += GameManager.TS;
		}

		if (offX > GameManager.TS) {

			tileX++;
			offX -= GameManager.TS;
		}

		if (offX < 0) {

			tileX--;
			offX += GameManager.TS;
		}

		if (gm.getCollision(tileX, tileY)) {

			this.dead = true;
		}

		posX = tileX * GameManager.TS + offX;
		posY = tileY * GameManager.TS + offY;

	}

	@Override
	public void render(GameEngine ge, Renderer r) {

		light = new Light(20, 0xffff0000);
		r.drawLight(light, (int) posX + 2, (int) posY - 1);
		if (direction==0) {
			r.drawFillRect((int) posX - 2, (int) posY - 2, 2, 10, 0xffff0000);
		} else {
			r.drawFillRect((int) posX - 2, (int) posY - 2, 10, 2, 0xffff0000);
		}
	}
}
