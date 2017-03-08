package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.effect.Declare;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.widget.IPlace;

/**
 * 战吼：消灭一种生物，并获得能力
 * @author chenxian
 *
 */
public class XiaomieshengwuzengjianengliDeclare extends Declare {

	public final static Integer XiaomieshengwuzengjianengliDeclare_ID = 10200015;

	private Integer atkUpValue = 0;
	private Integer atkUpTotal = 0;
	private Integer hpUpValue = 0;
	private Integer hpUpTotal = 0;
	private Integer stirps = 0;
	private Integer range = 0;
	private LifeCard lifeCard = null;
	
	public XiaomieshengwuzengjianengliDeclare(Integer atkUpValue, Integer hpUpValue, Integer stirps, Integer range) {
		super(XiaomieshengwuzengjianengliDeclare_ID);
		// TODO Auto-generated constructor stub
		this.atkUpValue = atkUpValue;
		this.hpUpValue = hpUpValue;
		this.stirps = stirps;
		this.range = range;
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		try {
			this.lifeCard.getDeath().action();
		} catch (RuleValidatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		getOwner().getAttack().addToAtk(this.atkUpValue);
		getOwner().getDeath().addToHp(this.hpUpValue);
		this.atkUpTotal += this.atkUpValue;
		this.hpUpTotal += this.hpUpValue;

	}
	
	@Override
	public Boolean isTrigger(Object[] args) {
		// TODO Auto-generated method stub
		IPlace place = (IPlace) ((Object[]) args[0])[0];
		this.lifeCard = queryTarget(place.getPosition(), this.range, false, false, this.stirps);
		return null!=lifeCard;
	}
	
	@Override
	public void invalid() {
		// TODO Auto-generated method stub
		super.invalid();
		
		getOwner().getAttack().addToAtk(-this.atkUpTotal);
		getOwner().getDeath().addToHp(-this.hpUpTotal);
		
		this.atkUpTotal = 0;
		this.hpUpTotal = 0;
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
