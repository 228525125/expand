package org.cx.game.rule;

import org.cx.game.action.Conjure;
import org.cx.game.corps.Corps;

public class ConjureRule extends AbstractRule {

	@Override
	public Class getInterceptable() {
		// TODO Auto-generated method stub
		return Conjure.class;
	}
	
	@Override
	public Conjure getOwner() {
		// TODO Auto-generated method stub
		return (Conjure) super.getOwner();
	}
	
	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		Corps owner = getOwner().getOwner();
		
		/*
		 * 判断移动攻击
		 */
		if(!getOwner().getOwner().getMobile()){
			getOwner().getOwner().getMove().setMoveable(false);
		}
		
		/*
		 * 一回合只能选择施法或攻击
		 */
		getOwner().getOwner().getAttack().setAttackable(false);
	}

}
