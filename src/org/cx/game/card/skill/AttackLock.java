package org.cx.game.card.skill;

import java.util.List;

import org.cx.game.action.IDeath;
import org.cx.game.card.LifeCard;
import org.cx.game.card.buff.AttackLockBuff;
import org.cx.game.card.buff.IBuff;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.widget.IGround;

/**
 * 锁定攻击目标，该动作发生在attack时，不管是否击中目标
 * 锁定需要在反击之前，因此将以下方法移至attack中，本对象暂不使用
 * @author chenxian
 *
 */
public class AttackLock extends PassiveSkill {

	private LifeCard attacked = null;
	
	public AttackLock(LifeCard life) {
		super();
		// TODO Auto-generated constructor stub
		setOwner(life);
	}

	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		List<IBuff> buffs = getOwner().getNexusBuff(AttackLockBuff.class);
		for(IBuff buff : buffs)
			buff.invalid();
		
		new AttackLockBuff(2,getOwner(),attacked).effect();
	}
	
	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		attacked = (LifeCard) ((Object[]) args[0])[0];
		
		List<IBuff> buffs = attacked.getBuff(AttackLockBuff.class);
		Boolean exist = false;
		for(IBuff buff : buffs){
			if(getOwner().equals(buff.getOwner())){
				exist = true;
				break;
			}
		}
		
		IGround ground = getOwner().getPlayer().getGround();
		Integer distance = ground.easyDistance(attacked.getContainerPosition(), getOwner().getContainerPosition());
		if(IDeath.Status_Live == attacked.getDeath().getStatus()
		&& 1==distance                                           //近身
		&& !exist)                                               //判断是否被锁定过                                                 
			affect();
	}
}
