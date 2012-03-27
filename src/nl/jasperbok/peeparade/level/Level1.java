package nl.jasperbok.peeparade.level;

import nl.jasperbok.peeparade.entity.NyanCat;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Level1 extends Level {

	public Level1() throws SlickException {
		super("level_1");
		env.addPlayer(new NyanCat(this, 1, "human"));
	}
	
	public void update(GameContainer container, int delta) throws SlickException {
		super.update(container, delta);
	}
	
	public void render(GameContainer container, Graphics g) throws SlickException {
		super.render(container, g);
	}
}
