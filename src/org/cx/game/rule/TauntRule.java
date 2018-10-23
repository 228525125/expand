package org.cx.game.rule;

import org.cx.game.action.Defend;
import org.cx.game.corps.Corps;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.magic.buff.TauntBuff;

public class TauntRule extends AbstractRule {

	private Boolean fightBack = null;
	
	@Override
	public void before(Object[] args) {
		// TODO Auto-generated method stub
		Corps owner = getOwner().getOwner();
		this.fightBack = getOwner().isCanFightBack();
		if(!owner.getBuff(TauntBuff.class).isEmpty()){
			getOwner().setCanFightBack(false);
		}
	}
	
	@Override
	public void finish(Object[] args) {
		// TODO Auto-generated method stub
		getOwner().setCanFightBack(fightBack);
	}
	
	@Override
	public Defend getOwner() {
		// TODO Auto-generated method stub
		return (Defend) super.getOwner();
	}
	
	@Override
	public Class getInterceptable() {
		// TODO Auto-generated method stub
		return Defend.class;
	}

}
