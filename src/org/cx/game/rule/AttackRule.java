package org.cx.game.rule;

import org.cx.game.action.Attack;
import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;
import org.cx.game.magic.buff.AttackLockBuff;
import org.cx.game.widget.IGround;

public class AttackRule extends AbstractRule implements IRule {

	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		Corps attacked = (Corps)((Object[]) args[0])[0];
		Corps attack = getOwner().getOwner();
		
		IGround ground = getOwner().getOwner().getGround();
		Integer distance = ground.distance(attacked.getPosition(), attack.getPosition());
		if(AbstractCorps.Death_Status_Live.equals(attacked.getDeath().getStatus())
		&& AbstractCorps.Death_Status_Live.equals(attack.getDeath().getStatus())
		&& 1==distance){                                           //近身
			new AttackLockBuff(attack,attacked).effect();
		}
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
