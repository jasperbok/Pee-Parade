package nl.jasperbok.peeparade;

import nl.jasperbok.peeparade.PeeParade;
import nl.jasperbok.peeparade.level.Level;
import nl.jasperbok.peeparade.level.Level1;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class PeeParade extends BasicGame {
	private Level currentLevel;
	
	public PeeParade() throws SlickException {
		super("Pee Parade");
	}
	
	public void init(GameContainer container) throws SlickException {
		currentLevel = new Level1();
	}
	
	public void update(GameContainer container, int delta) throws SlickException {
		currentLevel.update(container, delta);
	}
	
	public void render(GameContainer container, Graphics g) throws SlickException {
		currentLevel.render(container, g);
	}
	
	public static void main(String[] args) throws SlickException{
		AppGameContainer app = new AppGameContainer(new PeeParade());
		app.setDisplayMode(992, 672, false);
		app.start();
	}
}
