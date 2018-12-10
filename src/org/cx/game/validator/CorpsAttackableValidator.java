package org.cx.game.validator;

import org.cx.game.corps.Corps;
import org.cx.game.magic.skill.ActiveSkill;
import org.cx.game.tools.I18n;

public class CorpsAttackableValidator extends Validator {

	private ActiveSkill skill = null;
	
	public CorpsAttackableValidator(ActiveSkill skill) {
		this.skill = skill;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = super.validate();
		
		Corps corps = (Corps) this.skill.getOwner();
		
		if(corps.getAttack().getAttackable()){
			ret = true;
		}else{
			ret = false;
			addMessage(I18n.getMessage(CorpsAttackableValidator.class.getName()));
		}
		
		return ret; 
	}
}
