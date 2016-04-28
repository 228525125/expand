package org.cx.game.card.skill;

import org.cx.game.card.skill.ActiveSkill;
import org.cx.game.card.buff.AddAmmoBuff;
import org.cx.game.card.buff.IBuff;
import org.cx.game.card.buff.TiredAttackBuff;
import org.cx.game.exception.RuleValidatorException;

/**
 * 自动装弹
 * @author chenxian
 *
 */
public class AutomateAmmo extends ActiveSkill {

	private Integer bout = 3;
	
	public AutomateAmmo(Integer consume, Integer cooldown, Integer velocity,
			Integer bout) {
		super(consume, cooldown, velocity);
		// TODO Auto-generated constructor stub
		this.bout = bout;
	}
	
	@Override
	public void affect(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		IBuff buff = new AddAmmoBuff(bout, getOwner());
		buff.effect();
	}
	
	@Override
	public Integer getRange() {
		// TODO Auto-generated method stub
		return 1;
	}
	
	@Override
	public void useSkill(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.useSkill(objects);
		
		affect(objects);
	}

}
