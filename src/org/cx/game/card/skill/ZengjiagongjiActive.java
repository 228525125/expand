package org.cx.game.card.skill;

import org.cx.game.card.buff.IBuff;
import org.cx.game.card.buff.ZengjiagongjiBuff;
import org.cx.game.exception.RuleValidatorException;

public class ZengjiagongjiActive extends ActiveSkill {

	public final static Integer ZengjiagongjiActive_ID = 10290002;
	
	private Integer atk = 0;
	private Integer bout = 0;
	
	public ZengjiagongjiActive(Integer consume, Integer cooldown, Integer bout, Integer atk) {
		super(ZengjiagongjiActive_ID, consume, cooldown);
		// TODO Auto-generated constructor stub
		this.bout = bout;
		this.atk = atk;
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
		
		IBuff buff = new ZengjiagongjiBuff(this.bout, this.atk, getOwner());
		buff.effect();
	}

}
