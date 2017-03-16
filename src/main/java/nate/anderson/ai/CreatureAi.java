package nate.anderson.ai;

import nate.anderson.model.Creature;
import nate.anderson.model.Tile;

public class CreatureAi {

	protected Creature creature;
	
	public CreatureAi (Creature creature) {
		this.creature = creature;
		this.creature.setCreatureAi(this);
	}
	
	public void onEnter (int x, int y, Tile tile) {
		
	}
	
	public void onUpdate() {
		
	}
		

	public void onNotify(String string) {
		
	}
}
