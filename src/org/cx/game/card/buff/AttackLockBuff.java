package org.cx.game.card.buff;

import java.util.List;

import org.cx.game.action.Random;
import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.TauntActive;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.intercepter.Intercepter;
import org.cx.game.tools.Debug;

public class AttackLockBuff extends Buff {

	public static final Integer AttackLockBuff_ID = 10300001;
	public static final Integer Lock_Bout = 2;
	private LifeCard attack = null;
	
	
	public AttackLockBuff(LifeCard attack, LifeCard life) {
		super(AttackLockBuff_ID, Lock_Bout, life);
		// TODO Auto-generated constructor stub
		this.attack = attack;
		setDuplication(true);         //允许同时被多人锁定
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		if(!Debug.isDebug)
			getOwner().getPlayer().getContext().done();
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
		
		IIntercepter attackIn = new Intercepter(){
			
			private boolean invoke = true;
			
			@Override
			public void before(Object[] args) {
				// TODO Auto-generated method stub				
				LifeCard attacked = (LifeCard) ((Object[]) args[0])[0];
				
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
		
		IIntercepter moveIn = new Intercepter() {
			
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
	public LifeCard getLocker() {
		return attack;
	}

}
