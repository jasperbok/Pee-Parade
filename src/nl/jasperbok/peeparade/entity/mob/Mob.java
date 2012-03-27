package nl.jasperbok.peeparade.entity.mob;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import nl.jasperbok.peeparade.entity.Entity;
import nl.jasperbok.peeparade.level.Level;

public abstract class Mob extends Entity {
	protected int currentHealth;
	protected int maxHealth;
	
	/**
	 * Heals the Mob.
	 * @param amount The amount of HP the Mob gains.
	 */
	public void heal(int amount) {
		currentHealth += amount;
		if (currentHealth > maxHealth) currentHealth = maxHealth;
	}
	
	/**
	 * Deals damage to the Mob.
	 * 
	 * @param amount The amount of HP the Mob loses.
	 */
	public void hurt(int amount) {
		currentHealth -= amount;
		if (currentHealth <= 0) {
			currentHealth = 0;
			die();
		}
	}
	
	public void die() {
		
	}
	
	public Mob(Level level) throws SlickException {
		super(level);
	}
	
	/**
	 * Dummy function so that classes that extend Mob can be used inside
	 * lists and we can still call update() on them.
	 * 
	 * @param container The GameContainer this Mob is part of.
	 * @param delta The elapsed time since the last update.
	 * @throws SlickException
	 */
	public void update(int delta) throws SlickException {
		
	}
	
	/**
	 * Dummy function so that classes that extend Mob can be used inside
	 * lists and we can still call render() on them.
	 * 
	 * @param container The GameContainer this Mob is part of.
	 * @param g The Graphics object that renders the screen.
	 * @throws SlickException
	 */
	public void render(GameContainer container, Graphics g) throws SlickException {
		
	}
}
