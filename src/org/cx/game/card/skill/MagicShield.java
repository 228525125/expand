package org.cx.game.card.skill;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.ActiveSkill;
import org.cx.game.card.buff.IBuff;
import org.cx.game.card.buff.MagicShieldBuff;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.observer.NotifyInfo;

/**
 * 魔法护盾，能抵消部分伤害值
 * @author chenxian
 *
 */
public class MagicShield extends ActiveSkill{

	private Integer defendValue;      
	private Integer offsetScale;
	private Integer bout;
	
	/**
	 * 
	 * @param consume 
	 * @param cooldown
	 * @param applyType
	 * @param defendValue  免伤值
	 * @param offsetScale  免伤比例
	 * @param bout
	 * @param life
	 */
	public MagicShield(Integer consume, Integer cooldown, Integer velocity, Integer defendValue, Integer offsetScale, Integer bout) {
		super(consume, cooldown, velocity);
		// TODO Auto-generated constructor stub
		this.defendValue = defendValue;
		this.offsetScale = offsetScale;
		this.bout = bout;
	}

	@Override
	public Integer getRange() {
		// TODO Auto-generated method stub
		return getOwner().getAttack().getRange();
	}
	
	@Override
	public void affect(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		new MagicShieldBuff(bout, defendValue, offsetScale, getOwner()).effect();
	}
	
	@Override
	public void useSkill(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub		
		super.useSkill(objects);
		
		getOwner().affected(this);
	}
}
