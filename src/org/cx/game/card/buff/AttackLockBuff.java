package org.cx.game.card.buff;

import java.util.List;

import org.cx.game.action.Random;
import org.cx.game.card.LifeCard;
import org.cx.game.card.magic.IMagic;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.intercepter.Intercepter;
import org.cx.game.tools.Debug;

public class AttackLockBuff extends Buff {

	private LifeCard attack = null;
	
	public AttackLockBuff(Integer bout, LifeCard attack, LifeCard life) {
		super(bout, life);
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
					if(attacked.equals(alb.getLocker())){
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

	public LifeCard getLocker() {
		return attack;
	}

}
