package nate.anderson.model;

import java.awt.Color;

import nate.anderson.ai.CreatureAi;
import nate.anderson.world.World;

public class Creature {

	private World world;
	private CreatureAi ai;
	
	public int x;
	public int y;
	public int z;
	
	private char glyph;
	private Color color;
	
	private int maxHp;
	private int hp;
	private int attackValue;
	private int defenseValue;
	
	public char glyph() {
		return glyph;
	}
	
	public Color color() {
		return color;
	}
	
	public Creature (World world, char glyph, Color color, int maxHp, int attackValue, int defenseValue) {
		this.world = world;
		this.glyph = glyph;
		this.color= color;
		this.maxHp = maxHp;
		this.hp = maxHp;
		this.attackValue = attackValue;
		this.defenseValue = defenseValue;
	}
	
	public void setCreatureAi (CreatureAi ai) {
		this.ai = ai;
	}
	
	public void attack(Creature other) {
		int amount = Math.max(0, attackValue() - other.defenseValue());
		
		amount = (int)(Math.random() * amount) + 1;
		
		other.modifyHp(-amount);
		
		notify("You attack the '%s' for %d damage.", other.glyph, amount);
		other.notify("The '%s' attacks you for %d damage.", glyph, amount);
	}
	
	public void modifyHp(int amount) {
		hp += amount;
		
		if (hp < 1) {
			doAction("die");
			world.remove(this);
		}
	}
	
	public int maxHp() { 
		return maxHp;
	}
	
	public int hp() {
		return hp;
	}
	
	public int attackValue() {
		return attackValue;
	}
	
	public int defenseValue() {
		return defenseValue;
	}
	
	public void dig(int wx, int wy, int wz) {
		world.dig(wx, wy, wz);
	}
	
	public void moveBy (int mx, int my, int mz) {
		Creature other = world.creature(x + mx,  y + my);
		
		if (other == null) {
			ai.onEnter(x + mx, y + my, world.tile(x + mx,y + my, z + mz));
		}
		else {
			attack(other);
		}
	}
	
	public boolean canEnter(int wx, int wy, int wz) {
		return world.tile(wx, wy, wz).isGround() && world.creature(wx, wy) == null;
	}
	
	public void update() {
		ai.onUpdate();
	}
	
	public void notify(String message, Object ... params) {
		ai.onNotify(String.format(message,  params));
	}
	
	public void doAction(String message, Object ... params) {
		int range = 9;
		for (int ox = -range; ox < range + 1; ox++) {
			for (int oy = -range; oy < range + 1; oy++) {
				if (ox * ox + oy * oy > range * range) {
					continue;
				}
				
				Creature other = world.creature(x + ox,  y + oy);
				
				if (other == null) {
					continue;
				}
				if (other == this) {
					other.notify("You " + message + ".", params);
				}
				else {
					other.notify(String.format("The '%s' %s.", glyph, makeSecondPerson(message)), params);
				}
			}
		}
	}
	
	private String makeSecondPerson(String text) {
		String[] words = text.split(" ");
		words[0] = words[0] + "s";
		
		StringBuilder builder = new StringBuilder();
		for (String word : words) {
			builder.append(" ");
			builder.append(word);
		}
		
		return builder.toString().trim();
	}
}


















