package org.cx.game.card.buff;

import java.util.List;

import org.cx.game.card.LifeCard;
import org.cx.game.card.buff.AttackLockBuff;
import org.cx.game.card.buff.Buff;
import org.cx.game.card.buff.IBuff;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.intercepter.Intercepter;

public class StoneBuff extends Buff {

	public StoneBuff(Integer bout, Integer style, Integer type, Integer func,
			LifeCard life) {
		super(bout, style, type, func, life);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void effect() {
		// TODO Auto-generated method stub
		
		super.effect();
		
		/*
		 * 被石化后，锁定的目标全部取消
		 */
		List<IBuff> buffs = getOwner().getNexusBuff(AttackLockBuff.class);
		for(IBuff buff : buffs)
			buff.invalid();
		
		IIntercepter activateIn = new Intercepter("setActivate") {    //当activate状态为true时，表示从石化中恢复
			
			@Override
			public void before(Object[] args) {
				// TODO Auto-generated method stub
				if((Boolean)args[0])
					invalid();
			}
		};
		recordIntercepter(getOwner(), activateIn);

		affect();
		
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		//不能反击
		getOwner().getAttacked().setAttackBackChance(0);		
	}

}
