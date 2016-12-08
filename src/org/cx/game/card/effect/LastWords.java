package org.cx.game.card.effect;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.PassiveSkill;

/**
 * 遗言，子类只需覆盖affect方法就行；
 * @author chenxian
 *
 */
public abstract class LastWords extends PassiveSkill {

	public LastWords(Integer id) {
		// TODO Auto-generated constructor stub
		super(id);
	}
	
	@Override
	public void setOwner(LifeCard life) {
		// TODO Auto-generated method stub
		super.setOwner(life);
		
		life.getDeath().addIntercepter(this);
	}

	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		affect();
	}

}
