package org.cx.game.card.effect;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.PassiveSkill;

/**
 * 宣言，子类将宣言效果写在affect方法里面，将触发条件写在getTrigger方法里面；
 * @author chenxian
 *
 */
public abstract class Declare extends PassiveSkill {
	
	public abstract Boolean getTrigger();
	
	public Declare(Integer id) {
		// TODO Auto-generated constructor stub
		super(id);
	}
	
	@Override
	public void setOwner(LifeCard life) {
		// TODO Auto-generated method stub
		super.setOwner(life);
		
		life.getCall().addIntercepter(this);
	}
	
	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		if(getTrigger())
			affect();
	}
}
