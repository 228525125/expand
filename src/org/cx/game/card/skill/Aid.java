package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.ActiveSkill;
import org.cx.game.card.skill.IBuff;
import org.cx.game.exception.RuleValidatorException;

/**
 * 援助
 * @author chenxian
 *
 */
public class Aid extends ActiveSkill {

	private LifeCard aidObj = null;
	private Integer bout = 0;
	
	public Aid(Integer consume, Integer cooldown, Integer velocity, Integer style, Integer func, Integer bout) {
		super(consume, cooldown, velocity, style, func);
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
	public void affect(Object... objects)  throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		this.aidObj = (LifeCard) objects[0];
		new AidBuff(bout,getStyle(), IBuff.Type_Benefit, getFunc(), getOwner(),aidObj).effect();
	}
	
	@Override
	public void useSkill(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub		
		super.useSkill(objects);
		
		LifeCard life = (LifeCard) objects[0];
		life.affected(this);
	}

}
