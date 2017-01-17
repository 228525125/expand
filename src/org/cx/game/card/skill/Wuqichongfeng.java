package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.widget.IControlQueue;
import org.cx.game.widget.ControlQueue.Place;

public class Wuqichongfeng extends PassiveSkill {

	public final static Integer Wuqichongfeng_ID = 10200007;
	
	public Wuqichongfeng() {
		super(Wuqichongfeng_ID);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setOwner(LifeCard life) {
		// TODO Auto-generated method stub
		super.setOwner(life);
		
		life.getCall().addIntercepter(this);
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		IControlQueue queue = getOwner().getPlayer().getContext().getQueue();
		Place place = queue.getPlace(getOwner());
		queue.moveToPrior(place);
	}

	@Override
	public void before(Object[] args) {
		// TODO Auto-generated method stub
		LifeCard hero = getOwner().getPlayer().getHeroCard();
		if(null!=hero.getAttack().getWeapon())
			affect();
	}

	@Override
	public void finish(Object[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub

	}

}
