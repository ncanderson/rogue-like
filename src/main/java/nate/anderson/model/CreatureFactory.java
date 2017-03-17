package nate.anderson.model;

import java.util.List;

import asciiPanel.AsciiPanel;
import nate.anderson.ai.FungusAi;
import nate.anderson.ai.PlayerAi;
import nate.anderson.world.World;

public class CreatureFactory {

	private World world;
	
	public CreatureFactory (World world) {
		this.world = world;
	}
	
	public Creature newPlayer(List<String> messages) {
		Creature player = new Creature(world, '@', AsciiPanel.brightWhite, 100, 20, 5);
		world.addAtEmptyLocation(player);
		new PlayerAi(player, messages);
		return player;
	}
	
	public Creature newFungus(){
	    Creature fungus = new Creature(world, 'f', AsciiPanel.green, 10, 0, 0);
	    world.addAtEmptyLocation(fungus);
	    new FungusAi(fungus, this);
	    return fungus;
	}
	
	
}
