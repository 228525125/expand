package org.cx.game.validator;

import org.cx.game.corps.Corps;
import org.cx.game.tools.I18n;

public class CorpsAttackableValidator extends Validator {

	private Corps corps = null;
	
	public CorpsAttackableValidator(Corps corps) {
		// TODO Auto-generated constructor stub
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
