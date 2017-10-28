package nate.anderson.world;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import nate.anderson.model.Creature;
import nate.anderson.model.Tile;

public class World {

	private Tile[][][] tiles;
	private int width;
	private int height;
	private int zLevel;
	
	private List<Creature> creatures;
	
	public int width() {
		return width;
	}
	
	public int height() {
		return height;
	}
	
	public World(Tile[][][] tiles) {
		this.tiles = tiles;
		this.width = tiles.length;
		this.height = tiles[0].length;
		this.zLevel = tiles[1].length;
		this.creatures = new ArrayList<Creature>();
	}
	
	public Tile tile(int x, int y, int z) {
		if (x < 0 || x >= width || y < 0 || y >= height)
			return Tile.BOUNDS;
		else
			return tiles[x][y][z];
	}
	
	public char glyph(int x, int y, int z) {
		return tile(x, y, z).glyph();
	}
	
	public Color color(int x, int y, int z) {
		return tile(x, y, z).color();
	}
	
	/**
	 * Creature populating
	 */
	public Creature creature (int x, int y) {
		for (Creature c : creatures) {
			if (c.x == x && c.y == y) 
				return c;
		}
		return null;
	}
	
	
	/**
	 * Player / monster methods
	 * 
	 */
	public void addAtEmptyLocation(Creature creature) {
		int x;
		int y;
		int z;
		
		do {
			x = (int)(Math.random() * width);
			y = (int)(Math.random() * height);
			z = (int)(Math.random() * zLevel);
		}
		while (!tile(x,y, z).isGround());
		
		creature.x = x;
		creature.y = y;
		creature.z = z;
		creatures.add(creature);
	}
	
	public void dig (int x, int y, int z) {
		if (tile(x, y, z).isDiggable())
			tiles[x][y][z] = Tile.FLOOR;
	}
	
	public void remove(Creature other) {
		creatures.remove(other);
	}
	
	public void update() {
		List<Creature> toUpdate = new ArrayList<Creature>(creatures);
		for (Creature creature : toUpdate) {
			creature.update();
		}
	}
	
	
}
