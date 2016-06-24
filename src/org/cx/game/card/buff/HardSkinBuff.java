package org.cx.game.card.buff;

import org.cx.game.card.LifeCard;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.intercepter.Intercepter;

public class HardSkinBuff extends Buff {

	private Integer damageDownValue = 0;
	
	public HardSkinBuff(Integer bout, Integer damageDownValue, LifeCard life) {
		super(bout, life);
		// TODO Auto-generated constructor stub
		this.damageDownValue = damageDownValue;
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		Integer damage = (Integer) objects[0];
		damage += damageDownValue;
		damage = damage<0 ? damage : 0;
		
		objects[0] = damage;
	}

	@Override
	public void effect() {
		// TODO Auto-generated method stub
		super.effect();
		
		IIntercepter attackToDamageIn = new Intercepter("attackToDamage") {
			
			@Override
			public void before(Object[] args) {
				// TODO Auto-generated method stub
				affect(args);
			}
		};
		
		recordIntercepter(getOwner().getDeath(), attackToDamageIn);
	}
	
	
}
