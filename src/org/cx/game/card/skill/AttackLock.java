package org.cx.game.card.skill;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.action.IDeath;
import org.cx.game.card.LifeCard;
import org.cx.game.card.buff.AttackLockBuff;
import org.cx.game.card.buff.IBuff;
import org.cx.game.card.buff.TauntBuff;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.widget.IGround;

/**
 * 锁定攻击目标，该动作发生在attack时，不管是否击中目标
 * 锁定需要在反击之前，因此将以下方法移至attack中
 * 本对象暂不使用
 * @author chenxian
 *
 */
public class AttackLock extends PassiveSkill {

	public static final Integer AttackLock_ID = 100111;
	
	private LifeCard attacked = null;
	
	public AttackLock(LifeCard life) {
		super(AttackLock_ID);
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
		
		new AttackLockBuff(getOwner(),attacked).effect();
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
		
		Boolean taunt = true;
		IGround ground = getOwner().getPlayer().getGround();
		
		if(getOwner().containsBuff(TauntBuff.class)){
			TauntBuff buff = (TauntBuff) getOwner().getBuff(TauntBuff.class);
			taunt = attacked.equals(buff.getTaunter());
		}
		
		Integer distance = ground.distance(attacked.getContainerPosition(), getOwner().getContainerPosition());
		if(IDeath.Status_Live == attacked.getDeath().getStatus()
		&& 1==distance                                           //近身
		&& !exist                                                //判断是否被锁定过
		&& taunt)                                               //判断是否受到身边具有嘲讽的敌人的影响                                
			affect();
	}

	@Override
	public void finish(Object[] args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void before(Object[] args) {
		// TODO Auto-generated method stub
		
	}
}
