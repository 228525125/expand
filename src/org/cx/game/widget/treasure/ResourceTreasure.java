package org.cx.game.widget.treasure;

import org.cx.game.action.IAction;
import org.cx.game.action.Picked;
import org.cx.game.corps.Corps;
import org.cx.game.tools.CommonIdentifierE;

/**
 * 资料类物品
 * @author chenxian
 *
 */
public class ResourceTreasure extends Treasure {

	private Resource resource = null;
	
	public ResourceTreasure(Integer gold, Integer wood, Integer stone, Integer ore) {
		// TODO Auto-generated constructor stub
		this.resource = new Resource(gold, wood, stone, ore);
	}
	
	public Resource getResource() {
		return resource;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		String name = super.getName();
		name += "("+getResource().get(CommonIdentifierE.Gold)+"/"+getResource().get(CommonIdentifierE.Wood)+"/"+getResource().get(CommonIdentifierE.Stone)+"/"+getResource().get(CommonIdentifierE.Ore)+")";
		return name;
	}
	
	private Picked picked = null;
	
	@Override
	public Picked getPicked() {
		// TODO Auto-generated method stub
		if(null==this.picked){
			Picked picked = new TreasureResourcePicked();
			picked.setOwner(this);
			this.picked = picked;
		}
		return picked;
	}

	public class TreasureResourcePicked extends Picked implements IAction {

		@Override
		public void action(Object... objects) {
			// TODO Auto-generated method stub
			super.action(objects);
			
			Corps corps = (Corps) objects[0];
			corps.getPlayer().addToResource(getResource());
		}
	}
}
