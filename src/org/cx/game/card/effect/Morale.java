package org.cx.game.card.effect;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.PassiveSkill;
import org.cx.game.core.IContext;
import org.cx.game.widget.ControlQueue.Place;
import org.cx.game.widget.IControlQueue;
import org.cx.game.widget.IPlace;

/**
 * 士气高昂
 * @author chenxian
 *
 */
public class Morale extends PassiveSkill {

	public static final Integer Morale_ID = 10200005;
	private Boolean onoff = true; 
	
	public Morale() {
		super(Morale_ID);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setOwner(LifeCard life) {
		// TODO Auto-generated method stub
		super.setOwner(life);
		
		life.getActivate().addIntercepter(this);
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		if(onoff){
			IControlQueue queue = getOwner().getPlayer().getContext().getQueue();
			Place place = queue.getPlace(getOwner());
			queue.moveToPrior(place);
			
			onoff = false;
		}else{
			onoff = true;
		}
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
		affect();
	}

}
