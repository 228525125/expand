package org.cx.game.magic.buff;

import java.util.List;

import org.cx.game.action.Random;
import org.cx.game.corps.Corps;
import org.cx.game.intercepter.AbstractIntercepter;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.magic.skill.TauntActive;
import org.cx.game.tools.Debug;

public class AttackLockBuff extends AbstractBuff {

	public static final Integer AttackLockBuff_ID = 10300001;
	public static final Integer Lock_Bout = 2;
	private Corps attack = null;
	
	
	public AttackLockBuff(Corps attack, Corps corps) {
		super(AttackLockBuff_ID, Lock_Bout, corps);
		// TODO Auto-generated constructor stub
		this.attack = (Corps) attack;
		setDuplication(true);         //允许同时被多人锁定
	}
	
	@Override
	public Corps getOwner() {
		// TODO Auto-generated method stub
		return (Corps) super.getOwner();
	}
	
	@Override
	public void invalid() {
		// TODO Auto-generated method stub
		super.invalid();
		
		attack.removeNexusBuff(this);
	}
	
	@Override
	public void effect() {
		// TODO Auto-generated method stub
		List<IBuff> buffs = getOwner().getBuff(AttackLockBuff.class);
		for(IBuff buff : buffs){
			AttackLockBuff alb = (AttackLockBuff) buff;				
			if(alb.getLocker().equals(attack)){
				alb.invalid();
				break;
			}
		}
		
		super.effect();
		
		attack.addNexusBuff(this);
		
		IIntercepter attackIn = new AbstractIntercepter() {
			
			private boolean invoke = true;
			
			@Override
			public void before(Object[] args) {
				// TODO Auto-generated method stub				
				Corps attacked = (Corps) ((Object[]) args[0])[0];
				
				Boolean locked = false;
				List<IBuff> buffs = getOwner().getBuff(AttackLockBuff.class);
				for(IBuff buff : buffs){
					AttackLockBuff alb = (AttackLockBuff) buff;
					if(attacked.equals(alb.getLocker())                //被攻击者是锁定对象
					|| attacked.containsSkill(TauntActive.class)){           //或者被攻击者具有嘲讽技能          
						locked = true;
						break;
					}
				}
				
				Integer chance = attack.getAttack().getLockChance() - getOwner().getMove().getFlee();
				if(!locked && Random.isTrigger(chance)){
					invoke = false;
					affect();
				}
			}
			
			@Override
			public Boolean isInvoke() {
				// TODO Auto-generated method stub
				return invoke;
			}
		};
		recordIntercepter(getOwner().getAttack(), attackIn);
		
		IIntercepter moveIn = new AbstractIntercepter() {
			
			private boolean invoke = true;
			
			@Override
			public void before(Object[] args) {
				// TODO Auto-generated method stub
				if(!Random.isTrigger(getOwner().getMove().getFlee())){
					invoke = false;
					affect();
				}
			}
			
			@Override
			public Boolean isInvoke() {
				// TODO Auto-generated method stub
				return invoke;
			}
		};
		recordIntercepter(getOwner().getMove(), moveIn);
	}

	/**
	 * 发起锁定的一方
	 * @return
	 */
	public Corps getLocker() {
		return attack;
	}

}
