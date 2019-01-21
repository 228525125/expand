package org.cx.game.rule;

import java.util.List;

import org.cx.game.action.Activate;
import org.cx.game.action.Call;
import org.cx.game.corps.Corps;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.magic.buff.AttackLockBuff;
import org.cx.game.magic.buff.AbstractBuff;

public class ActivateRule extends AbstractRule {
	 
	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		Boolean activation = (Boolean)((Object[]) args[0])[0];
		
		Activate activate = getOwner();
		Corps owner = activate.getOwner();
		
		if(activation){
			List<AbstractBuff> buffs = owner.getNexusBuff(AttackLockBuff.class);  //清除锁定对象
			for(AbstractBuff buff : buffs){
				buff.invalid();
			}
			
			/*
			 * 激活相关状态
			 */
			owner.getAttack().setAttackable(true);
			owner.getConjure().setConjureable(true);
			owner.getMove().setMoveable(true);
			owner.getMove().setEnergy(owner.getEnergy());
			owner.getDefend().setCanFightBack(true);
		}else{
			
			/*
			 * 改变相关状态
			 */
			owner.getAttack().setAttackable(false);
			owner.getMove().setMoveable(false);
			owner.getConjure().setConjureable(false);
		}
	}
	
	@Override
	public Activate getOwner() {
		// TODO Auto-generated method stub
		return (Activate) super.getOwner();
	}
	
	@Override
	public Class getInterceptable() {
		// TODO Auto-generated method stub
		return Activate.class;
	}

}
