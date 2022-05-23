package code.game;

import java.awt.event.KeyEvent;

import code.engine.GameEngine;
import code.engine.Renderer;
import code.engine.gfx.Light;

public class Player extends GameObject {

	private int tileX;
	private int tileY;

	private float offX;
	private float offY;

	private float speed = 100;

	private float fallSpeed = 10;
	private float jump = -4;
	private boolean ground = false;

	private float fallDistance = 0;

	private Light light;

	public Player(int posX, int posY) {

		this.tag = "player";
		this.tileX = posX;
		this.tileY = posY;
		this.offX = 0;
		this.offY = 0;
		this.posX = posX * GameManager.TS;
		this.posY = posY * GameManager.TS;
		this.width = GameManager.TS;
		this.height = GameManager.TS;
	}

	@Override
	public void update(GameEngine ge, GameManager gm, float dt) {

		// #region Left and Right

		if (ge.getInput().key(KeyEvent.VK_RIGHT) || ge.getInput().key(KeyEvent.VK_L)) {
			if (gm.getCollision(tileX + 1, tileY)
					|| gm.getCollision(tileX + 1, tileY + (int) Math.signum((int) offY))) {

				if (offX < 0) {

					offX += dt * speed;

					if (offX > 0) {
						offX = 0;
					}

				} else {
					offX = 0;
				}
			}
			// else {
			// 	offX += dt * speed;
			// }
			if (ge.getInput().key(KeyEvent.VK_SHIFT)) {
				//System.err.println("uwu");
				if (gm.getCollision(tileX + 1, tileY)
						|| gm.getCollision(tileX + 1, tileY + (int) Math.signum((int) offY))) {
	
					if (offX < 0) {
						offX += dt * speed;
						System.err.println(offX);
	
						if (offX > 0) {
							offX = 0;
						}
	
					} else {
						offX = 0;
					}
				}
			} else {
				offX += dt * speed;
				System.err.println(offX);
			}
		}


		if (ge.getInput().key(KeyEvent.VK_LEFT) || ge.getInput().key(KeyEvent.VK_J)) {

			if (gm.getCollision(tileX - 1, tileY)
					|| gm.getCollision(tileX - 1, tileY + (int) Math.signum((int) offY))) {

				if (offX > 0) {
					offX -= dt * speed;

					if (offX < 0) {
						offX = 0;
					}

				} else {
					offX = 0;
				}

			}
			// else {
			// 	offX -= dt * speed;
			// }
			if (ge.getInput().key(KeyEvent.VK_SHIFT)) {
				//System.err.println("uwu");
				if (gm.getCollision(tileX - 1, tileY)
						|| gm.getCollision(tileX - 1, tileY + (int) Math.signum((int) offY))) {
	
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
			} 
			else {
				offX -= dt * speed;
				System.err.println(offX);
			}
		}

		// To fix jump in the air

		// if (fallDistance > 0) {
		// if ((gm.getCollision(tileX, tileY + 1) || gm.getCollision(tileX + (int)
		// Math.signum((int) offX), tileY + 1))
		// && offY > 0) {
		// fallDistance = 0;
		// offY = 0;
		// ground = true;
		// } else
		// ground = false;
		// }

		// #endregion End of Left and Right

		// #region Jump and Gravity

		fallDistance += dt * fallSpeed;

		int doubleJump = 0;
		if (ge.getInput().key(KeyEvent.VK_SPACE) && ground) {
			fallDistance = jump;
			// ground = false;
			doubleJump++;
			if (doubleJump==1 && ge.getInput().key(KeyEvent.VK_SPACE)) {
				System.out.println(doubleJump);
				fallDistance = jump;
				doubleJump++;
			}
			ground = false;
		}

		offY += fallDistance;

		if (fallDistance < 0) {

			if ((gm.getCollision(tileX, tileY - 1) ||
					gm.getCollision(tileX + (int) Math.signum((int) offX), tileY - 1)) &&
					offY < 0) {
				fallDistance = 0;
				offY = 0;
			}

		}

		if (fallDistance > 0) {

			// System.err.println(gm.getCollision(tileX, tileY + 1));

			if ((gm.getCollision(tileX, tileY + 1) || gm.getCollision(tileX + (int) Math.signum((int) offX), tileY + 1))
					&& offY > 0) {
				fallDistance = 0;
				offY = 0;
				ground = true;
			}
		}

		// #endregion End of Jump and Gravity

		// Final position

		if (offY > GameManager.TS / 2) {

			tileY++;
			offY -= GameManager.TS;
		}

		if (offY < -GameManager.TS / 2) {

			tileY--;
			offY += GameManager.TS;
		}

		if (offX > GameManager.TS / 2) {

			tileX++;
			offX -= GameManager.TS;
		}

		if (offX < -GameManager.TS / 2) {

			tileX--;
			offX += GameManager.TS;
		}

		posX = tileX * GameManager.TS + offX;
		posY = tileY * GameManager.TS + offY;

		// Shooting
		if (ge.getInput().keyDown(KeyEvent.VK_W)) {
			gm.addObject(new Bullet(tileX, tileY, offX + width / 2, offY + height / 2, 0));
		}
		if (ge.getInput().keyDown(KeyEvent.VK_D)) {
			gm.addObject(new Bullet(tileX, tileY, offX + width / 2, offY + height / 2, 1));
		}
		if (ge.getInput().keyDown(KeyEvent.VK_S)) {
			gm.addObject(new Bullet(tileX, tileY, offX + width / 2, offY + height / 2, 2));
		}
		if (ge.getInput().keyDown(KeyEvent.VK_A)) {
			gm.addObject(new Bullet(tileX, tileY, offX + width / 2, offY + height / 2, 3));
		}

	}

	@Override
	public void render(GameEngine ge, Renderer r) {
		light = new Light(100, 0xffcccccc);
		r.drawLight(light, (int) posX + 8, (int) posY + 8);
		r.drawFillRect((int) posX, (int) posY, width, height, 0xff00ff00);
	}

}
