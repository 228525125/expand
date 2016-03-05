package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.ActiveSkill;
import org.cx.game.card.buff.IBuff;
import org.cx.game.card.buff.IceVallumBuff;
import org.cx.game.exception.RuleValidatorException;

/**
 * 寒冰壁垒
 * @author chenxian
 *
 */
public class IceVallum extends ActiveSkill {

	private Integer bout;
	
	public IceVallum(Integer consume, Integer cooldown, Integer velocity,
			Integer style, Integer func, Integer bout) {
		super(consume, cooldown, velocity, style, func);
		// TODO Auto-generated constructor stub
		this.bout = bout;
	}

	@Override
	public Integer getRange() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void affect(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		LifeCard life = (LifeCard) objects[0];
		new IceVallumBuff(bout, getStyle(), IBuff.Type_Neutral, getFunc(), life).effect();
	}
	
	@Override
	public void useSkill(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.useSkill(objects);
		
		getOwner().affected(this);
	}

}
