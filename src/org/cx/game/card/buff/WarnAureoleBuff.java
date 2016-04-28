package org.cx.game.card.buff;

import org.cx.game.card.LifeCard;
import org.cx.game.card.buff.SimpleBuff;

public class WarnAureoleBuff extends SimpleBuff {

	private Integer immuneDamageRatio;
	
	/**
	 * 
	 * @param bout
	 * @param style
	 * @param type
	 * @param func
	 * @param immuneDamageRatio 增加免伤比 基准100
	 * @param life
	 */
	public WarnAureoleBuff(Integer bout, Integer immuneDamageRatio, LifeCard life) {
		super(bout, life);
		// TODO Auto-generated constructor stub
		this.immuneDamageRatio = immuneDamageRatio;
	}

	@Override
	public void before(Object[] args) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void effect() {
		// TODO Auto-generated method stub
		super.effect();
		
		affect();
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		addToKeepImmuneDamageRatio(immuneDamageRatio/100);
		
		super.affect(objects);
	}

}
