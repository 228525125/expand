package org.cx.game.validator;

import org.cx.game.magic.skill.AbstractSkill;
import org.cx.game.magic.skill.ActiveSkill;
import org.cx.game.tools.I18n;

/**
 * 施法范围检验
 * @author chenxian
 *
 */
public class ConjureRangeValidator extends Validator {

	private AbstractSkill skill = null;
	private Integer position = null;
	
	public ConjureRangeValidator(AbstractSkill skill, Integer position) {
		// TODO Auto-generated constructor stub
		this.skill = skill;
		this.position = position;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Integer curPosition = skill.getOwner().getPosition();
		Integer step = skill.getOwner().getPlayer().getContext().getGround().distance(curPosition, position);
		Integer range = ((ActiveSkill) skill).getRange();
		
		if(range>=step)
			return true;
		else{
			addMessage(I18n.getMessage(ConjureRangeValidator.class.getName()));
			return false;
		}
	}
}
