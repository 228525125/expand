package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.buff.IBuff;
import org.cx.game.card.buff.ZaochengshanghaizengjiagongjiDeclareBuff;
import org.cx.game.card.effect.Declare;
import org.cx.game.widget.IPlace;

/**
 * 战吼：对一个随从造成伤害，并使其获得攻击力
 * @author chenxian
 *
 */
public class ZaochengshanghaizengjiagongjiDeclare extends Declare {

	public final static Integer ZaochengshanghaizengjiagongjiDeclare_ID = 10200017;
	
	private Integer harm = 0;
	private Integer atkUpValue = 0;
	private Integer range = 0;
	private LifeCard lifeCard = null;
	
	public ZaochengshanghaizengjiagongjiDeclare(Integer harm, Integer atkUpValue, Integer range) {
		super(ZaochengshanghaizengjiagongjiDeclare_ID);
		// TODO Auto-generated constructor stub
		this.harm = harm;
		this.atkUpValue = atkUpValue;
		this.range = range;
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		IBuff buff = new ZaochengshanghaizengjiagongjiDeclareBuff(harm, atkUpValue, lifeCard);
		buff.effect();
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
		this.lifeCard = queryTarget(place.getPosition(), range, null, false, Stirps_Null);
		return null!=lifeCard;
	}

}
