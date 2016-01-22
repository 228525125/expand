package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.SimpleBuff;

public class WeakBuff extends SimpleBuff {

	private Integer atkDownScale = 0;
	
	public WeakBuff(Integer bout, Integer style, Integer type, Integer func,
			Integer atkDownScale ,LifeCard life) {
		super(bout, style, type, func, life);
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
