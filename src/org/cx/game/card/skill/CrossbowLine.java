package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.LineRangeActiveSkill;
import org.cx.game.exception.RuleValidatorException;

/**
 * 弩.直电
 * @author chenxian
 *
 */
public class CrossbowLine extends LineRangeActiveSkill {

	private Integer atkScale = 0;
	
	public CrossbowLine(Integer consume, Integer cooldown, Integer velocity,
			Integer style, Integer func, Integer length, Integer atkScale) {
		super(consume, cooldown, velocity, style, func, length);
		// TODO Auto-generated constructor stub
		this.atkScale = atkScale;
	}
	
	@Override
	public void affect(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		LifeCard life = (LifeCard) objects[0];
		life.getDeath().magicToHp(-getOwner().getAttack().getAtk()*atkScale/100);
	}

	@Override
	public Integer getRange() {
		// TODO Auto-generated method stub
		return 1;
	}

}
