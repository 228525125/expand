package org.cx.game.rule;

import org.cx.game.action.Attacked;
import org.cx.game.action.IAttack;
import org.cx.game.action.IDeath;
import org.cx.game.corps.Corps;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.magic.buff.TauntBuff;

public class TauntRule extends Rule implements IRule {

	private Boolean fightBack = null;
	
	@Override
	public void before(Object[] args) {
		// TODO Auto-generated method stub
		Corps owner = getOwner().getOwner();
		this.fightBack = getOwner().getFightBack();
		if(!owner.getBuff(TauntBuff.class).isEmpty()){
			getOwner().setFightBack(false);
		}
	}
	
	@Override
	public void finish(Object[] args) {
		// TODO Auto-generated method stub
		getOwner().setFightBack(fightBack);
	}
	
	@Override
	public Attacked getOwner() {
		// TODO Auto-generated method stub
		return (Attacked) super.getOwner();
	}
	
	@Override
	public Class getInterceptable() {
		// TODO Auto-generated method stub
		return Attacked.class;
	}

}
