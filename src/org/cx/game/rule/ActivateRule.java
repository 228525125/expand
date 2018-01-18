package org.cx.game.rule;

import java.util.List;

import org.cx.game.action.Activate;
import org.cx.game.action.Call;
import org.cx.game.corps.Corps;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.magic.buff.AttackLockBuff;
import org.cx.game.magic.buff.IBuff;

public class ActivateRule extends AbstractRule implements IRule {
	
	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		Boolean activation = (Boolean)((Object[]) args[0])[0];
		
		Activate activate = getOwner();
		Corps owner = activate.getOwner();
		
		if(activation){
			List<IBuff> buffs = owner.getNexusBuff(AttackLockBuff.class);  //清除锁定对象
			for(IBuff buff : buffs){
				buff.invalid();
			}
		}else{
			/*
			 * 当活力值大于一次行动消耗时，再次获得一次行动
			 */
			if(owner.getActivate().getVigour()>=Activate.ActivationConsume){
				try {
					owner.activate(true);
				} catch (RuleValidatorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
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
