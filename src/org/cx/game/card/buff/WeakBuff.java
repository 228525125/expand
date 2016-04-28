package org.cx.game.card.buff;

import org.cx.game.card.LifeCard;
import org.cx.game.card.buff.SimpleBuff;

public class WeakBuff extends SimpleBuff {

	private Integer atkDownScale = 0;
	
	public WeakBuff(Integer bout,
			Integer atkDownScale ,LifeCard life) {
		super(bout, life);
		// TODO Auto-generated constructor stub
		this.atkDownScale = atkDownScale;
	}
	
	@Override
	public void effect() {
		// TODO Auto-generated method stub
		super.effect();
		
		Integer atkDownValue = getOwner().getAttack().getAtk()*atkDownScale/100;
		addToKeepAtk(-atkDownValue);
		
		affect();
	}

}
