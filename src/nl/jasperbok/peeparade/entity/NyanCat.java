package nl.jasperbok.peeparade.entity;

import nl.jasperbok.peeparade.level.Level;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

public class NyanCat extends Cat {
	private float walkSpeed = 0.15f;
	// Variables for peeing.
	private int peeTime = 1000;
	private int timePeed = 0;
	private boolean peeing = false;
	// Variables for drinking.
	private int drinkTime = 1000;
	private int timeDrank = 0;
	private boolean drinking = false;
	
	// Sprites and animations.
	private SpriteSheet sprites;
	private Animation walkRightAnimation;
	private Animation walkLeftAnimation;
	private Animation walkUpAnimation;
	private Animation walkDownAnimation;
	private Animation peeAnimation;
	private Animation idleAnimation;
	private Animation currentAnimation;
	
	public NyanCat(Level level, int playerNumber, String playerType) throws SlickException {
		super(level, playerNumber, playerType);
		
		// Initialize the spritesheet and animations.
		sprites = new SpriteSheet("data/sprites/entity/nyan_cat.png", 32, 32);
		walkRightAnimation = new Animation();
		for (int i = 0; i < 3; i++) {
			walkRightAnimation.addFrame(sprites.getSprite(i, 0), 150);
		}
		walkLeftAnimation = new Animation();
		for (int i = 0; i < 3; i++) {
			walkLeftAnimation.addFrame(sprites.getSprite(i, 0).getFlippedCopy(true, false), 150);
		}
		walkUpAnimation = new Animation();
		for (int i = 0; i < 3; i++) {
			walkUpAnimation.addFrame(sprites.getSprite(i, 1), 150);
		}
		walkDownAnimation = new Animation();
		for (int i = 0; i < 3; i++) {
			walkDownAnimation.addFrame(sprites.getSprite(i, 2), 150);
		}
		peeAnimation = new Animation();
		for (int i = 0; i < 3; i++) {
			peeAnimation.addFrame(sprites.getSprite(i, 3), 150);
		}
		
		position = new Vector2f(200, 300);
		currentAnimation = walkRightAnimation;
	}
	
	public void updateBoundingBox() {
		boundingBox.setBounds(position.x, position.y, currentAnimation.getCurrentFrame().getWidth(), currentAnimation.getCurrentFrame().getHeight());
	}
	
	public void update(Input input, int delta) throws SlickException {
		updateBoundingBox();
		
		// If Nyancat is controlled by a human, check for keypresses,
		// but only if Nyancat is not peeing or drinking.
		if (playerType == "human" && !peeing && !drinking) {
			if (input.isKeyDown(leftKey)) velocity.set(-walkSpeed * delta, velocity.getY());
			if (input.isKeyDown(rightKey)) velocity.set(walkSpeed * delta, velocity.getY());
			if (!input.isKeyDown(leftKey) && !input.isKeyDown(rightKey)) velocity.set(0, velocity.getY());
			
			if (input.isKeyDown(upKey)) velocity.set(velocity.getX(), -walkSpeed * delta);
			if (input.isKeyDown(downKey)) velocity.set(velocity.getX(), walkSpeed * delta);
			if (!input.isKeyDown(upKey) && !input.isKeyDown(downKey)) velocity.set(velocity.getX(), 0);
			
			if (input.isKeyDown(actionKey)) {
				peeing = true;
			}
		}
		
		// Update the current animation depending on the velocity.
		if (velocity.getY() > 0) {
			currentAnimation = walkDownAnimation;
		} else if (velocity.getY() < 0) {
			currentAnimation = walkUpAnimation;
		}
		
		if (velocity.getX() > 0) {
			currentAnimation = walkRightAnimation;
		} else if (velocity.getX() < 0) {
			currentAnimation = walkLeftAnimation;
		}
		
		if (velocity.getX() == 0 && velocity.getY() == 0) currentAnimation = walkRightAnimation;
		
		if (peeing) {
			velocity = new Vector2f(0, 0);
			currentAnimation = peeAnimation;
			timePeed += delta;
			if (timePeed >= peeTime) {
				peeing = false;
				timePeed = 0;
			}
		}
	}
	
	public void render(GameContainer container, Graphics g) throws SlickException {
		currentAnimation.draw(position.getX(), position.getY());
	}
}
