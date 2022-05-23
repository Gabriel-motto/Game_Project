package code.engine;

public abstract class AbstractGame {

	public abstract void init(GameEngine ge);

	public abstract void update(GameEngine ge, float dt);

	public abstract void render(GameEngine ge, Renderer r);
}
