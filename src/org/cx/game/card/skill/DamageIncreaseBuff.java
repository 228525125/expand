package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.Buff;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.intercepter.Intercepter;
import org.cx.game.observer.NotifyInfo;

/**
 * 伤害加深
 * @author chenxian
 *
 */
public class DamageIncreaseBuff extends Buff {

	private Integer scale;
	
	/**
	 * 
	 * @param bout
	 * @param scale 每次递增比例
	 * @param life
	 */
	public DamageIncreaseBuff(Integer bout, Integer style, Integer type, Integer func, Integer scale, LifeCard life) {
		super(bout, style, type, func, life);
		// TODO Auto-generated constructor stub
		setDuplication(true);       //默认是false
		this.scale = scale;
	}
	
	private LifeCard attack = null;
	
	@Override
	public void effect() {
		// TODO Auto-generated method stub		
		IIntercepter attackedIn = new Intercepter(){

			@Override
			public void before(Object[] args) {
				// TODO Auto-generated method stub
				LifeCard life = (LifeCard) ((Object[]) args[0])[0];
				if(life.containsSkill(DamageIncrease.class))
					attack = life;
				else
					attack = null;
			}
		};
		recordIntercepter(getOwner().getAttacked(), attackedIn);
		
		IIntercepter deathIn = new Intercepter("attackToDamage"){

			@Override
			public void before(Object[] args) {
				// TODO Auto-generated method stub
				if(null!=attack)
					affect(args);
			}
		};
		recordIntercepter(getOwner().getDeath(), deathIn);
		
		super.effect();
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		Integer d = (Integer) objects[0];
		objects[0] = d*(100+scale)/100;
		
		super.affect(objects);
	}

}
