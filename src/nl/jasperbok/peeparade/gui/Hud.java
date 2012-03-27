package nl.jasperbok.peeparade.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Hud {
	private static Hud instance = null;
	
	public void update(int delta) throws SlickException {
	}
	
	public void render(GameContainer container, Graphics g) throws SlickException {
	}
	
	private Hud() {
	}
	
	public static synchronized Hud getInstance() {
		if (instance == null) {
			instance = new Hud();
		}
		
		return instance;
	}
}
