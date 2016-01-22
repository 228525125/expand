package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.ActiveSkill;
import org.cx.game.card.skill.IBuff;
import org.cx.game.exception.RuleValidatorException;

/**
 * 猎人标记
 * @author chenxian
 *
 */
public class HunterTab extends ActiveSkill {

	private Integer bout;
	private Integer damageScale;
	
	public HunterTab(Integer consume, Integer cooldown, Integer velocity,
			Integer style, Integer func, Integer bout, Integer damageScale) {
		super(consume, cooldown, velocity, style, func);
		// TODO Auto-generated constructor stub
		this.bout = bout;
		this.damageScale = damageScale;
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
		new HunterTabBuff(bout,getStyle(),IBuff.Type_Harm, getFunc(), damageScale,life).effect();
	}
	
	@Override
	public void useSkill(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.useSkill(objects);
		
		LifeCard affected = (LifeCard) objects[0];
		affected.affected(this);
	}

}
