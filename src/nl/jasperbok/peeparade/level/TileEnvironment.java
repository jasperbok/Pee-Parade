package nl.jasperbok.peeparade.level;

import java.util.ArrayList;

import nl.jasperbok.peeparade.entity.Entity;
import nl.jasperbok.peeparade.entity.Cat;
import nl.jasperbok.peeparade.entity.Usable;
import nl.jasperbok.peeparade.entity.mob.Mob;
import nl.jasperbok.peeparade.level.Tile;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

/**
 * @author Jasper Bok <lunanoko@gmail.com>
 * @version 0.7
 * @since 2012-03-20
 */
public class TileEnvironment {
	/**
	 * If true, the TileEnvironment will draw the outlines of all the Tiles
	 * and Entities inside of the TileEnvironment. Costs a lot of FPS!
	 */
	private boolean drawBoundingBoxes = false;
	private TiledMap map;
	private String mapName;
	private int mapWidth;
	private int mapHeight;
	private int tileWidth;
	private int tileHeight;
	
	/**
	 * All the entities that don't fit in any other collection.
	 */
	private ArrayList<Entity> entities;
	/**
	 * All the entities that implement the Usable interface.
	 */
	private ArrayList<Usable> usableEntities;
	/**
	 * All the mobs.
	 */
	private ArrayList<Mob> mobs;
	/**
	 * All the tiles of the map. These tiles do not contain the image used to
	 * represent them. That's only stored inside the map itself.
	 */
	private Tile[][] tiles;
	/**
	 * The cats representing any players (humans as well as AI's).
	 */
	private ArrayList<Cat> players;
	/**
	 * A list containing all the entities (except tiles) inside the
	 * TileEnvironment. This is used to loop through all of them without
	 * having to loop through multiple lists.
	 */
	private ArrayList<Entity> allEntities;
	
	private TerritoryLayer territoryLayer;
	
	public TileEnvironment(String mapName) throws SlickException {
		this.map = new TiledMap("data/level/" + mapName + ".tmx");
		this.mapName = mapName;
		this.mapWidth = map.getWidth();
		this.mapHeight = map.getHeight();
		this.tileWidth = map.getTileWidth();
		this.tileHeight = map.getTileHeight();
		
		this.entities = new ArrayList<Entity>();
		this.usableEntities = new ArrayList<Usable>();
		this.mobs = new ArrayList<Mob>();
		this.players = new ArrayList<Cat>();
		this.allEntities = new ArrayList<Entity>();

		// Load specific parts of the map.
		MapLoader loader = new MapLoader();
		this.tiles = loader.loadTiles(map);
		
		territoryLayer = new TerritoryLayer(tiles);
	}
	
	/**
	 * Updates all of the entities inside the TileEnvironment.
	 * 
	 * This method first calls the updateEntities() methods, which in turn
	 * calls the update() method of all the entities stored in the
	 * allEntities list.
	 * Then the moveEntities() method is called, which updates the position
	 * of all the entities in allEntities according to their velocity.
	 * Finally, checkForCollisions() is called, which will check if any
	 * entities overlap and solve the collisions where they appear. 
	 * @param container The GameContainer this TileEnvironment is part of.
	 * @param delta The elapsed game time since the last update.
	 * @throws SlickException
	 */
	public void update(GameContainer container, int delta) throws SlickException {
		updateEntities(container.getInput(), delta);
		moveEntities();
		checkForCollisions();
	}
	
	/**
	 * Calls the update() method of all entities stored inside allEntities.
	 * 
	 * @param input An instance of the Input class.
	 * @param delta The elapsed game time since the last update.
	 * @throws SlickException
	 */
	private void updateEntities(Input input, int delta) throws SlickException {
		for (Entity ent: allEntities) {
			ent.update(input, delta);
		}
	}
	
	/**
	 * Checks for collisions between entities and entities and tiles.
	 */
	private void checkForCollisions() {
		ArrayList<Entity> collides = new ArrayList<Entity>();
		
		for (int i = 0; i < allEntities.size(); i++) {
			Entity first = allEntities.get(i);
			checkForTileCollisions(first); // Check whether the entity collides with a tile.
			for (int j = i + 1; j < allEntities.size(); j++) {
				Entity second = allEntities.get(j);
				// If there's a collision between them, move A back its velocity - overlap.
				// If a's velocity == 0, move B
				// Or move them both half the overlap... Dunno yet...
			}
		}
	}
	
	private void checkForTileCollisions(Entity ent) {
		/*
		int extraYTiles = (int)(ent.boundingBox.getMinY() % tileHeight);
		int extraXTiles = (int)(ent.boundingBox.getMinX() % tileWidth);
		if (extraYTiles != 0) extraYTiles = 1;
		if (extraXTiles != 0) extraXTiles = 1;
		int numHorizontalTiles = (int)Math.floor(ent.boundingBox.getWidth() / tileWidth) + extraXTiles;
		int numVerticalTiles = (int)Math.floor(ent.boundingBox.getHeight() / tileHeight) + extraYTiles;
		ArrayList<Tile> tilesToCheck = new ArrayList<Tile>();
		
		if (ent.velocity.getX() > 0) {
			// Going right.
			if (ent.velocity.getY() > 0) {
				// Going down-right.
			} else if (ent.velocity.getY() < 0) {
				// Going up-right.
			}
			
		} else if (ent.velocity.getX() < 0) {
			// Going left.
			if (ent.velocity.getY() > 0) {
				// Going down-left.
			} else if (ent.velocity.getY() < 0) {
				// Going up-left.
			}
		}
		if (ent.velocity.getY() > 0) {
			// Going down.
		} else if (ent.velocity.getY() < 0) {
			// Going up.
		}
		*/
		int numHorizontalTiles = (int)Math.floor(ent.boundingBox.getWidth() / tileWidth) + 1;
		int numVerticalTiles = (int)Math.floor(ent.boundingBox.getHeight() / tileHeight) + 1;
		int topTileX = (int)Math.floor(ent.boundingBox.getMinX() / tileWidth);
		int topTileY = (int)Math.floor(ent.boundingBox.getMinY() / tileHeight);
		Tile[][] tilesToCheck = new Tile[numHorizontalTiles][numVerticalTiles];
		
		// Add all the tiles the Entity is touching to the tilesToCheck Array.
		for (int x = 0; x < numHorizontalTiles; x++) {
			for (int y = 0; y < numVerticalTiles; y++) {
				tilesToCheck[x][y] = tiles[topTileX + x][topTileY + y];
			}
		}
		
		// Loop through the tilesToCheck Array and check for collisions.
		tileLoop: for (int x = 0; x < numHorizontalTiles; x++) {
			for (int y = 0; y < numVerticalTiles; y++) {
				// If the current Tile has the blocking property.
				if (tilesToCheck[x][y].isBlocking) {
					Vector2f adjustment = new Vector2f(0, 0);
					
					// If the left side of the Entity is overlapping add a positive force on the x axis.
					if (tilesToCheck[x][y].boundingBox.intersects(new Line(ent.boundingBox.getMinX(), ent.boundingBox.getMinY(), ent.boundingBox.getMinX(), ent.boundingBox.getMaxY()))) {
						adjustment.add(new Vector2f(1, 0));
					}
					// If the right side of the Entity is overlapping add a negative force on the x axis.
					if (tilesToCheck[x][y].boundingBox.intersects(new Line(ent.boundingBox.getMaxX(), ent.boundingBox.getMinY(), ent.boundingBox.getMaxX(), ent.boundingBox.getMaxY()))) {
						adjustment.add(new Vector2f(-1, 0));
					}
					// If the top side of the Entity is overlapping add a positive force on the y axis.
					if (tilesToCheck[x][y].boundingBox.intersects(new Line(ent.boundingBox.getMinX(), ent.boundingBox.getMinY(), ent.boundingBox.getMaxX(), ent.boundingBox.getMinY()))) {
						adjustment.add(new Vector2f(0, 1));
					}
					// If the bottom side of the Entity is overlapping add a negative force on the y axis.
					if (tilesToCheck[x][y].boundingBox.intersects(new Line(ent.boundingBox.getMinX(), ent.boundingBox.getMaxY(), ent.boundingBox.getMaxX(), ent.boundingBox.getMaxY()))) {
						adjustment.add(new Vector2f(0, -1));
					}
					
					Rectangle entRect = new Rectangle(ent.position.getX(), ent.position.getY(), ent.boundingBox.getWidth(), ent.boundingBox.getHeight());
					for (int i = 0; i < 50; i++) {
						ent.position.add(adjustment);
						entRect.setLocation(ent.position);
						if (!tilesToCheck[x][y].boundingBox.intersects(entRect)) {
							break;
						}
					}
					/*
					// Check whether the tile intersects with the Entities boundingBox.
					if (tilesToCheck[x][y].boundingBox.intersects(new Rectangle(ent.position.getX(), ent.position.getY(), ent.boundingBox.getWidth(), ent.boundingBox.getHeight()))) {
						//ent.setPosition(ent.position.getX() - ent.velocity.getX(), ent.position.getY() - ent.velocity.getY());
						ent.setPosition(ent.previousPosition.getX(), ent.previousPosition.getY());
						break tileLoop;
						/*
						// If the Entity was previously above the Tile.
						if (ent.previousPosition.getY() <= tilesToCheck[x][y].boundingBox.getMinY()) {
							ent.setPosition(ent.position.getX(), tilesToCheck[x][y].boundingBox.getMinY() - ent.boundingBox.getHeight() - ent.velocity.getY());
						}
						// If the Entity was previously below the Tile.
						if (ent.previousPosition.getY() >= tilesToCheck[x][y].boundingBox.getMaxY()) {
							ent.setPosition(ent.position.getX(), tilesToCheck[x][y].boundingBox.getMinY() + tilesToCheck[x][y].boundingBox.getHeight() - ent.velocity.getY());
						}
						//If the Entity is still colliding...
						if (tilesToCheck[x][y].boundingBox.intersects(ent.boundingBox)) {
							// If the Entity was previously right of the Tile.
							if (ent.previousPosition.getX() >= tilesToCheck[x][y].boundingBox.getMaxX()) {
								ent.setPosition(tilesToCheck[x][y].boundingBox.getMaxX(), ent.position.getY());
							}
							// If the Entity was previously left of the Tile.
							if (ent.previousPosition.getX() <= tilesToCheck[x][y].boundingBox.getMinX()) {
								ent.setPosition(tilesToCheck[x][y].boundingBox.getMinX() - ent.boundingBox.getWidth(), ent.position.getY());
							}
						}*/
					}
				}
			}
		}
		
		/*
		//int tileX = (int)Math.floor(ent.position.getX() / tileWidth);
		//int tileY = (int)Math.floor(ent.position.getY() / tileHeight);
		int tileX = (int)Math.floor(ent.boundingBox.getCenterX() / tileWidth);
		int tileY = (int)Math.floor(ent.boundingBox.getCenterY() / tileHeight);
		Vector2f entDisplacement = new Vector2f(ent.position.getX() - ent.previousPosition.getX(), ent.position.getY() - ent.previousPosition.getY());
		Tile tile = null;
		
		if (entDisplacement.getX() > 0) {
			// Moving right.
			tileX = (int)Math.floor(ent.boundingBox.getMaxX() / tileWidth);
			tileY = (int)Math.floor(ent.boundingBox.getCenterY() / tileHeight);
		}
		
		if (tiles[tileX][tileY].isBlocking) {
			tile = tiles[tileX][tileY];
		}
		
		// If a blocking tile is touching the Entity...
		if (tile != null) {
			System.out.println("COLLISION DETECTED!");
			
			if (entDisplacement.getX() > 0) {
				// Coming from the left.
				ent.position.x = ent.boundingBox.getWidth() + (tileX * tileWidth);
			} else if (entDisplacement.getX() < 0) {
				// Coming from the right.
				ent.position.x = tileWidth + (tileX * tileWidth);
			}
			
			if (entDisplacement.getY() > 0 ) {
				// Coming from above.
				ent.position.y = ent.boundingBox.getHeight() + (tileY * tileHeight);
			} else if (entDisplacement.getY() < 0) {
				// Coming from below.
				ent.position.y = tileHeight + (tileY * tileHeight);
			}
		}
		/*
		if (tiles[tileX][tileY].isBlocking) {
			// There's a collision so let's store the Tile in a local variable
			// so we can reference it using a shorter notation.
			tile = tiles[tileX][tileY];
			
			if (ent.velocity.getX() > 0) {
				// Coming from the left.
				ent.position.x = ent.boundingBox.getWidth() + (tileX * tileWidth);
			} else if (ent.velocity.getX() < 0) {
				// Coming from the right.
				ent.position.x = tileWidth + (tileX * tileWidth);
			}
			
			if (ent.velocity.getY() > 0 ) {
				// Coming from above.
				ent.position.y = ent.boundingBox.getHeight() + (tileY * tileHeight);
			} else if (ent.velocity.getY() < 0) {
				// Coming from below.
				ent.position.y = tileHeight + (tileY * tileHeight);
			}
			System.out.println("COLLISION!");
		} else {
			System.out.println("not colliding");
		}
		*/
	//}
	
	/**
	 * Updates the position of all entities in the level according to their
	 * velocity. Sets their velocity to (0, 0) afterwards.
	 */
	private void moveEntities() {
		for (Entity ent: allEntities) {
			ent.setPosition(ent.position.x + ent.velocity.x, ent.position.y + ent.velocity.y);
			ent.velocity = new Vector2f(0, 0);
		}
	}
	
	/**
	 * Add an entity to the list of entities.
	 * 
	 * @param ent The entity to add to the list.
	 */
	public void addEntity(Entity ent) {
		entities.add(ent);
		if (ent instanceof Usable) usableEntities.add((Usable) ent);
		updateEntityList();
	}
	
	/**
	 * Add a mob to the list of mobs.
	 * 
	 * @param mob The mob to add to the list.
	 */
	public void addMob(Mob mob) {
		mobs.add(mob);
		updateEntityList();
	}
	
	public void addPlayer(Cat player) {
		players.add(player);
		updateEntityList();
	}
	
	private void updateEntityList() {
		allEntities.addAll(entities);
		allEntities.addAll(mobs);
		allEntities.addAll(players);
	}
	
	/**
	 * Returns the first Usable who's 'use activation field' lies within the
	 * given rectangle.
	 * 
	 * @param rect The rectangle where the Usable should react on.
	 * @return Reference to the first Usable within the given rectangle.
	 */
	public Usable getUsableEntity(Rectangle rect) {
		for (Usable obj: usableEntities) {
			if (obj.canBeUsed(rect)) return obj;
		}
		return null;
	}
	
	public void render(GameContainer container, Graphics g) throws SlickException {
		map.render(0, 0);
		territoryLayer.render(container, g);
		
		// Render all the entities.
		for (Entity ent: allEntities) {
			ent.render(container, g);
		}
		
		// Render bounding boxes if this setting is enabled.
		if (drawBoundingBoxes) {
			for (Entity ent: allEntities) {
				g.drawRect(ent.position.getX(), ent.position.getY(), ent.boundingBox.getWidth(), ent.boundingBox.getHeight());
			}
			for (Tile[] tileRow: tiles) {
				for (Tile tile: tileRow) {
					g.drawRect(tile.position.getX(), tile.position.getY(), tile.boundingBox.getWidth(), tile.boundingBox.getHeight());
				}
			}
		}
	}
	
	/** GETTERS AND SETTERS **/
	
	public String getMapName() {
		return mapName;
	}
	
	public int getTileHeight() {
		return tileHeight;
	}
	
	public int getTileWidth() {
		return tileWidth;
	}
	
	/**
	 * Returns a Tile from the tile list.
	 * 
	 * @param xPos The x position of the Tile. Note that this is not a pixel
	 * value but a relative value.
	 * @param yPos The y position of the Tile. Note that this is not a pixel
	 * value but a relative value.
	 * @return Tile The matching Tile.
	 */
	public Tile getTile(int xPos, int yPos) {
		return (tiles[xPos][yPos]);
	}
}