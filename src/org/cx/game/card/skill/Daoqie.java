package org.cx.game.card.skill;

import org.cx.game.action.Random;
import org.cx.game.card.LifeCard;

/**
 * 盗窃，增加玩家power
 * @author chenxian
 *
 */
public class Daoqie extends PassiveSkill {

	private Integer chance = 0;
	private Integer power = 0;
	
	/**
	 * 
	 * @param chance 触发几率
	 * @param resource 资源
	 */
	public Daoqie(Integer chance, Integer power) {
		// TODO Auto-generated constructor stub
		this.chance = chance;
		this.power = power;
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		getOwner().getPlayer().addToResource(power);
		
		super.affect(objects);
	}
	
	@Override
	public void setOwner(LifeCard life) {
		// TODO Auto-generated method stub
		super.setOwner(life);
		
		life.getAttack().addIntercepter(this);
	}
	
	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		super.after(args);
		
		if(Random.isTrigger(chance))
			affect();
	}
}
