package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.ActiveSkill;
import org.cx.game.card.skill.IBuff;
import org.cx.game.exception.RuleValidatorException;

/**
 * 变羊
 * @author chenxian
 *
 */
public class Sheep extends ActiveSkill {
	
	private Integer bout;
	
	public Sheep(Integer consume, Integer cooldown, Integer velocity, Integer style, Integer func,
			Integer bout) {
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
		new SheepBuff(bout,getStyle(), IBuff.Type_Harm, getFunc(), life).effect();
	}
	
	@Override
	public void useSkill(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub		
		super.useSkill(objects);
		
		LifeCard life = (LifeCard) objects[0];
		life.affected(this);
	}

}
