package nl.jasperbok.peeparade.level;

import org.newdawn.slick.SlickException;

import nl.jasperbok.peeparade.entity.Entity;

public class Tile extends Entity {
	public int width;
	public int height;
	
	public Tile(int width, int height, int xPos, int yPos, boolean blocking) throws SlickException {
		this.width = width;
		this.height = height;
		this.isBlocking = blocking;
		this.isMovable = false;
		this.setPosition(xPos, yPos);
	}
}