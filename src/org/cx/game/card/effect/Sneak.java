package org.cx.game.card.effect;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.PassiveSkill;

/**
 * 潜行
 * @author chenxian
 *
 */
public abstract class Sneak extends PassiveSkill {

	@Override
	public void setOwner(LifeCard life) {
		// TODO Auto-generated method stub
		super.setOwner(life);
		
		life.getCall().addIntercepter(this);
	}
	
	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		affect();
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		getOwner().getMove().changeHide(true);
	}

}
