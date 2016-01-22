package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;

/**
 * 魔法盾
 * @author chenxian
 *
 */
public class MagicShieldBuff extends DamageResistBuff {

	public MagicShieldBuff(Integer bout, Integer style, Integer type, Integer func, Integer defendValue,
			Integer offsetScale, LifeCard life) {
		super(bout, defendValue, offsetScale, style, type, func, life);
		// TODO Auto-generated constructor stub
	}

}
