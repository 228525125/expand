package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.effect.LastWords;

/**
 * 亡语：对敌方造成伤害
 * @author chenxian
 *
 */
public class ZaochengshanghaiLastWords extends LastWords {

	public final static Integer ZaochengshanghaiLastWords_ID = 10200016;
	
	private Integer harm = 0;
	private Boolean hero = false;
	private Integer range = 0;
	
	private LifeCard lifeCard = null;
	
	public ZaochengshanghaiLastWords(Integer harm, Boolean hero, Integer range) {
		super(ZaochengshanghaiLastWords_ID);
		// TODO Auto-generated constructor stub
		this.harm = harm;
		this.hero = hero;
		this.range = range;
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		this.lifeCard.getAffected().magicHarm(this.harm);
	}
	
	@Override
	public Boolean isTrigger(Object[] args) {
		// TODO Auto-generated method stub
		this.lifeCard = queryTarget(getOwner().getContainerPosition(), range, false, hero, Stirps_Null);
		return null!=this.lifeCard;
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
