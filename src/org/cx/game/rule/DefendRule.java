package org.cx.game.rule;

import org.cx.game.action.CorpsUpgrade;
import org.cx.game.action.Defend;
import org.cx.game.action.Death;
import org.cx.game.corps.Corps;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.tools.Logger;
import org.cx.game.tools.Util;
import org.cx.game.widget.treasure.EmpiricValue;

public class DefendRule extends AbstractRule {
	

	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		Corps attack = (Corps) ((Object[]) args[0])[0];
		Corps defend = getOwner().getOwner();
		Boolean isFightBack = (Boolean) ((Object[]) args[0])[3];
		Integer attackMode = (Integer) ((Object[]) args[0])[4];
		
		Integer damageValue = (Integer) getOwner().getActionResult("damageValue");
		Boolean isCanFightBack = (Boolean) getOwner().getActionResult("isCanFightBack");
		
		Logger.debug(this, defend.getName()+" [受伤]; 伤害："+damageValue+"/ def: "+getOwner().getDef()+"/ 允许反击："+isCanFightBack);
		
		//造成的实际伤害
		Death death = defend.getDeath();
		death.setHp(Util.Sub, damageValue);
		
		if(death.getHp()<=0){
			defend.death();
			
			//经验值的基数与阶层对应的等级有关，一般来说一个阶层应该对应一个等级，经验值基数 = 阶层的等级所需经验值*?%
			Integer dRank = defend.getRank();
			Integer dLevel = defend.getUpgrade().getLevel();
			Integer baseEmp = 100;
			
			Integer aRank = attack.getRank();
			
			//如果死者阶层大于攻击者，则有经验加成
			Integer scale = (dRank-aRank)>0 ? 10*(dRank-aRank) : 100;
			
			CorpsUpgrade cu = (CorpsUpgrade) attack.getUpgrade();
			cu.setEmpiricValue(Util.Sum, baseEmp);
			
			if(cu.isUpgrade()){
				attack.upgrade();
			}
		}else{
			/*
			 * 反击
			 */
			if(isCanFightBack                                               //本回合还能否反击 
				&& !isFightBack                                             //这次攻击方式是否是反击
				&& CommonIdentifierE.Attack_Mode_Near.equals(attackMode)    //近战攻击方式
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
