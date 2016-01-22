package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.ActiveSkill;
import org.cx.game.card.skill.IBuff;
import org.cx.game.card.skill.IMagic;
import org.cx.game.exception.RuleValidatorException;

/**
 * 神圣护体
 * @author chenxian
 *
 */
public class HolyBody extends ActiveSkill {

	private Integer bout = 3;
	
	public HolyBody(Integer consume, Integer cooldown, Integer velocity,
			Integer style, Integer func, Integer bout) {
		super(consume, cooldown, velocity, style, func);
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
		
		new HolyBodyBuff(bout, IMagic.Style_Magic, IBuff.Type_Benefit, IMagic.Func_Other, life).effect();;
	}

}
