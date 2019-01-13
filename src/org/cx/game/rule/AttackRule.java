package org.cx.game.rule;

import java.util.Map;

import org.cx.game.action.Attack;
import org.cx.game.corps.Corps;
import org.cx.game.magic.buff.AttackLockBuff;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.tools.Logger;
import org.cx.game.tools.Util;
import org.cx.game.widget.AbstractGround;

public class AttackRule extends AbstractRule {
 
	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		Corps attacked = (Corps)((Object[]) args[0])[0];
		Corps attack = getOwner().getOwner();
		
		/*
		 * 蓄力归零
		 */
		attack.getGrow().setPower(0);
		
		AbstractGround ground = getOwner().getOwner().getGround();
		Integer distance = ground.distance(attacked.getPosition(), attack.getPosition());
		if(CommonIdentifierE.Death_Status_Live.equals(attacked.getDeath().getStatus())
		&& CommonIdentifierE.Death_Status_Live.equals(attack.getDeath().getStatus())
		&& 1==distance){                                           //近身
			new AttackLockBuff(attack,attacked).effect();
		}
		
		Integer atk = (Integer) getOwner().getActionResult("atk");
		Integer dmg = (Integer) getOwner().getActionResult("dmg");
		Boolean isFightBack = (Boolean) getOwner().getActionResult("isFightBack");
		Integer attackMode = (Integer) getOwner().getActionResult("attackMode");
		
		//Logger.debug(this, attack.getName()+" "+(isFightBack ? "[反击]" : "[攻击]")+attacked.getName()+"; atk:"+atk+"/dmg:"+Attack.IntegerToDamage(dmg)[0]+"-"+Attack.IntegerToDamage(dmg)[1]+"/mode:"+(attackMode.equals(115) ? "近战" : "远程"));
		
		attacked.defend(attack, atk, dmg, isFightBack, attackMode);
	}
	
	@Override
	public Attack getOwner() {
		// TODO Auto-generated method stub
		return (Attack) super.getOwner();
	}
	
	@Override
	public Class getInterceptable() {
		// TODO Auto-generated method stub
		return Attack.class;
	}

}
