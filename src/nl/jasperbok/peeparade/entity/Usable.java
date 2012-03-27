package nl.jasperbok.peeparade.entity;

import nl.jasperbok.peeparade.entity.Entity;

import org.newdawn.slick.geom.Rectangle;

public interface Usable {

	public void use(Entity user);
	
	public boolean canBeUsed(Rectangle rect);
}
