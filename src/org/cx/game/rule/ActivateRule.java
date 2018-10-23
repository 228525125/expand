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
		}/*else{
			
			 * 当活力值大于一次行动消耗时，再次获得一次行动
			 
			if(owner.getActivate().getVigour()>=Activate.ActivationConsume){
				owner.activate(true);
			}
		}*/
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
