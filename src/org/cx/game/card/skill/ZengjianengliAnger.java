package org.cx.game.card.skill;

import org.cx.game.card.effect.Anger;
import org.cx.game.widget.IControlQueue;
import org.cx.game.widget.ControlQueue.Place;

/**
 * 激怒：增加攻击 或 士气
 * @author chenxian
 *
 */
public class ZengjianengliAnger extends Anger {

	public final static Integer ZengjianengliAnger_ID = 10200012;
	
	private Integer atkUpValue = 0;
	private Integer atkUpTotal = 0;
	private Boolean morale = false;
	
	public ZengjianengliAnger(Integer atkUpValue, Boolean morale) {
		super(ZengjianengliAnger_ID);
		// TODO Auto-generated constructor stub
		this.atkUpValue = atkUpValue;
		this.morale = morale;
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		if(0<atkUpValue){
			getOwner().getAttack().addToAtk(atkUpValue);
			this.atkUpTotal += this.atkUpValue;
		}
		
		if(morale){
			IControlQueue queue = getOwner().getPlayer().getContext().getControlQueue();
			Place place = queue.getPlace(getOwner());
			queue.moveToPrior(place);
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
	public void invalid() {
		// TODO Auto-generated method stub
		super.invalid();
		
		getOwner().getAttack().addToAtk(-this.atkUpTotal);
		this.atkUpTotal = 0;
	}

}
