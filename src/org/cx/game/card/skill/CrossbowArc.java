package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.ArcRangeActiveSkill;
import org.cx.game.exception.RuleValidatorException;

/**
 * 弩.弧光
 * @author chenxian
 *
 */
public class CrossbowArc extends ArcRangeActiveSkill {

	private Integer atkScale = 0;
	
	public CrossbowArc(Integer consume, Integer cooldown, Integer velocity,
			Integer radius, Integer atkScale) {
		super(consume, cooldown, velocity, radius);
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
