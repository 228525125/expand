package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.buff.IBuff;
import org.cx.game.card.buff.TransmitBackBuff;
import org.cx.game.card.skill.ActiveSkill;
import org.cx.game.exception.RuleValidatorException;

/**
 * 传送.定
 * @author chenxian
 *
 */
public class TransmitBack extends ActiveSkill {

	private Integer bout;
	
	/**
	 * 
	 * @param consume
	 * @param cooldown
	 * @param velocity
	 * @param bout 返回bout回合之前的位置
	 */
	public TransmitBack(Integer consume, Integer cooldown, Integer velocity,
			Integer bout) {
		super(consume, cooldown, velocity);
		// TODO Auto-generated constructor stub
		this.bout = bout;
		setParameterTypeValidator(new Class[]{LifeCard.class});
	}

	@Override
	public Integer getRange() {
		// TODO Auto-generated method stub
		return getOwner().getAttack().getRange();
	}
	
	@Override
	public void useSkill(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.useSkill(objects);
		
		LifeCard life = (LifeCard) objects[0];
		
		IBuff buff = new TransmitBackBuff(bout, life.getContainerPosition(), life);
		buff.effect();
	}

}
