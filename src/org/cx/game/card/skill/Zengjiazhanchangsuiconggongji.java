package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.effect.Declare;
import org.cx.game.widget.IPlace;
/**
 * 增加战场随从攻击
 * @author chenxian
 *
 */
public class Zengjiazhanchangsuiconggongji extends Declare {

	public final static Integer Zengjiazhanchangsuiconggongji_ID = 10200008;
	private Integer atkUpValue = 0;
	private Integer step = 1;
	private LifeCard lifeCard = null;
	
	public Zengjiazhanchangsuiconggongji(Integer atkUpValue) {
		super(Zengjiazhanchangsuiconggongji_ID);
		// TODO Auto-generated constructor stub
		this.atkUpValue = atkUpValue;
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		lifeCard.getAttack().addToAtk(atkUpValue);
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
		this.lifeCard = queryTarget(place.getPosition(), step);
		return null!=lifeCard;
	}

}
