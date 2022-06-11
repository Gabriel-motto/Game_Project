package code.game;

import java.awt.event.KeyEvent;

import code.engine.GameEngine;
import code.engine.Renderer;
import code.engine.gfx.Image;
import code.engine.gfx.ImageTile;
import code.engine.gfx.Light;

public class Player extends GameObject {
	// Posicion del jugador
	private int playerX;
	private int playerY;

	// Movimiento
	private float offX;
	private float offY;

	// Velocidad
	private float speed = 80;

	// Gravedad y variables de salto
	private float gravity = 10;
	private float jump = -4;
	private float fallDistance = 0;
	private boolean ground = false;
	private int jumps = 0;

	private Light light;

	// Sprite movimiento y animacion
	//private Image imagen = new Image("/resources/img/Slime.png");
	private ImageTile playerImage = new ImageTile("/resources/img/Slime.png", 16, 16);
	private int direction = 0;
	private float animation = 1;

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
		// L y J movimiento para teclado 60% sin flechas de direccion

		// Derecha
		if (ge.getInput().key(KeyEvent.VK_RIGHT) || ge.getInput().key(KeyEvent.VK_L)) {

			direction = 0;
			if (ground) {
				animation += dt * 5;

				if (animation >= 6) {
					animation = 3;
				}
			}

			if (ge.getInput().key(KeyEvent.VK_SHIFT)) {
				// System.err.println("shift");

				if (ground) {
					animation += dt * 15;

					if (animation >= 6) {
						animation = 3;
					}
				}

				offX += dt * speed * 2;
				if (gm.getCollision(playerX + 1, playerY)
						|| gm.getCollision(playerX + 1, playerY + (int) Math.signum((int) offY))) {
					if (offX < 0) {
						offX += dt * speed * 2;
						// System.err.println(offX);
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
				// System.err.println(offX);
			}
		}

		// Izquierda
		if (ge.getInput().key(KeyEvent.VK_LEFT) || ge.getInput().key(KeyEvent.VK_J)) {

			direction = 1;
			if (ground) {
				animation += dt * 5;

				if (animation >= 6) {
					animation = 3;
				}
			}

			if (ge.getInput().key(KeyEvent.VK_SHIFT)) {
				// System.err.println("shift");

				if (ground) {
					animation += dt * 15;
					if (animation >= 6) {
						animation = 3;
					}
				}

				offX -= dt * speed * 2;
				if (gm.getCollision(playerX - 1, playerY)
						|| gm.getCollision(playerX - 1, playerY + (int) Math.signum((int) offY))) {
					if (offX > 0) {
						offX -= dt * speed;
						// System.err.println(offX);
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

		// System.err.println(this.posX);

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

		fallDistance += dt * gravity;

		if (ge.getInput().keyDown(KeyEvent.VK_SPACE)) {

			jumps++;
		}
		// System.err.println(jumps);

		if (ge.getInput().keyDown(KeyEvent.VK_SPACE) && ground) {
			fallDistance = jump;
			ground = false;
		}

		if (jumps == 2) {
			fallDistance = jump + 1;
			jumps++;
		}

		offY += fallDistance;

		// System.err.println(fallDistance);

		if (fallDistance < 0) {
			if ((gm.getCollision(playerX, playerY - 1) ||
					gm.getCollision(playerX + (int) Math.signum((int) offX), playerY - 1)) &&
					offY < 0) {
				// fallDistance = 0;
				// offY = 0;

				animation = 6;
			}

		}

		if (fallDistance > 0) {
			if ((gm.getCollision(playerX, playerY + 1) ||
					gm.getCollision(playerX + (int) Math.signum((int) offX), playerY + 1)) &&
					offY > 0) {
				fallDistance = 0;
				offY = 0;
				ground = true;
				jumps = 0;
			}
		}

		if ((int) fallDistance == 0 && (int) offX == 0 && (int) offY == 0) {
			if (ground) {
				
				animation += dt * 3;
				
				if (animation >= 3) {
					animation = 0;
				}
			}
		}
		if ((int) fallDistance < 0) {
			if (ge.getInput().key(KeyEvent.VK_RIGHT)) {
				direction = 0;
				animation = 6;
			}
			if (ge.getInput().key(KeyEvent.VK_LEFT)) {
				direction = 1;
				animation = 6;
			}
			System.err.println(direction);
		}
		if ((int) fallDistance > 0) {
			if (ge.getInput().key(KeyEvent.VK_RIGHT)) {
				direction = 0;
				animation = 7;
			}
			if (ge.getInput().key(KeyEvent.VK_LEFT)) {
				direction = 1;
				animation = 7;
			}
		}

		// #endregion

		// #region Posicion final

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

		// #endregion

		// #region Disparar

		if (ge.getInput().keyDown(KeyEvent.VK_W)) {
			gm.addObject(new Bullet(playerX, playerY, offX + width / 2, offY + height / 2, 0));
		}
		if (ge.getInput().keyDown(KeyEvent.VK_D)) {
			gm.addObject(new Bullet(playerX, playerY, offX + width / 2, offY + height / 2, 1));
		}
		// if (ge.getInput().keyDown(KeyEvent.VK_S)) {
		// gm.addObject(new Bullet(playerX, playerY, offX + width / 2, offY + height /
		// 2, 2));
		// }
		if (ge.getInput().keyDown(KeyEvent.VK_A)) {
			gm.addObject(new Bullet(playerX, playerY, offX + width / 2, offY + height / 2, 3));
		}

		// #endregion
	}

	@Override
	public void render(GameEngine ge, Renderer r) {
		light = new Light(100, 0xffcccccc);
		r.drawLight(light, (int) posX + 8, (int) posY + 8);
		// r.drawFillRect((int) posX, (int) posY, width, height, 0xff00ff00);
		//r.drawImage(imagen, (int) posX, (int) posY);
		r.drawImageTile(playerImage, (int) posX, (int) posY, (int) animation, direction);
	}
}
