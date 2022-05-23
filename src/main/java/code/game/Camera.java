package code.game;

import code.engine.GameEngine;
import code.engine.Renderer;

public class Camera {

	private float offX;
	private float offY;

	private String targetTag;
	private GameObject target = null;

	public Camera(String tag) {
		this.targetTag = tag;

	}

	public void update(GameEngine ge, GameManager gm, float dt) {

		if (target == null) {

			target = gm.getObject(targetTag);

		}

		if (target == null) {
			return;
		}

		float targetX = (target.getPosX() + target.getWidth() / 2) - ge.getWidth() / 2;
		float targetY = (target.getPosY() + target.getHeight() / 2) - ge.getHeight() / 2;

		// Smooth Camera
		offX -= dt * (offX - targetX) * 8;
		offY -= dt * (offY - targetY) * 8;

	}

	public void render(Renderer r) {

		r.setCamX((int) offX);
		r.setCamY((int) offY);
	}

	// Getters & Setters

	public float getOffX() {
		return offX;
	}

	public void setOffX(float offX) {
		this.offX = offX;
	}

	public float getOffY() {
		return offY;
	}

	public void setOffY(float offY) {
		this.offY = offY;
	}

	public String getTargetTag() {
		return targetTag;
	}

	public void setTargetTag(String targetTag) {
		this.targetTag = targetTag;
	}

	public GameObject getTarget() {
		return target;
	}

	public void setTarget(GameObject target) {
		this.target = target;
	}

}
