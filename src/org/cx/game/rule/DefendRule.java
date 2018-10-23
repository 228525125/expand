package org.cx.game.rule;

import org.cx.game.action.Defend;
import org.cx.game.action.Death;
import org.cx.game.corps.Corps;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.tools.Util;

public class DefendRule extends AbstractRule {
	

	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		Corps attack = (Corps) ((Object[]) args[0])[0];
		Corps defend = getOwner().getOwner();
		Boolean isFightBack = (Boolean) ((Object[]) args[0])[3];
		
		Integer damageValue = (Integer) getOwner().getActionResult("damageValue");
		Boolean isCanFightBack = (Boolean) getOwner().getActionResult("isCanFightBack");
		
		//造成的实际伤害
		Death death = defend.getDeath();
		death.setHp(Util.Sub, damageValue);
		
		if(death.getHp()<=0){
			defend.death();
		}else{
			/*
			 * 反击
			 */
			if(isCanFightBack                                               //本回合还能否反击 
				&& !isFightBack                                             //这次攻击方式是否是反击
				&& 0<defend.getAttack().getAtk()){                          //是否有攻击力 
				
				defend.getAttack().setFightBack(true);                 //设置为反击
				defend.attack(attack);
				defend.getAttack().setFightBack(false);                //反击结束
				defend.getDefend().setCanFightBack(false);           //每个回合只能反击一次
			}
		}
	}

	@Override
	public Class getInterceptable() {
		// TODO Auto-generated method stub
		return Defend.class;
	}
	
	@Override
	public Defend getOwner() {
		// TODO Auto-generated method stub
		return (Defend) super.getOwner();
	}

}
