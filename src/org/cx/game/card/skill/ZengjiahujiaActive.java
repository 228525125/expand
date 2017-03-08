package org.cx.game.card.skill;

import org.cx.game.exception.RuleValidatorException;

public class ZengjiahujiaActive extends ActiveSkill {
	
	public final static Integer ZengjiahujiaActive_ID = 10290001;
	
	private Integer armour = 0;

	public ZengjiahujiaActive(Integer consume, Integer cooldown, Integer armour) {
		super(ZengjiahujiaActive_ID, consume, cooldown);
		// TODO Auto-generated constructor stub
		this.armour = armour;
	}

	@Override
	public Integer getRange() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void useSkill(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.useSkill(objects);
		
		getOwner().affected(this);
	}
	
	@Override
	public void affect(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		getOwner().getAttacked().addToArmour(this.armour);
	}
}
