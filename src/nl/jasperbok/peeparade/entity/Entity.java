package nl.jasperbok.peeparade.entity;

import nl.jasperbok.peeparade.level.Level;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public abstract class Entity {
	public Level level;
	public Vector2f position = new Vector2f(0, 0);
	public Vector2f velocity = new Vector2f(0, 0);
	public Rectangle boundingBox;
	public boolean isBlocking = true;
	public boolean isMovable = true;
	
	public Entity(Level level) throws SlickException {
		this.level = level;
		this.boundingBox = new Rectangle(0, 0, 0, 0);
	}
	
	public Entity() throws SlickException {
		
	}
	
	/**
	 * Dummy function so that classes that extend Entity can be used inside
	 * lists and we can still call update() on them.
	 * 
	 * @param container The GameContainer this Entity is part of.
	 * @param delta The elapsed time since the last update.
	 * @throws SlickException
	 */
	public void update(Input input, int delta) throws SlickException {
		
	}
	
	/**
	 * Dummy function so that classes that extend Entity can be used inside
	 * lists and we can still call render() on them.
	 * 
	 * @param container The GameContainer this Entity is part of.
	 * @param g The Graphics object that renders the screen.
	 * @throws SlickException
	 */
	public void render(GameContainer container, Graphics g) throws SlickException {
		
	}
	
	public void setPosition(float xPos, float yPos) {
		position.set(xPos, yPos);
	}
	
	public void setXPosition(float xPos) {
		position.set(xPos, position.getY());
	}
	
	public void setYPosition(float yPos) {
		position.set(position.getX(), yPos);
	}
	
	public void setVelocity(float xVel, float yVel) {
		velocity.set(xVel, yVel);
	}
	
	public void setXVelocity(float xVel) {
		velocity.set(xVel, velocity.getY());
	}
	
	public void setYVelocity(float yVel) {
		velocity.set(velocity.getX(), yVel);
	}
}
