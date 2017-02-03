package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.buff.IBuff;
import org.cx.game.card.buff.ZengjiazhanchangsuiconggongjiBuff;
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
	private Integer bout = 0;
	
	public Zengjiazhanchangsuiconggongji(Integer atkUpValue, Integer bout) {
		super(Zengjiazhanchangsuiconggongji_ID);
		// TODO Auto-generated constructor stub
		this.atkUpValue = atkUpValue;
		this.bout = bout;
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		IBuff buff = new ZengjiazhanchangsuiconggongjiBuff(atkUpValue, bout, this.lifeCard);
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
		this.lifeCard = queryTarget(place.getPosition(), step);
		return null!=lifeCard;
	}

}
