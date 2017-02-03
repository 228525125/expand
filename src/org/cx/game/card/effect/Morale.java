package org.cx.game.card.effect;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.PassiveSkill;
import org.cx.game.widget.ControlQueue.Place;
import org.cx.game.widget.IControlQueue;

/**
 * 士气高昂
 * @author chenxian
 *
 */
public class Morale extends PassiveSkill {

	public static final Integer Morale_ID = 10200003;
	
	public Morale() {
		// TODO Auto-generated constructor stub
		super(Morale_ID);
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
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		affect();
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
