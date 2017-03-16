package nate.anderson.ai;

import nate.anderson.model.Creature;
import nate.anderson.model.CreatureFactory;

public class FungusAi extends CreatureAi {

	private CreatureFactory factory;
	private int spreadCount;
	
	public FungusAi (Creature creature, CreatureFactory factory) {
		super(creature);
		this.factory = factory;
	}
	
	@Override
	public void onUpdate() {
		if (spreadCount < 5 && Math.random() < 0.02) {
			spread();
		}
	}
	
	private void spread() {
		int x = creature.x + (int)(Math.random() * 11) - 5;
		int y = creature.y + (int)(Math.random() * 11) - 5;
		
		if (!creature.canEnter(x,y)) {
			return;
		}
		
		creature.doAction("spawn a child");
		
		Creature child = factory.newFungus();
		child.x = x;
		child.y = y;
		spreadCount ++; 
	}
	
}
