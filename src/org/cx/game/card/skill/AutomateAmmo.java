package org.cx.game.card.skill;

import org.cx.game.card.skill.ActiveSkill;
import org.cx.game.card.buff.IBuff;
import org.cx.game.card.buff.TiredAttackBuff;
import org.cx.game.exception.RuleValidatorException;

/**
 * 自动装弹
 * @author chenxian
 *
 */
public class AutomateAmmo extends ActiveSkill {

	public AutomateAmmo(Integer consume, Integer cooldown, Integer velocity,
			Integer style, Integer func) {
		super(consume, cooldown, velocity, style, func);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void affect(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		for(IBuff buff : getOwner().getBuffList()){
			if (buff instanceof TiredAttackBuff) {
				buff.invalid();
				break;
			}
		}
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
