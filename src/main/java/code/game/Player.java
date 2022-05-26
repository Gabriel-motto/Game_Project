package code.game;

import java.awt.event.KeyEvent;

import code.engine.GameEngine;
import code.engine.Renderer;
import code.engine.gfx.Light;

public class Player extends GameObject {
	// Posicion del jugador
	private int playerX;
	private int playerY;

	private float offX;
	private float offY;

	private float speed = 100;

	private float fallSpeed = 10;
	private float jump = -4;
	private boolean ground = false;

	private float fallDistance = 0;
	private boolean doubleJump = true;
	private boolean limitJump = true;

	private Light light;

	public Player(int posX, int posY) {
		this.tag = "player";
		this.playerX = posX;
		this.playerY = posY;
		this.offX = 0;
		this.offY = 0;
		this.posX = posX * GameManager.pixelSize;
		this.posY = posY * GameManager.pixelSize;
		this.width = GameManager.pixelSize;
		this.height = GameManager.pixelSize;
	}

	@Override
	public void update(GameEngine ge, GameManager gm, float dt) {

		// #region Movimiento derecha-izquierda

		if (ge.getInput().key(KeyEvent.VK_RIGHT) || ge.getInput().key(KeyEvent.VK_L)) {
			if (ge.getInput().key(KeyEvent.VK_SHIFT)) {
				System.err.println("shift");
				offX += dt * speed*2;
				if (gm.getCollision(playerX + 1, playerY)
						|| gm.getCollision(playerX + 1, playerY + (int) Math.signum((int) offY))) {
					if (offX < 0) {
						offX += dt * speed*2;
						System.err.println(offX);
						if (offX > 0) {
							offX = 0;
						}
					} else {
						offX = 0;
					}
				}
			} else if (gm.getCollision(playerX + 1, playerY)
					|| gm.getCollision(playerX + 1, playerY + (int) Math.signum((int) offY))) {
				if (offX < 0) {
					offX += dt * speed;
					if (offX > 0) {
						offX = 0;
					}
				} else {
					offX = 0;
				}
			} else {
				offX += dt * speed;
			}
		}

		if (ge.getInput().key(KeyEvent.VK_LEFT) || ge.getInput().key(KeyEvent.VK_J)) {
			if (ge.getInput().key(KeyEvent.VK_SHIFT)) {
				System.err.println("shift");
				offX -= dt * speed*2;
				if (gm.getCollision(playerX - 1, playerY)
						|| gm.getCollision(playerX - 1, playerY + (int) Math.signum((int) offY))) {
					if (offX > 0) {
						offX -= dt * speed;
						System.err.println(offX);
						if (offX < 0) {
							offX = 0;
						}
	
					} else {
						offX = 0;
					}
				}
			} else if (gm.getCollision(playerX - 1, playerY)
					|| gm.getCollision(playerX - 1, playerY + (int) Math.signum((int) offY))) {
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
		
		// #endregion End of Left and Right

		// #region Jump and Gravity

		// Para no saltar mid-air

		// if (fallDistance > 0) {
		// if ((gm.getCollision(playerX, playerY + 1) || gm.getCollision(playerX + (int)
		// Math.signum((int) offX), playerY + 1))
		// && offY > 0) {
		// fallDistance = 0;
		// offY = 0;
		// ground = true;
		// } else
		// ground = false;
		// }

		fallDistance += dt * fallSpeed;

		if (ge.getInput().key(KeyEvent.VK_SPACE) && limitJump) {

			if (!doubleJump && !limitJump) {
				fallDistance += jump;
				System.out.println("double");
				
			} else {
				fallDistance = jump;
				doubleJump = false;
				limitJump = false;
				ground = false;
			}
			
			System.out.println("double"+doubleJump);
			System.out.println("limit"+limitJump);

			// fallDistance = jump;
			// doubleJump = false;
			// ground = false;
			// if (ge.getInput().key(KeyEvent.VK_SPACE) && !doubleJump) {
			// 	System.out.println("uwu");
			// 	fallDistance += jump;
			// 	doubleJump = true;
			// }
			// limitJump = false;
		}

		offY += fallDistance;

		if (fallDistance < 0) {
			if ((gm.getCollision(playerX, playerY - 1) ||
					gm.getCollision(playerX + (int) Math.signum((int) offX), playerY - 1)) &&
					offY < 0) {
				fallDistance = 0;
				offY = 0;
			}

		}

		if (fallDistance > 0) {

			// System.err.println(gm.getCollision(playerX, playerY + 1));

			if ((gm.getCollision(playerX, playerY + 1) || gm.getCollision(playerX + (int) Math.signum((int) offX), playerY + 1))
					&& offY > 0) {
				fallDistance = 0;
				offY = 0;
				ground = true;
				limitJump = true;
			}
		}

		// #endregion

		//#region Posicion final

		if (offY > GameManager.pixelSize / 2) {

			playerY++;
			offY -= GameManager.pixelSize;
		}

		if (offY < -GameManager.pixelSize / 2) {

			playerY--;
			offY += GameManager.pixelSize;
		}

		if (offX > GameManager.pixelSize / 2) {

			playerX++;
			offX -= GameManager.pixelSize;
		}

		if (offX < -GameManager.pixelSize / 2) {

			playerX--;
			offX += GameManager.pixelSize;
		}

		posX = playerX * GameManager.pixelSize + offX;
		posY = playerY * GameManager.pixelSize + offY;

		//#endregion
		
		//#region Disparar

		if (ge.getInput().key(KeyEvent.VK_W)) {
			
			gm.addObject(new Bullet(playerX, playerY, offX + width / 2, offY + height / 2, 0));
		}
		if (ge.getInput().key(KeyEvent.VK_D)) {
			gm.addObject(new Bullet(playerX, playerY, offX + width / 2, offY + height / 2, 1));
		}
		if (ge.getInput().key(KeyEvent.VK_S)) {
			gm.addObject(new Bullet(playerX, playerY, offX + width / 2, offY + height / 2, 2));
		}
		if (ge.getInput().key(KeyEvent.VK_A)) {
			gm.addObject(new Bullet(playerX, playerY, offX + width / 2, offY + height / 2, 3));
		}

		//#endregion
	}

	@Override
	public void render(GameEngine ge, Renderer r) {
		light = new Light(100, 0xffcccccc);
		r.drawLight(light, (int) posX + 8, (int) posY + 8);
		r.drawFillRect((int) posX, (int) posY, width, height, 0xff00ff00);
	}
}
