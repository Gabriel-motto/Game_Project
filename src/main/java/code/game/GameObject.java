package code.game;

import code.engine.GameEngine;
import code.engine.Renderer;

public abstract class GameObject {

	protected String tag;

	protected float posX;
	protected float posY;
	
	protected int width;
	protected int height;
	protected boolean dead = false;

	public abstract void update(GameEngine ge, GameManager gm,float dt);

	public abstract void render(GameEngine ge, Renderer r);

	//#region setter y getters
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public float getPosX() {
		return posX;
	}

	public void setPosX(float posX) {
		this.posX = posX;
	}

	public float getPosY() {
		return posY;
	}

	public void setPosY(float posY) {
		this.posY = posY;
	}

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

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	//#endregion
}
