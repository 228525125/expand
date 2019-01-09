package org.cx.game.validator;

import org.cx.game.corps.Corps;
import org.cx.game.magic.skill.ActiveSkill;
import org.cx.game.tools.I18n;

public class CorpsAttackableValidator extends CorpsActivateValidator {

	private Corps corps = null;
	
	public CorpsAttackableValidator(Corps corps) {
		super(corps);
		this.corps = corps;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = super.validate();
		
		if(corps.getAttack().getAttackable()){
			ret = true;
		}else{
			ret = false;
			addMessage(I18n.getMessage(CorpsAttackableValidator.class.getName()));
		}
		
		return ret; 
	}
}
