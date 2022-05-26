package code.game;

import code.engine.GameEngine;
import code.engine.Renderer;
import code.engine.gfx.Light;

public class Enemy extends GameObject{
	private int enemyX;
	private int enemyY;
	private int initialEnemyPos;
	private int finalEnemyPos;

	private float offX;
	private float offY;

	private float speed = 100;

	private Light light;

	public Enemy(int posX, int posY) {
		this.tag = "enemy";
		this.enemyX = posX; // 38
		this.enemyY = posY; // 27
		this.initialEnemyPos = posX;
		this.finalEnemyPos = 46;
		this.offX = 0;
		this.offY = 0;
		this.posX = posX * GameManager.pixelSize;
		this.posY = posY * GameManager.pixelSize;
		this.width = GameManager.pixelSize;
		this.height = GameManager.pixelSize;
	}

	@Override
	public void update(GameEngine ge, GameManager gm, float dt) {
		//#region Movimiento

		// Movimiento hacia la derecha
		if (enemyX == initialEnemyPos) {
			if (gm.getCollision(enemyX + 1, enemyY)
					|| gm.getCollision(enemyX + 1, enemyY + (int) Math.signum((int) offY))) {
						
				if (offX < 0) {
					
					offX += dt * speed;
					if (offX > 0) {
						offX = 0;
					}
				} else {
					System.err.println("enemy");
					offX = 0;
				}
			} else {
				
				offX += dt * speed;
			}
		}

		// Movimiento hacia la izquierda
		if (enemyX == finalEnemyPos) {
			if (gm.getCollision(enemyX - 1, enemyY)
					|| gm.getCollision(enemyX - 1, enemyY + (int) Math.signum((int) offY))) {
				if (offX > 0) {
					offX -= dt * speed;
					if (offX < 0) {
						offX = 0;
					}
				} else {
					offX = 0;
				}
			} else {
				offX -= dt * speed;
			}
		}

		//#endregion
	}

	@Override
	public void render(GameEngine ge, Renderer r) {
		light = new Light(50, 0xffff0000);
		r.drawLight(light, (int) posX + 8, (int) posY + 8);
		r.drawFillRect((int) posX, (int) posY, width, height, 0xffff0000);
	}
}
