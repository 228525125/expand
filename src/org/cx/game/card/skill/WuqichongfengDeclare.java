package org.cx.game.card.skill;

import org.cx.game.card.effect.Declare;
import org.cx.game.widget.IControlQueue;
import org.cx.game.widget.ControlQueue.Place;
import org.cx.game.widget.IWeapon;

/**
 * 战吼：当你装备一把武器时，该随从获得冲锋
 * @author chenxian
 *
 */
public class WuqichongfengDeclare extends Declare {

	public final static Integer WuqichongfengDeclare_ID = 10200007;
	
	private IWeapon weapon = null;
	
	public WuqichongfengDeclare() {
		super(WuqichongfengDeclare_ID);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		IControlQueue queue = getOwner().getPlayer().getContext().getControlQueue();
		Place place = queue.getPlace(getOwner());
		queue.moveToPrior(place);
	}

	@Override
	public void finish(Object[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public Boolean isTrigger(Object[] args) {
		// TODO Auto-generated method stub
		this.weapon = getOwner().getPlayer().getHeroCard().getAttack().getWeapon();
		return null!=this.weapon;
	}

	@Override
	public void before(Object[] args) {
		// TODO Auto-generated method stub
		
	}

}
