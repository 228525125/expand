package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.ActiveSkill;
import org.cx.game.card.buff.HolyBodyBuff;
import org.cx.game.card.buff.IBuff;
import org.cx.game.card.magic.IMagic;
import org.cx.game.exception.RuleValidatorException;

/**
 * 神圣护体
 * @author chenxian
 *
 */
public class HolyBody extends ActiveSkill {

	private Integer bout = 3;
	
	public HolyBody(Integer consume, Integer cooldown, Integer velocity,
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
	public void affect(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		LifeCard life = (LifeCard) objects[0];
		
		new HolyBodyBuff(bout, life).effect();;
	}

}
