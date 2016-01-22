package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.Buff;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.intercepter.Intercepter;
import org.cx.game.observer.NotifyInfo;

/**
 * 变羊
 * @author chenxian
 *
 */
public class SheepBuff extends Buff {

	public SheepBuff(Integer bout, Integer style, Integer type, Integer func, LifeCard life) {
		super(bout, style, type, func, life);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		getOwner().getPlayer().getContext().done();       //直接跳过该回合
		
		super.affect(objects);
	}

	@Override
	public void effect() {
		// TODO Auto-generated method stub
		IIntercepter activateIn = new Intercepter("setActivate"){

			@Override
			public void finish(Object[] args) {
				// TODO Auto-generated method stub
				if((Boolean) args[0])
					affect();
			}
		};
		recordIntercepter(getOwner(), activateIn);
		
		IIntercepter attackedIn = new Intercepter(){

			@Override
			public void before(Object[] args) {
				// TODO Auto-generated method stub
				invalid();
			}
		};
		recordIntercepter(getOwner().getAttacked(), attackedIn);
		
		super.effect();
	}
}
