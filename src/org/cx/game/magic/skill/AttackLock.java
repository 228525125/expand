package org.cx.game.magic.skill;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.action.IAction;
import org.cx.game.corps.Corps;
import org.cx.game.magic.buff.AbstractBuff;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.magic.buff.AttackLockBuff;
import org.cx.game.magic.buff.TauntBuff;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.widget.Ground;

/**
 * 锁定攻击目标，该动作发生在attack时，不管是否击中目标
 * 锁定需要在反击之前，因此将以下方法移至attack中
 * 本对象暂不使用
 * @author chenxian
 *
 */
public class AttackLock extends PassiveSkill {

	public static final Integer AttackLock_ID = 100111;
	
	private Corps attacked = null;
	
	public AttackLock(Corps corps) {
		super(AttackLock_ID);
		// TODO Auto-generated constructor stub
		setOwner(corps);
	}
	
	@Override
	public Corps getOwner() {
		// TODO Auto-generated method stub
		return (Corps) super.getOwner();
	}

	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		List<AbstractBuff> buffs = getOwner().getNexusBuff(AttackLockBuff.class);
		for(AbstractBuff buff : buffs)
			buff.invalid();
		
		new AttackLockBuff(getOwner(),attacked).effect();
	}
	
	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		attacked = (Corps) ((Object[]) args[0])[0];
		
		List<AbstractBuff> buffs = attacked.getBuff(AttackLockBuff.class);
		Boolean exist = false;
		for(AbstractBuff buff : buffs){
			if(getOwner().equals(buff.getOwner())){
				exist = true;
				break;
			}
		}
		
		Boolean taunt = true;
		Ground ground = getOwner().getPlayer().getContext().getGround();
		
		if(getOwner().containsBuff(TauntBuff.class)){
			TauntBuff buff = (TauntBuff) getOwner().getBuff(TauntBuff.class);
			taunt = attacked.equals(buff.getTaunter());
		}
		
		Integer distance = ground.distance(attacked.getPosition(), getOwner().getPosition());
		if(CommonIdentifierE.Death_Status_Live == attacked.getDeath().getStatus()
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
