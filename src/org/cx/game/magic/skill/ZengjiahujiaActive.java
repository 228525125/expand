package org.cx.game.magic.skill;

import org.cx.game.exception.RuleValidatorException;

public class ZengjiahujiaActive extends ActiveSkill {
	
	public final static Integer ZengjiahujiaActive_ID = 10290001;
	
	private Integer armour = 0;

	public ZengjiahujiaActive(Integer cooldown, Integer armour) {
		super(ZengjiahujiaActive_ID, cooldown);
		// TODO Auto-generated constructor stub
		this.armour = armour;
	}

	@Override
	public Integer getRange() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void useSkill(Object... objects) {
		// TODO Auto-generated method stub
		super.useSkill(objects);
		
		getOwner().affected(this);
	}
}
