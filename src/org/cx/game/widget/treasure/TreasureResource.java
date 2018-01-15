package org.cx.game.widget.treasure;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.action.IPicked;
import org.cx.game.action.Picked;
import org.cx.game.core.IPlayer;
import org.cx.game.corps.Corps;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.widget.IGround;

/**
 * 资料类物品
 * @author chenxian
 *
 */
public class TreasureResource extends Treasure implements ITreasure {

	private IResource resource = null;
	
	public TreasureResource(Integer gold, Integer wood, Integer stone, Integer ore) {
		// TODO Auto-generated constructor stub
		this.resource = new Resource(gold, wood, stone, ore);
	}
	
	public IResource getResource() {
		return resource;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		String name = super.getName();
		name += "("+getResource().get(IResource.Gold)+"/"+getResource().get(IResource.Wood)+"/"+getResource().get(IResource.Stone)+"/"+getResource().get(IResource.Ore)+")";
		return name;
	}
	
	private IPicked picked = null;
	
	@Override
	public IPicked getPicked() {
		// TODO Auto-generated method stub
		if(null==this.picked){
			IPicked picked = new TreasureResourcePicked();
			picked.setOwner(this);
			this.picked = picked;
		}
		return picked;
	}

	public class TreasureResourcePicked extends Picked implements IPicked {

		@Override
		public void action(Object... objects) throws RuleValidatorException {
			// TODO Auto-generated method stub
			super.action(objects);
			
			Corps corps = (Corps) objects[0];
			corps.getPlayer().addToResource(getResource());
		}
	}
}
