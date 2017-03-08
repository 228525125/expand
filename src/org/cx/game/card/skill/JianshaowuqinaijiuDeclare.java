package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.effect.Declare;
import org.cx.game.widget.IPlace;

public class JianshaowuqinaijiuDeclare extends Declare {

	public final static Integer JianshaowuqinaijiuDeclare_ID = 10200013;
	
	private Integer wearDownValue = 0;
	private Integer range = 0;
	private LifeCard hero = null;
	
	public JianshaowuqinaijiuDeclare(Integer wearDownValue, Integer range) {
		super(JianshaowuqinaijiuDeclare_ID);
		// TODO Auto-generated constructor stub
		this.wearDownValue = wearDownValue;
		this.range = range;
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		this.hero.getAttack().getWeapon().addToWear(wearDownValue);
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
	public Boolean isTrigger(Object[] args) {
		// TODO Auto-generated method stub
		IPlace place = (IPlace) ((Object[]) args[0])[0];
		this.hero = queryTarget(place.getPosition(), this.range, false, true, Stirps_Null);
		return null!=this.hero && null!=this.hero.getAttack().getWeapon();
	}

}
