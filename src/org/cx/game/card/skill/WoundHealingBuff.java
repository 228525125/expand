package org.cx.game.card.skill;

import org.cx.game.action.Random;
import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.Buff;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.observer.NotifyInfo;

/**
 * 伤口愈合
 * @author chenxian
 *
 */
public class WoundHealingBuff extends Buff {

	private Integer renewScale;
	
	/**
	 * 
	 * @param bout
	 * @param renewScale 每回合恢复HP总量的百分比
	 * @param life
	 */
	public WoundHealingBuff(Integer bout, Integer style, Integer type, Integer func, Integer renewScale, LifeCard life) {
		super(bout, style, type, func, life);
		// TODO Auto-generated constructor stub
		this.renewScale = renewScale;
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		Integer hp = getOwner().getHp();
		Integer cureValue = hp*renewScale/100;
		getOwner().getDeath().magicToHp(cureValue);
		
		super.affect(objects);
	}
	
	@Override
	public void finish(Object[] args) {
		// TODO Auto-generated method stub
		super.finish(args);
	}

	@Override
	public void before(Object[] args) {
		affect();
	}

}
