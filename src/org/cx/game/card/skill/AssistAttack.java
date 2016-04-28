package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.ActiveSkill;
import org.cx.game.card.buff.AssistAttackBuff;
import org.cx.game.card.buff.IBuff;
import org.cx.game.exception.RuleValidatorException;

/**
 * 协助.进攻
 * @author chenxian
 *
 */
public class AssistAttack extends ActiveSkill {

	private Integer bout = 0;
	private LifeCard aidObj = null;
	
	public AssistAttack(Integer consume, Integer cooldown, Integer velocity,
			 Integer bout) {
		super(consume, cooldown, velocity);
		// TODO Auto-generated constructor stub
		this.bout = bout;
		setParameterTypeValidator(new Class[]{LifeCard.class});
	}

	@Override
	public Integer getRange() {
		// TODO Auto-generated method stub
		return getOwner().getEnergy();
	}
	
	@Override
	public void affect(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		this.aidObj = (LifeCard) objects[0];
		new AssistAttackBuff(bout, getOwner(), aidObj).effect();
	}
	
	@Override
	public void useSkill(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.useSkill(objects);
		
		LifeCard life = (LifeCard) objects[0];
		life.affected(this);
	}

}
