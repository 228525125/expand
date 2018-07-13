package org.cx.game.widget.treasure;

import org.cx.game.action.IAction;
import org.cx.game.action.Picked;
import org.cx.game.corps.Corps;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.tools.Util;

/**
 * 资料类物品
 * @author chenxian
 *
 */
public class MineralTreasure extends Treasure {

	private Mineral mineral = null;
	
	public MineralTreasure(Integer gold, Integer wood, Integer stone, Integer ore) {
		// TODO Auto-generated constructor stub
		this.mineral = new Mineral(gold, wood, stone, ore);
	}
	
	public Mineral getMineral() {
		return this.mineral;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		String name = super.getName();
		name += "("+getMineral().get(CommonIdentifierE.Gold)+"/"+getMineral().get(CommonIdentifierE.Wood)+"/"+getMineral().get(CommonIdentifierE.Stone)+"/"+getMineral().get(CommonIdentifierE.Ore)+")";
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
			corps.getPlayer().setMineral(Util.Sum, getMineral());
		}
	}
}
