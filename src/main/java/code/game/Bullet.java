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
	private float speed = 400;

	private Light light;
	// private Light light2;

	public Bullet(int tileX, int tileY, float offX, float offY, int direction) {
		this.tag = "bullet";
		this.direction = direction;
		this.tileX = tileX;
		this.tileY = tileY;
		this.offX = offX;
		this.offY = offY;
		posX = tileX * GameManager.pixelSize + offX;
		posY = tileY * GameManager.pixelSize + offY;

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

		if (offY > GameManager.pixelSize) {
			tileY++;
			offY -= GameManager.pixelSize;
		}

		if (offY < 0) {
			tileY--;
			offY += GameManager.pixelSize;
		}

		if (offX > GameManager.pixelSize) {
			tileX++;
			offX -= GameManager.pixelSize;
		}

		if (offX < 0) {
			tileX--;
			offX += GameManager.pixelSize;
		}

		//System.err.println("enemigo"+(int) gm.getObject("enemy").getPosX() / GameManager.pixelSize);
		//System.err.println((int) (gm.getObject("enemy").getPosY() / GameManager.pixelSize));
		//System.err.println("bala"+tileX);
		//System.err.println(tileY);


		for (int i = 0; i < gm.numEnemigos; i++) {
			try {
			
				System.err.println(i);

				// Desaparición de la bala
				if (gm.getCollision(tileX, tileY) ||
				((int) (gm.getObject("enemy"+i).getPosX() / GameManager.pixelSize) == tileX
				&& (int) (gm.getObject("enemy"+i).getPosY() / GameManager.pixelSize) == tileY)) {
					this.dead = true;
				}

				// Desaparición del enemigo
				if (((int) (gm.getObject("enemy"+i).getPosX() / GameManager.pixelSize) == tileX
				&& (int) (gm.getObject("enemy"+i).getPosY() / GameManager.pixelSize) == tileY)) {
					gm.getObject("enemy"+i).dead = true;
				}

			} catch (NullPointerException e) {}
		}

		posX = tileX * GameManager.pixelSize + offX;
		posY = tileY * GameManager.pixelSize + offY;
	}

	@Override
	public void render(GameEngine ge, Renderer r) {

		light = new Light(20, 0xffff0000);
		r.drawLight(light, (int) posX + 2, (int) posY - 1);
		if (direction==0) {
			r.drawFillRect((int) posX - 2, (int) posY - 2, 2, 5, 0xffff0000);
		} else {
			r.drawFillRect((int) posX - 2, (int) posY - 2, 5, 2, 0xffff0000);
		}
	}
}
