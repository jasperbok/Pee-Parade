package nl.jasperbok.peeparade.level;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import nl.jasperbok.peeparade.entity.Entity;

public class Tile extends Entity {
	public int id;
	public int width;
	public int height;
	
	public Tile(int id, int width, int height, int xPos, int yPos, boolean blocking) throws SlickException {
		this.id = id;
		this.width = width;
		this.height = height;
		this.isBlocking = blocking;
		this.isMovable = false;
		this.boundingBox = new Rectangle(xPos, yPos, width, height);
		this.setPosition(xPos, yPos);
	}
}