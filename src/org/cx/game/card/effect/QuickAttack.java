package org.cx.game.card.effect;

import java.util.Map;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.PassiveSkill;
import org.cx.game.widget.ControlQueue.Place;
import org.cx.game.widget.IControlQueue;

/**
 * 速攻
 * @author chenxian
 *
 */
public class QuickAttack extends PassiveSkill {

	@Override
	public void setOwner(LifeCard life) {
		// TODO Auto-generated method stub
		super.setOwner(life);
		
		life.getCall().addIntercepter(this);
	}

	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		IControlQueue queue = getOwner().getPlayer().getContext().getQueue();
		Place place = queue.getPlace(getOwner());
		queue.moveToPrior(place);
	}

	@Override
	public void before(Object[] args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void finish(Object[] args) {
		// TODO Auto-generated method stub
		
	}

}
