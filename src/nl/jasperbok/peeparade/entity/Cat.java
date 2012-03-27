package nl.jasperbok.peeparade.entity;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import nl.jasperbok.peeparade.entity.mob.Mob;
import nl.jasperbok.peeparade.level.Level;

public class Cat extends Mob {
	private int playerNumber;
	protected String playerType;
	
	protected int upKey;
	protected int downKey;
	protected int rightKey;
	protected int leftKey;
	protected int actionKey;

	public Cat(Level level, int playerNumber, String playerType) throws SlickException {
		super(level);
		this.playerNumber = playerNumber;
		this.playerType = playerType;
		setControls();
	}
	
	private void setControls() {
		if (playerNumber == 1) {
			upKey = Input.KEY_UP;
			downKey = Input.KEY_DOWN;
			rightKey = Input.KEY_RIGHT;
			leftKey = Input.KEY_LEFT;
			actionKey = Input.KEY_RCONTROL;
		} else if (playerNumber == 2) {
			upKey = Input.KEY_W;
			downKey = Input.KEY_S;
			rightKey = Input.KEY_D;
			leftKey = Input.KEY_A;
			actionKey = Input.KEY_E;
		}
	}
}
