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
	private boolean direction = true;

	private Light light;

	private float fallDistance = 0;

	public Enemy(int posX, int posY, int finalEnemyPos, int enemyNumber) {
		this.tag = "enemy" + enemyNumber;
		this.enemyX = posX; // 38 , 12
		this.enemyY = posY; // 27 , 13
		this.initialEnemyPos = posX;
		this.finalEnemyPos = finalEnemyPos;
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
		if (enemyX >= initialEnemyPos && direction) {
			offX += dt * speed;
			if (enemyX == finalEnemyPos) {
				direction = false;
			}

		}

		// Movimiento hacia la izquierda
		if (enemyX <= finalEnemyPos&& !direction) {
			offX -= dt * speed;
			if (enemyX == initialEnemyPos) {
				direction = true;
			}
		}

		//#endregion

		//#region Gravedad

		if (fallDistance < 0) {
			if ((gm.getCollision(enemyX, enemyY - 1) ||
					gm.getCollision(enemyX + (int) Math.signum((int) offX), enemyY - 1)) &&
					offY < 0) {
				fallDistance = 0;
				offY = 0;
			}
		}
		if (fallDistance > 0) {
			if ((gm.getCollision(enemyX,enemyY + 1) || gm.getCollision(enemyX + (int) Math.signum((int) offX),enemyY + 1))
					&& offY > 0) {
				fallDistance = 0;
				offY = 0;
			}
		}

		//#endregion

		//#region Posicion final

		if (offX > GameManager.pixelSize / 2) {
			enemyX++;
			offX -= GameManager.pixelSize;
		}

		if (offX < -GameManager.pixelSize / 2) {
			enemyX--;
			offX += GameManager.pixelSize;
		}
		posX = enemyX * GameManager.pixelSize + offX;

		//#endregion
	}

	@Override
	public void render(GameEngine ge, Renderer r) {
		light = new Light(50, 0xffff0000);
		r.drawLight(light, (int) posX + 8, (int) posY + 8);
		r.drawFillRect((int) posX, (int) posY, width, height, 0xffff0000);
	}
}
