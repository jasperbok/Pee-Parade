package nl.jasperbok.peeparade.level;

import nl.jasperbok.peeparade.gui.Hud;
import nl.jasperbok.peeparade.level.TileEnvironment;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Level {
	public TileEnvironment env;
	
	public Level(String mapFileName) throws SlickException {
		env = new TileEnvironment(mapFileName);
	}
	
	public void update(GameContainer container, int delta) throws SlickException {
		env.update(container, delta);
		Hud.getInstance().update(delta);
	}
	
	public void render(GameContainer container, Graphics g) throws SlickException {
		env.render(container, g);
		Hud.getInstance().render(container, g);
	}
}

