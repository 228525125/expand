package org.cx.game.card.effect;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.PassiveSkill;

/**
 * 愤怒
 * @author chenxian
 *
 */
public abstract class Anger extends PassiveSkill {
	
	@Override
	public void setOwner(LifeCard life) {
		// TODO Auto-generated method stub
		super.setOwner(life);
		
		life.getDeath().addIntercepter(this);
	}
	
	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		Integer hp = (Integer)args[0];
		if(hp<0)
			affect();
	}
	
	@Override
	public String getIntercepterMethod() {
		// TODO Auto-generated method stub
		return "addToHp";
	}

}
