package org.cx.game.validator;

import org.cx.game.magic.skill.ISkill;
import org.cx.game.tools.I18n;

/**
 * 施法范围检验
 * @author chenxian
 *
 */
public class ConjureRangeValidator extends Validator {

	private ISkill skill = null;
	private Integer position = null;
	
	public ConjureRangeValidator(ISkill skill, Integer position) {
		// TODO Auto-generated constructor stub
		this.skill = skill;
		this.position = position;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Integer curPosition = skill.getOwner().getPosition();
		Integer step = skill.getOwner().getPlayer().getContext().getGround().distance(curPosition, position);
		Integer range = skill.getRange();
		
		if(range>=step)
			return true;
		else{
			addMessage(I18n.getMessage(ConjureRangeValidator.class.getName()));
			return false;
		}
	}
}
