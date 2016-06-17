package org.cx.game.card.buff;

import org.cx.game.card.LifeCard;

public class JingzhunBuff extends SimpleBuff {

	private Integer rangeUpValue = 0;
	
	public JingzhunBuff(Integer bout, Integer rangeUpValue, LifeCard life) {
		super(bout, life);
		// TODO Auto-generated constructor stub
		this.rangeUpValue = rangeUpValue;
	}
	
	@Override
	public void effect() {
		// TODO Auto-generated method stub
		super.effect();
		
		addToKeepRange(rangeUpValue);
		
		affect();
	}

}
