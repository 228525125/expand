package org.cx.game.widget.treasure;

import org.cx.game.action.IPicked;
import org.cx.game.action.Picked;
import org.cx.game.corps.Hero;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.tools.I18n;

/**
 * 装备物品
 * @author chenxian
 *
 */
public class TreasureEquipment extends Treasure implements ITreasure {

	private Integer type = null;
	private String name = null;
	private Integer atk = 0;
	private Integer def = 0;
	
	public TreasureEquipment(Integer type) {
		// TODO Auto-generated constructor stub
		this.type = type;
	}
	
	public Integer getType() {
		// TODO Auto-generated method stub
		return type;
	}
	
	public Integer getAtk() {
		return atk;
	}

	public void setAtk(Integer atk) {
		this.atk = atk;
	}

	public Integer getDef() {
		return def;
	}

	public void setDef(Integer def) {
		this.def = def;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		if(null==name){
			name = I18n.getMessage(this, getType(), "name");
			name += "("+this.atk+"/"+this.def+")";
		}
		return name;
	}
	
	private IPicked picked = null;
	
	@Override
	public IPicked getPicked() {
		// TODO Auto-generated method stub
		if(null==this.picked){
			IPicked picked = new TreasureEquipmentPicked();
			picked.setOwner(this);
			this.picked = picked;
		}
		return picked;
	}
	
	public class TreasureEquipmentPicked extends Picked implements IPicked {

		@Override
		public void action(Object... objects) throws RuleValidatorException {
			// TODO Auto-generated method stub
			super.action(objects);
			
			Hero hero = (Hero) objects[0];
			hero.addTreasure(getOwner());
		}
	}

}
