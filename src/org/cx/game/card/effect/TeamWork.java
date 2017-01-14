package org.cx.game.card.effect;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.PassiveSkill;

/**
 * 协助
 * @author chenxian
 *
 */
public abstract class TeamWork extends PassiveSkill {
	
	public Boolean isTrigger(){
		return getOwner().getPlayer().getCallCountOfBout()>0;
	}
	
	public TeamWork(Integer id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setOwner(LifeCard life) {
		// TODO Auto-generated method stub
		super.setOwner(life);
		
		life.getCall().addIntercepter(this);
	}

	@Override
	public void before(Object[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void finish(Object[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		if(isTrigger())
			affect();
	}

}
