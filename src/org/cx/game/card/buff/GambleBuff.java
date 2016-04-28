package org.cx.game.card.buff;

import org.cx.game.card.LifeCard;
import org.cx.game.card.buff.SimpleBuff;
import org.cx.game.observer.NotifyInfo;

public class GambleBuff extends SimpleBuff {
	
	private Integer downImmuneDamageRatio = 0;
	private Integer speedChance = 0;
	
	/**
	 * 
	 * @param bout
	 * @param style
	 * @param type
	 * @param func
	 * @param downImmuneDamageRatio 免伤比降低值 
	 * @param life
	 */
	public GambleBuff(Integer bout, Integer downImmuneDamageRatio, LifeCard life) {
		super(bout, life);
		// TODO Auto-generated constructor stub
		this.downImmuneDamageRatio = downImmuneDamageRatio;
		this.speedChance = life.getAttack().getSpeedChance();
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
		addToKeepImmuneDamageRatio(downImmuneDamageRatio);
		getOwner().getAttack().setSpeedChance(0);
		
		super.affect(objects);
	}
}
