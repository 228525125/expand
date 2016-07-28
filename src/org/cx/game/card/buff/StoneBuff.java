package org.cx.game.card.buff;

import java.util.List;

import org.cx.game.card.LifeCard;
import org.cx.game.card.buff.AttackLockBuff;
import org.cx.game.card.buff.Buff;
import org.cx.game.card.buff.IBuff;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.intercepter.Intercepter;

/**
 * 石化
 * @author chenxian
 *
 */
public class StoneBuff extends Buff {

	public StoneBuff(Integer bout,
			LifeCard life) {
		super(bout, life);
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
		
		IIntercepter activateIn = new Intercepter() {    //当activate状态为true时，表示从石化中恢复
			
			@Override
			public void before(Object[] args) {
				// TODO Auto-generated method stub
				Boolean activation = (Boolean)((Object[])args[0])[0];
				
				if(activation)
					invalid();
			}
		};
		recordIntercepter(getOwner().getActivate(), activateIn);

		affect();
		
	}

}
