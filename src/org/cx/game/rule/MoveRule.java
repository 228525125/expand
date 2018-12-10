package org.cx.game.rule;

import org.cx.game.action.Move;
import org.cx.game.corps.Corps;
import org.cx.game.tools.CommonIdentifierE;

public class MoveRule extends AbstractRule {

	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		Corps owner = getOwner().getOwner();
		
		/*
		 * 蓄力归零
		 */
		owner.getGrow().setPower(0);
		
		/*
		 * 远程单位，疲劳惩罚
		 */
		if(CommonIdentifierE.Attack_Mode_Far.equals(owner.getAttackMode())){
			owner.getAttack().setAttackable(false);
		}
	}
	
	@Override
	public Class getInterceptable() {
		// TODO Auto-generated method stub
		return Move.class;
	}
	
	@Override
	public Move getOwner() {
		// TODO Auto-generated method stub
		return (Move) super.getOwner();
	}

}
