package org.cx.game.validator;

import org.cx.game.corps.Corps;
import org.cx.game.tools.I18n;

public class CorpsActivateValidator extends Validator {

	private Corps corps = null;
	
	public CorpsActivateValidator(Corps corps) {
		// TODO Auto-generated constructor stub
		this.corps = corps;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = super.validate();
		if(ret){
			if(this.corps.getActivate().getActivation()){
				ret = true;
			}else{
				addMessage(I18n.getMessage(CorpsActivateValidator.class.getName()));
				ret = false;
			}
		}
		
		return ret;
	}
}
