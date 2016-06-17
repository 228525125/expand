package org.cx.game.card.buff;

import org.cx.game.card.LifeCard;

public class ZhufuBuff extends SimpleBuff {

	private Integer atkUpScale = 0;
	
	public ZhufuBuff(Integer bout, Integer atkUpScale, LifeCard life) {
		super(bout, life);
		// TODO Auto-generated constructor stub
		this.atkUpScale = atkUpScale;
	}
	
	@Override
	public void effect() {
		// TODO Auto-generated method stub
		super.effect();
		
		addToKeepAtk(getOwner().getAttack().getAtk()*atkUpScale/100);
		
		affect();
	}

}
